package com.application.presentation.dto.pack.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ProductoCantidadRequest(
        @NotNull Long productoId,
        @Min(1) int cantidad
) {
}
