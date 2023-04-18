package com.vineria.vineria.controladores;

import com.vineria.vineria.entidades.Cliente;
import com.vineria.vineria.servicios.ClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/clientes")
public class ClienteControlador {

    @Autowired
    ClienteServicio clienteServicio;

    @GetMapping("")
    public String mostrarClientes(ModelMap model){

        try {
            List<Cliente> clientes = clienteServicio.buscarTodo();
            model.put("clientes", clientes);
            model.put("sinClientes", false);
            return "clientes.html";
        }catch (Exception e){
            model.put("sinClientes", true);
            model.put("errorSinClientes", e.getMessage());
            return "clientes.html";
        }

    }

    @PostMapping("/eliminarCliente")
    public String eliminarCliente(ModelMap model, @RequestParam Integer id){

        clienteServicio.eliminarCliente(id);
        return "redirect:/clientes";

    }

    @PostMapping("/buscarPorNombre")
    public String buscarPorNombre(ModelMap model, @RequestParam String busqueda){

        try {
            List<Cliente> resultado = clienteServicio.busquedaPorMatching(busqueda);
            model.put("clientes", resultado);
            model.put("noEncontrado", false);
            return "clientes.html";
        }catch (Exception e){
            model.put("noEncontrado", true);
            model.put("errorNoEncontrado", e.getMessage());
            return "clientes.html";
        }

    }

    @GetMapping("/crearCliente")
    public String mostrarCrearCliente(ModelMap model){
        return "clienteCrear.html";
    }

    @PostMapping("/crearCliente")
    public String crearCliente(ModelMap model, @RequestParam String nombre, @RequestParam String domicilio){

        try {
            clienteServicio.crearCliente(nombre, domicilio);
            model.put("creado", true);
            model.put("errorCreado", null);
            return "clienteCrear.html";
        }catch (RuntimeException e){
            model.put("creado", false);
            model.put("errorCreado", e.getMessage());
            return "clienteCrear.html";
        }

    }

    @GetMapping("/actualizarCliente/{id}")
    public String mostrarClienteAActualizar(ModelMap model, @PathVariable Integer id){

        try {
            Cliente cliente = clienteServicio.buscarPorId(id);
            model.put("cliente", cliente);
            model.put("sinCliente", false);
            return "clienteActualizar.html";
        }catch (Exception e){
            model.put("sinCliente", true);
            model.put("errorSinCliente", e.getMessage());
            return "clienteActualizar.html";
        }

    }

    @PostMapping("/actualizarCliente")
    public String actualizarCliente(ModelMap model, @RequestParam Integer id, @RequestParam String nombre, @RequestParam String domicilio){

        try {
            Cliente cliente = clienteServicio.actualizarCliente(id, nombre, domicilio);
            model.put("actualizado", true);
            model.put("errorActualizado", null);
            model.put("cliente", cliente);
            return "clienteActualizar.html";
        }catch (RuntimeException e){
            Cliente cliente = clienteServicio.buscarPorId(id);
            model.put("actualizado", false);
            model.put("errorActualizado", e.getMessage());
            model.put("cliente", cliente);
            return "clienteActualizar.html";
        }

    }

}