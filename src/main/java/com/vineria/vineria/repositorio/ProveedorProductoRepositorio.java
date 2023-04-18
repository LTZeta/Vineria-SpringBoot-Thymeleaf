package com.vineria.vineria.repositorio;

import com.vineria.vineria.entidades.ProveedorProducto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProveedorProductoRepositorio extends JpaRepository<ProveedorProducto, Integer> {

    @Query("SELECT p FROM ProveedorProducto AS p WHERE p.proveedor.id = :id")
    List<ProveedorProducto> buscarPorIdProveedor(@Param("id") Integer id);

    @Query("SELECT p FROM ProveedorProducto AS p WHERE p.proveedor.id = :id AND p.producto.descripcion LIKE :busqueda%")
    List<ProveedorProducto> buscarPorIdProveedorYNombreProducto(@Param("id") Integer id, @Param("busqueda") String busqueda);

    @Query("SELECT p FROM ProveedorProducto AS p WHERE p.proveedor.id = :idProveedor AND p.producto.id = :idProducto")
    ProveedorProducto buscarPorIdProveedorEIdProducto(@Param("idProveedor") Integer idProveedor, @Param("idProducto") Integer idProducto);

    @Transactional
    void deleteByProveedorId(Integer id);

    @Transactional
    void deleteByProductoId(Integer id);

}
