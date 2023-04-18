package com.vineria.vineria.repositorio;

import com.vineria.vineria.entidades.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepositorio extends JpaRepository<Cliente, Integer> {

    Cliente existsByNombre(String nombre);

    @Query("SELECT t FROM Cliente AS t WHERE t.domicilio = :domicilio")
    List<Cliente> buscarPorDomicilio(@Param("domicilio") String domicilio);

    @Query("SELECT t FROM Cliente AS t WHERE t.nombre = :nombre AND t.domicilio = :domicilio")
    List<Cliente> buscarPorNombreYDomicilio(@Param("nombre") String nombre, @Param("domicilio") String domicilio);

    @Query("SELECT t FROM Cliente AS t WHERE t.nombre LIKE :busqueda%")
    List<Cliente> busquedaPorMatching(@Param("busqueda") String busqueda);
}
