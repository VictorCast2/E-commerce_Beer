package com.application.presentation.dto.pack.request;

import com.application.persistence.entity.pack.enums.ETipo;
import jakarta.validation.constraints.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public record PackCreateRequest(
        @NotBlank String imagen,
        @NotBlank String nombre,
        @NotNull @Positive double precio,
        @NotNull @Min(1) int stock,
        @NotBlank String descripcion,
        @NotNull ETipo tipo,
        @NotEmpty List<Long> categoriaIds,
        @NotEmpty List<ProductoCantidadRequest> productos
) {
}
