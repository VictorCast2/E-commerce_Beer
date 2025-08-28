package com.application.presentation.dto.producto.response;

public record ProductoResponse(
        Long id,
        String imagen,
        String nombre,
        double precio,
        int stock,
        String descripcion,
        String marca,
        String presentacion
) {
}
