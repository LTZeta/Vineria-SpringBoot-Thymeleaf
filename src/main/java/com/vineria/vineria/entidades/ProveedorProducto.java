package com.vineria.vineria.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Entity
@Table(name = "proveedor_producto")
@Data @AllArgsConstructor @NoArgsConstructor
@Accessors(chain = true)
public class ProveedorProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Producto producto;

    @ManyToOne
    private Proveedor proveedor;

    private Float precio;

    public ProveedorProducto(Proveedor proveedor, Producto producto, Float precio){
        this.producto = producto;
        this.proveedor = proveedor;
        this.precio = precio;
    }

}