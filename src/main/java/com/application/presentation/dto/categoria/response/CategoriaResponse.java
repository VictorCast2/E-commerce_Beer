package com.application.presentation.dto.categoria.response;

public record CategoriaResponse(
        Long id,
        String nombre,
        String descripcion,
        long totalPacks
) {
}
