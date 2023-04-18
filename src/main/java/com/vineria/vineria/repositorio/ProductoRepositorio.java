package com.vineria.vineria.repositorio;

import com.vineria.vineria.entidades.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepositorio extends JpaRepository<Producto, Integer> {


    /* la 't' es de TABLE y sentenciamos que PRODUCTO(la entidad) sea t
    *  ESTO SE LLAMA SEGUN FRANQUITO DEL MORO: ESCRIBIR LA SENTENCIA MANUALMENTE. */
    @Query("SELECT t FROM Producto AS t WHERE t.descripcion = :descripcion")
    Optional<Producto> buscarPorDescripcion(@Param("descripcion") String descripcion);

    @Query("SELECT t FROM Producto AS t WHERE t.descripcion LIKE :busqueda%")
    List<Producto> busquedaPorMatching(@Param("busqueda") String busqueda);



    /* ESTO SE LLAMA DERIVATED QUERYS SEGUN FRANQUITO DEL MONTE */
    boolean existsByDescripcion(String descripcion);



}