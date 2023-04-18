package com.vineria.vineria.servicios;

import com.vineria.vineria.entidades.Producto;
import com.vineria.vineria.repositorio.ProductoRepositorio;
import com.vineria.vineria.repositorio.ProveedorProductoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoServicio {

    @Autowired
    private ProductoRepositorio productoRepositorio;
    @Autowired
    private ProveedorProductoRepositorio proveedorProductoRepositorio;

    public void crearProducto(String descripcion, Integer precio, Integer stock){
        Optional<Producto> productoEncontradoSiExiste = productoRepositorio.buscarPorDescripcion(descripcion);
        if (productoEncontradoSiExiste.isEmpty()){
            Producto producto = new Producto()
                    .setDescripcion(descripcion)
                    .setPrecio(precio)
                    .setStock(stock);
            productoRepositorio.save(producto);
        }else throw new RuntimeException("El producto ya existe");
    }

    public Producto actualizarProducto(Integer id, String descripcion, Integer precio, Integer stock){
        Optional<Producto> productoAActualizar = productoRepositorio.findById(id);

        if (productoAActualizar.isPresent()){

            if (!productoAActualizar.get().getDescripcion().equalsIgnoreCase(descripcion)){
                productoAActualizar.get().setDescripcion(descripcion);
            }
            if (!productoAActualizar.get().getPrecio().equals(precio)){
                productoAActualizar.get().setPrecio(precio);
            }
            if (!productoAActualizar.get().getStock().equals(stock)){
                productoAActualizar.get().setStock(stock);
            }

            return productoRepositorio.save(productoAActualizar.get());

        }else throw new RuntimeException("No se encontró el producto");

    }

    public void eliminarProducto(Integer id){
        productoRepositorio.deleteById(id);
        proveedorProductoRepositorio.deleteByProductoId(id);
    }

    public List<Producto> busquedaPorMatching(String busqueda){
        List<Producto> lista = productoRepositorio.busquedaPorMatching(busqueda);
        if (lista.isEmpty()){
            throw new RuntimeException("No se encontraron Productos con esa descripcion");
        }
        return lista;
    }

    public List<Producto> buscarTodo(){
        List <Producto> lista = productoRepositorio.findAll();
        if (lista.isEmpty()){
            throw new RuntimeException("Aun no se cargo ningún producto!");
        }
        return lista;
    }

    public Producto buscarPorId(Integer id) {
        Optional<Producto> producto = productoRepositorio.findById(id);
        if (producto.isEmpty()){
            throw new RuntimeException("Producto inexistente");
        }
        return producto.get();
    }

}