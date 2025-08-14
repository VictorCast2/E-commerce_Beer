package com.application.presentation.dto.categoria.response;

public record CategoriaResponse(
        String nombre,
        String descripcion,
        long totalPacks
) {
}
