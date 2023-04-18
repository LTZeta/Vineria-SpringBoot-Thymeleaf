package com.vineria.vineria.servicios;

import com.vineria.vineria.entidades.Producto;
import com.vineria.vineria.entidades.Proveedor;
import com.vineria.vineria.entidades.ProveedorProducto;
import com.vineria.vineria.repositorio.ProductoRepositorio;
import com.vineria.vineria.repositorio.ProveedorProductoRepositorio;
import com.vineria.vineria.repositorio.ProveedorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProveedorServicio {
    @Autowired
    private ProveedorRepositorio proveedorRepositorio;

    @Autowired
    private ProveedorProductoRepositorio proveedorProductoRepositorio;

    @Autowired
    private ProductoRepositorio productoRepositorio;

    public List<Proveedor> buscarTodo(){
        List<Proveedor> proveedores = proveedorRepositorio.findAll();
        if (proveedores.isEmpty()){
            throw new RuntimeException("Aun no se cargo ningún proveedor!");
        }
        return proveedores;
    }

    public void eliminarProveedor(Integer id){
        proveedorRepositorio.deleteById(id);
        proveedorProductoRepositorio.deleteByProveedorId(id);
    }

    public List<Proveedor> busquedaPorMatching(String busqueda){
        List<Proveedor> resultado = proveedorRepositorio.busquedaPorMatching(busqueda);
        if (resultado.isEmpty()){
            throw new RuntimeException("No se encontraron proveedores con ese nombre");
        }
        return resultado;
    }

    public void crearProveedor(String nombre, String localidad){

        List<Proveedor> proveedorExiste = proveedorRepositorio.buscarPorNombreYLocalidad(nombre, localidad);
        if (proveedorExiste.isEmpty()){
            Proveedor proveedor = new Proveedor()
                    .setNombre(nombre)
                    .setLocalidad(localidad);
            proveedorRepositorio.save(proveedor);
        }else throw new RuntimeException("Ya existe un Proveedor idéntico");

    }

    public Proveedor buscarPorId(Integer id){

        Optional<Proveedor> proveedor = proveedorRepositorio.findById(id);
        if (proveedor.isEmpty()){
            throw new RuntimeException("Proveedor inexistente");
        }
        return proveedor.get();

    }

    public Proveedor actualizarProveedor(Integer id, String nombre, String localidad){

        Optional<Proveedor> proveedorAActualizar = proveedorRepositorio.findById(id);

        if (proveedorAActualizar.isPresent()){

            if (!proveedorAActualizar.get().getNombre().equalsIgnoreCase(nombre)){
                proveedorAActualizar.get().setNombre(nombre);
            }
            if (!proveedorAActualizar.get().getLocalidad().equals(localidad)){
                proveedorAActualizar.get().setLocalidad(localidad);
            }

            return proveedorRepositorio.save(proveedorAActualizar.get());

        }else throw new RuntimeException("No se encontró el proveedor");

    }

    public List<ProveedorProducto> todosLosProductos(Proveedor proveedor){

        List<ProveedorProducto> proveedorProductos = proveedorProductoRepositorio.buscarPorIdProveedor(proveedor.getId());
        if (proveedorProductos.isEmpty()){
            throw new RuntimeException("El proveedor no tiene ningún producto");
        }else {
            return proveedorProductos;
        }

    }

    public List<ProveedorProducto> todosLosProductosConNombre(Proveedor proveedor, String busqueda){

        List<ProveedorProducto> proveedorProductos = proveedorProductoRepositorio.buscarPorIdProveedorYNombreProducto(proveedor.getId(), busqueda);
        if (proveedorProductos.isEmpty()){
            throw new RuntimeException("El proveedor no tiene ningún producto con esa descripción");
        }else {
            return proveedorProductos;
        }

    }

    public ProveedorProducto buscarProductoAActualizar(Integer idProveedor, Integer idProducto){
        return proveedorProductoRepositorio.buscarPorIdProveedorEIdProducto(idProveedor, idProducto);
    }

    public ProveedorProducto buscarPorIdProveedorProducto(Integer id){

        Optional<ProveedorProducto> proveedorProducto = proveedorProductoRepositorio.findById(id);
        if (proveedorProducto.isPresent()){
            return proveedorProducto.get();
        }else throw new RuntimeException("La relación es inexistente");

    }

    public ProveedorProducto actualizarProductoAProveedor(Integer idProveedor, Float precio){

        Optional<ProveedorProducto> proveedorProductoAActualizar = proveedorProductoRepositorio.findById(idProveedor);
        if (proveedorProductoAActualizar.isPresent()){

            if (!proveedorProductoAActualizar.get().getPrecio().equals(precio)){
                proveedorProductoAActualizar.get().setPrecio(precio);
            }

            return proveedorProductoRepositorio.save(proveedorProductoAActualizar.get());

        }else throw new RuntimeException("La relación no existe");

    }

    public void eliminarProveedorProducto(Integer id){proveedorProductoRepositorio.deleteById(id);}

    public List<Producto> todosLosProductos(){return productoRepositorio.findAll();}

    public void AgregarProductoAProveedor (Integer idProveedor, Integer idProducto, Float precio){

        boolean yaExiste = validar(idProveedor, idProducto);

        if (yaExiste){
            throw new RuntimeException("El producto ya esta agregado al proveedor");
        }else {
            crearProductoAProveedor(idProveedor, idProducto, precio);
        }

    }

    public boolean validar(Integer idProveedor, Integer idProducto){

        boolean yaExiste;
        ProveedorProducto proveedorProducto = proveedorProductoRepositorio.buscarPorIdProveedorEIdProducto(idProveedor, idProducto);
        if (proveedorProducto == null){
            return yaExiste=false;
        }else{
            return yaExiste=true;
        }

    }

    public void crearProductoAProveedor(Integer idProveedor, Integer idProducto, Float precio){

        Optional<Proveedor> proveedor = proveedorRepositorio.findById(idProveedor);
        Optional<Producto> producto = productoRepositorio.findById(idProducto);
        if (proveedor.isPresent()){
            if (producto.isPresent()){
                ProveedorProducto proveedorProducto = new ProveedorProducto(proveedor.get(), producto.get(), precio);
                proveedorProductoRepositorio.save(proveedorProducto);
            }else throw new RuntimeException("El producto no existe");
        }else throw new RuntimeException("El proveedor no existe");

    }

}