package com.vineria.vineria.controladores;

import com.vineria.vineria.entidades.Producto;
import com.vineria.vineria.entidades.Proveedor;
import com.vineria.vineria.entidades.ProveedorProducto;
import com.vineria.vineria.servicios.ProveedorServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/proveedores")
public class ProveedorControlador {

    @Autowired
    private ProveedorServicio proveedorServicio;

    @GetMapping("")
    public String mostrarProveedores(ModelMap model){

        try {
            List<Proveedor> proveedores = proveedorServicio.buscarTodo();
            model.put("proveedores", proveedores);
            model.put("sinProveedores", false);
            return "proveedores.html";
        }catch (Exception e){
            model.put("sinProveedores", true);
            model.put("errorSinProveedores", e.getMessage());
            return "proveedores.html";
        }

    }

    @PostMapping("/eliminarProveedor")
    public String eliminarProveedor(@RequestParam Integer id){

        proveedorServicio.eliminarProveedor(id);
        return "redirect:/proveedores";

    }

    @PostMapping("/buscarPorNombre")
    public String buscarPorNombre(ModelMap model, @RequestParam String busqueda){

        try {
            List<Proveedor> resultado = proveedorServicio.busquedaPorMatching(busqueda);
            model.put("proveedores", resultado);
            model.put("noEncontrado", false);
            return "proveedores.html";
        }catch (Exception e){
            model.put("noEncontrado", true);
            model.put("errorNoEncontrado", e.getMessage());
            return "proveedores.html";
        }

    }

    @GetMapping("/crearProveedor")
    public String mostrarCrearProveedor(ModelMap model){
        return "proveedorCrear.html";
    }

    @PostMapping("/crearProveedor")
    public String crearProveedor(ModelMap model, @RequestParam String nombre, @RequestParam String localidad){

        try {
            proveedorServicio.crearProveedor(nombre, localidad);
            model.put("creado", true);
            model.put("errorCreado", null);
            return "proveedorCrear.html";
        }catch (RuntimeException e){
            model.put("creado", false);
            model.put("errorCreado", e.getMessage());
            return "proveedorCrear.html";
        }

    }

    @GetMapping("/actualizarProveedor/{id}")
    public String mostrarProveedorAActualizar(ModelMap model, @PathVariable Integer id){

        try {
            Proveedor proveedor = proveedorServicio.buscarPorId(id);
            model.put("proveedor", proveedor);
            model.put("sinProveedor", false);
            return "proveedorActualizar.html";
        }catch (Exception e){
            model.put("sinProveedor", true);
            model.put("errorSinProveedor", e.getMessage());
            return "proveedorActualizar.html";
        }

    }

    @PostMapping("/actualizarProveedor")
    public String actualizarProveedor(ModelMap model, @RequestParam Integer id, @RequestParam String nombre, @RequestParam String localidad){

        try {
            Proveedor proveedor = proveedorServicio.actualizarProveedor(id, nombre, localidad);
            model.put("actualizado", true);
            model.put("errorActualizado", null);
            model.put("proveedor", proveedor);
            return "proveedorActualizar.html";
        }catch (RuntimeException e){
            Proveedor proveedor = proveedorServicio.buscarPorId(id);
            model.put("actualizado", false);
            model.put("errorActualizado", e.getMessage());
            model.put("proveedor", proveedor);
            return "proveedorActualizar.html";
        }

    }

    @GetMapping("/productosProveedor/{id}")
    public String mostrarProductosProveedor(ModelMap model, @PathVariable Integer id){

        try {
            Proveedor proveedor = proveedorServicio.buscarPorId(id);
            List<ProveedorProducto> proveedorProductos = proveedorServicio.todosLosProductos(proveedor);
            model.put("proveedor", proveedor);
            model.put("proveedorProductos", proveedorProductos);
            model.put("sinProductos", false);
            return "proveedorProductos.html";


        }catch (Exception e){
            Proveedor proveedor = proveedorServicio.buscarPorId(id);
            model.put("titulo", "Productos de "+proveedor.getNombre());
            model.put("proveedor", proveedor);
            model.put("sinProductos", true);
            model.put("errorSinProductos", e.getMessage());
            return "proveedorProductos.html";
        }

    }

    @PostMapping("/buscarProductoPorDescripcion")
    public String buscarProductosPorNombre(ModelMap model, @RequestParam Integer id, @RequestParam String busqueda){

        try {
            Proveedor proveedor = proveedorServicio.buscarPorId(id);
            List<ProveedorProducto> proveedorProductos = proveedorServicio.todosLosProductosConNombre(proveedor, busqueda);
            model.put("titulo", "Productos de "+proveedor.getNombre());
            model.put("proveedor", proveedor);
            model.put("proveedorProductos", proveedorProductos);
            model.put("sinProductos", false);
            return "proveedorProductos.html";
        }catch (Exception e){
            Proveedor proveedor = proveedorServicio.buscarPorId(id);
            model.put("titulo", "Productos de "+proveedor.getNombre());
            model.put("proveedor", proveedor);
            model.put("noEncontrado", true);
            model.put("errorNoEncontrado", e.getMessage());
            return "proveedorProductos.html";
        }

    }

    @GetMapping("/actualizarProducto/{idProveedor}/{idProducto}")
    public String mostrarActualizarProductoAProveedor(ModelMap model, @PathVariable Integer idProveedor, @PathVariable Integer idProducto){

        Proveedor proveedor = proveedorServicio.buscarPorId(idProveedor);
        ProveedorProducto proveedorProducto = proveedorServicio.buscarProductoAActualizar(idProveedor, idProducto);
        model.put("titulo", "Actualizar "+ proveedorProducto.getProducto().getDescripcion() +" de "+proveedor.getNombre());
        model.put("proveedor", proveedor);
        model.put("proveedorProducto", proveedorProducto);
        return "proveedorActualizarProducto.html";

    }

    @PostMapping("/actualizarProducto")
    public String actualizarProductoAProveedor(ModelMap model, @RequestParam Integer idProveedor, @RequestParam Integer idProveedorProducto, Float precio){

        try {
            Proveedor proveedor = proveedorServicio.buscarPorId(idProveedor);
            ProveedorProducto proveedorProducto = proveedorServicio.actualizarProductoAProveedor(idProveedorProducto, precio);
            model.put("titulo", "Actualizar "+ proveedorProducto.getProducto().getDescripcion() +" de "+proveedor.getNombre());
            model.put("proveedor", proveedor);
            model.put("proveedorProducto", proveedorProducto);
            model.put("actualizado", true);
            return "proveedorActualizarProducto.html";
        }catch (Exception e){
            Proveedor proveedor = proveedorServicio.buscarPorId(idProveedor);
            ProveedorProducto proveedorProducto = proveedorServicio.buscarPorIdProveedorProducto(idProveedorProducto);
            model.put("titulo", "Actualizar "+ proveedorProducto.getProducto().getDescripcion() +" de "+proveedor.getNombre());
            model.put("proveedor", proveedor);
            model.put("proveedorProducto", proveedorProducto);
            model.put("actualizado", false);
            model.put("errorActualizado", e.getMessage());
            return "proveedorActualizarProducto.html";

        }
    }

    @PostMapping("/eliminarProducto")
    public String eliminarProductoAProveedor(@RequestParam Integer idProductoProveedor){

        ProveedorProducto proveedorProducto =  proveedorServicio.buscarPorIdProveedorProducto(idProductoProveedor);
        Proveedor proveedor = proveedorProducto.getProveedor();
        proveedorServicio.eliminarProveedorProducto(idProductoProveedor);
        return "redirect:/proveedores/productosProveedor/" + proveedor.getId();

    }

    @GetMapping("/agregarProducto/{idProveedor}")
    public String mostrarAgregarProductoAProveedor(ModelMap model, @PathVariable Integer idProveedor){

        Proveedor proveedor = proveedorServicio.buscarPorId(idProveedor);
        List<Producto> productos = proveedorServicio.todosLosProductos();
        model.put("titulo", "Agregar productos a " + proveedor.getNombre());
        model.put("proveedor", proveedor);
        model.put("productos", productos);
        return "proveedorAgregarProducto.html";
    }

    @PostMapping("/agregarProducto")
    public String agregarProductoAProveedor(ModelMap model, @RequestParam Integer idProveedor, @RequestParam Integer idProducto, @RequestParam Float precio){

        try {
            Proveedor proveedor = proveedorServicio.buscarPorId(idProveedor);
            List<Producto> productos = proveedorServicio.todosLosProductos();
            proveedorServicio.AgregarProductoAProveedor(idProveedor, idProducto, precio);
            model.put("titulo", "Agregar productos a " + proveedor.getNombre());
            model.put("proveedor", proveedor);
            model.put("productos", productos);
            model.put("agregado", true);
            return "proveedorAgregarProducto.html";

        }catch (Exception e){
            Proveedor proveedor = proveedorServicio.buscarPorId(idProveedor);
            List<Producto> productos = proveedorServicio.todosLosProductos();
            model.put("proveedor", proveedor);
            model.put("titulo", "Agregar productos a " + proveedor.getNombre());
            model.put("productos", productos);
            model.put("agregado", false);
            model.put("errorAgregado", e.getMessage());
            return "proveedorAgregarProducto.html";
        }

    }

}