package com.vineria.vineria.controladores;

import com.vineria.vineria.entidades.Producto;
import com.vineria.vineria.servicios.ProductoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/productos")
public class ProductoControlador {

    @Autowired
    private ProductoServicio productoServicio;

    @GetMapping("")
    public String mostrarProductos(ModelMap model){

        try {
            List<Producto> productos = productoServicio.buscarTodo();
            model.put("productos", productos);
            model.put("sinProductos", false);
            return "productos.html";
        }catch (Exception e){
            model.put("sinProductos", true);
            model.put("errorSinProductos", e.getMessage());
            return "productos.html";
        }

    }

    @GetMapping("/inventario")
    public String mostrarInventario(ModelMap model){

        try {
            List<Producto> productos = productoServicio.buscarTodo();
            model.put("productos", productos);
            model.put("sinProductos", false);
            return "inventario.html";
        }catch (Exception e){
            model.put("sinProductos", true);
            model.put("errorSinProductos", e.getMessage());
            return "inventario.html";
        }

    }

    @PostMapping("/eliminarProducto")
    public String eliminarProducto(ModelMap model, @RequestParam Integer id){

        productoServicio.eliminarProducto(id);
        return "redirect:/productos";

    }

    @PostMapping("/buscarPorDescripcion")
    public String buscarPorDescripcion(ModelMap model, @RequestParam String busqueda){

        try {
            List<Producto> resultado = productoServicio.busquedaPorMatching(busqueda);
            model.put("productos", resultado);
            model.put("noEncontrado", false);
            return "productos.html";
        }catch (Exception e){
            model.put("noEncontrado", true);
            model.put("errorNoEncontrado", e.getMessage());
            return "productos.html";
        }

    }

    @PostMapping("/inventario/buscarPorDescripcion")
    public String inventarioBuscarPorDescripcion(ModelMap model, @RequestParam String busqueda){

        try {
            List<Producto> resultado = productoServicio.busquedaPorMatching(busqueda);
            model.put("productos", resultado);
            model.put("noEncontrado", false);
            return "inventario.html";
        }catch (Exception e){
            model.put("noEncontrado", true);
            model.put("errorNoEncontrado", e.getMessage());
            return "inventario.html";
        }

    }

    @GetMapping("/crearProducto")
    public String mostrarCrearProducto(ModelMap model){
        return "productoCrear.html";
    }

    @PostMapping("/crearProducto")
    public String crearProducto(ModelMap model, @RequestParam String descripcion, @RequestParam Integer precio, @RequestParam Integer stock){

        try {
            productoServicio.crearProducto(descripcion, precio, stock);
            model.put("creado", true);
            model.put("errorCreado", null);
            return "productoCrear.html";
        }catch (RuntimeException e){
            model.put("creado", false);
            model.put("errorCreado", e.getMessage());
            return "productoCrear.html";
        }

    }

    @GetMapping("/actualizarProducto/{id}")
    public String mostrarProductoAActualizar(ModelMap model, @PathVariable Integer id){

        try {
            Producto producto = productoServicio.buscarPorId(id);
            model.put("producto", producto);
            return "productoActualizar.html";
        }catch (Exception e){
            model.put("sinProducto", true);
            model.put("errorSinProducto", e.getMessage());
            return "productoActualizar.html";
        }

    }

    @PostMapping("/actualizarProducto")
    public String actualizarProducto(ModelMap model, @RequestParam Integer id, @RequestParam String descripcion, @RequestParam Integer precio, @RequestParam Integer stock){

        try {
            Producto producto = productoServicio.actualizarProducto(id, descripcion, precio, stock);
            model.put("actualizado", true);
            model.put("error", null);
            model.put("producto", producto);
            return "productoActualizar.html";
        }catch (RuntimeException e){
            Producto producto = productoServicio.buscarPorId(id);
            model.put("actualizado", false);
            model.put("errorActualizado", e.getMessage());
            model.put("producto", producto);
            return "productoActualizar.html";
        }

    }

}
