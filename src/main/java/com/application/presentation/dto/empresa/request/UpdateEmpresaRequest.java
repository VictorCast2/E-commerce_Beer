package com.application.presentation.dto.empresa.request;

import com.application.persistence.entity.empresa.enums.ESector;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

@Validated
public record UpdateEmpresaRequest(
        MultipartFile imagen,
        String imagenOriginal,
        @NotBlank String ciudad,
        @NotBlank String direccion,
        @NotBlank String telefono,
        @NotBlank String correo,
        @NotNull ESector sector
) {
}
