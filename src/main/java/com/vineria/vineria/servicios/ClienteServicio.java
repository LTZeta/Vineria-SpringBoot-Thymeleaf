package com.vineria.vineria.servicios;

import com.vineria.vineria.entidades.Cliente;
import com.vineria.vineria.repositorio.ClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServicio {

    @Autowired
    ClienteRepositorio clienteRepositorio;

    public void crearCliente(String nombre, String domicilio){

        List<Cliente> clienteExiste = clienteRepositorio.buscarPorNombreYDomicilio(nombre, domicilio);
        if (clienteExiste.isEmpty()){
            Cliente cliente = new Cliente()
                    .setNombre(nombre)
                    .setDomicilio(domicilio);
            clienteRepositorio.save(cliente);
        }else throw new RuntimeException("Ya existe un Cliente idéntico");

    }

    public Cliente actualizarCliente(Integer id, String nombre, String domicilio){

        Optional<Cliente> ClienteAActualizar = clienteRepositorio.findById(id);

        if (ClienteAActualizar.isPresent()){

            if (!ClienteAActualizar.get().getNombre().equalsIgnoreCase(nombre)){
                ClienteAActualizar.get().setNombre(nombre);
            }
            if (!ClienteAActualizar.get().getDomicilio().equals(domicilio)){
                ClienteAActualizar.get().setDomicilio(domicilio);
            }

            return clienteRepositorio.save(ClienteAActualizar.get());

        }else throw new RuntimeException("No se encontró el cliente");

    }

    public void eliminarCliente(Integer id){ clienteRepositorio.deleteById(id); }

    public Cliente buscarPorId(Integer id){

        Optional<Cliente> cliente = clienteRepositorio.findById(id);
        if (cliente.isEmpty()){
            throw new RuntimeException("Cliente inexistente");
        }
        return cliente.get();

    }

    public List<Cliente> busquedaPorMatching(String busqueda){

        List<Cliente> resultado = clienteRepositorio.busquedaPorMatching(busqueda);
        if (resultado.isEmpty()){
            throw new RuntimeException("No se encontraron clientes con ese Nombre");
        }
        return resultado;

    }

    public List<Cliente> buscarTodo(){

        List<Cliente> list = clienteRepositorio.findAll();
        if (list.isEmpty()){
            throw new RuntimeException("Aun no se cargo ningún cliente!");
        }
        return list;

    }


    public List<Cliente> buscarPorDomicilio(String domicilio){

        List<Cliente> clientesEn = clienteRepositorio.buscarPorDomicilio(domicilio);
        if (clientesEn.isEmpty()){
            throw new RuntimeException("No se encontraron clientes con ese domicilio");
        }
        else {
            return clientesEn;
        }

    }

}