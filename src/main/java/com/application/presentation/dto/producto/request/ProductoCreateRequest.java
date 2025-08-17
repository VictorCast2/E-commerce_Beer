package com.application.presentation.dto.producto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;

@Validated
public record ProductoCreateRequest(
        @NotBlank String imagen,
        @NotBlank String nombre,
        @NotBlank @Positive double precio,
        @NotBlank @Positive int stock,
        @NotBlank String descripcion,
        @NotBlank String marca,
        @NotBlank String presentacion
) {
}
