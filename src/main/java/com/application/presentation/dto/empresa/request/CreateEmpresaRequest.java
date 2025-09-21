package com.application.presentation.dto.empresa.request;

import com.application.persistence.entity.empresa.enums.ESector;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public record CreateEmpresaRequest(
        @NotBlank String nit,
        @NotBlank String razonSocial,
        @NotBlank String ciudad,
        @NotBlank String direccion,
        @NotBlank String telefono,
        @NotBlank String correo,
        @NotNull ESector sector
) {
}
