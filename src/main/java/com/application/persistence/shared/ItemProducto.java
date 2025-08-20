package com.application.persistence.shared;

import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@MappedSuperclass // indica que no es una tabla, pero sus campos se heredan como columnas en las subclases.
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class ItemProducto {

    private String imagen;
    private String nombre;
    private double precio;
    private int stock;
    private String descripcion;
    private boolean activo;
}
