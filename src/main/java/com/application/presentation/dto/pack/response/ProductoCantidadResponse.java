package com.application.presentation.dto.pack.response;

public record ProductoCantidadResponse(
        Long productoId,
        String nombre,
        int cantidad
) {
}
