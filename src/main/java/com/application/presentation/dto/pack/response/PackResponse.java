package com.application.presentation.dto.pack.response;

import com.application.persistence.entity.producto.enums.ETipo;

import java.util.List;

public record PackResponse(
        Long id,
        String imagen,
        String nombre,
        double precio,
        int stock,
        String descripcion,
        ETipo tipo,
        List<PackCategoriaResponse> categorias,
        List<ProductoCantidadResponse> productos
) {
}
