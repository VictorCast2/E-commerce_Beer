package com.application.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

@Validated
public record CategoriaCreateRequest(
        @NotBlank String nombre,
        @NotBlank String descripcion
) {
}
