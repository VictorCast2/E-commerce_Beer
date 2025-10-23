package com.application.presentation.dto.comentario.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

@Validated
public record ComentarioCreateRequest(
        @NotBlank String titulo,
        @NotBlank String mensaje,
        @Min(1) @Max(5) int calificacion
) {
}
