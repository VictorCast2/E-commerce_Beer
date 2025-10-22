package com.application.presentation.dto.categoria.response;

import java.util.List;

public record CategoriaResponse(
        Long id,
        String imagen,
        String nombre,
        String descripcion,
        boolean activo,
        long totalProductos,
        List<SubCategoriaResponse> subcategorias
) {
}
