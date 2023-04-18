package com.vineria.vineria.repositorio;

import com.vineria.vineria.entidades.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProveedorRepositorio extends JpaRepository<Proveedor, Integer> {

    @Query("SELECT t FROM Proveedor AS t WHERE t.nombre LIKE :busqueda%")
    List<Proveedor> busquedaPorMatching(@Param("busqueda") String busqueda);

    @Query("SELECT t FROM Proveedor AS t WHERE t.nombre = :nombre AND t.localidad = :localidad")
    List<Proveedor> buscarPorNombreYLocalidad(@Param("nombre") String nombre, @Param("localidad") String localidad);

}