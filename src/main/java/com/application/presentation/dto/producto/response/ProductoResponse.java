package com.application.presentation.dto.producto.response;

import com.application.persistence.entity.producto.enums.ETipo;

import java.util.List;

public record ProductoResponse(
        Long id,
        String imagen,
        String nombre,
        String marca,
        String pais,
        ETipo tipo,
        double precio,
        int stock,
        String descripcion,
        List<ProductoCategoriaResponse> categorias
) {
}
