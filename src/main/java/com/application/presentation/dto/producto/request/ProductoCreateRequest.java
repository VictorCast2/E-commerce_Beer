package com.application.presentation.dto.producto.request;

import com.application.persistence.entity.producto.enums.ETipo;
import jakarta.validation.constraints.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public record ProductoCreateRequest(
        @NotBlank String codigoProducto,
        @NotBlank String imagen,
        @NotBlank String nombre,
        @NotBlank String marca,
        @NotBlank String pais,
        @NotNull ETipo tipo,
        @NotNull @Positive double precio,
        @NotNull @Min(1) int stock,
        @NotBlank String descripcion,
        @NotEmpty List<Long> categoriaIds
) {
}