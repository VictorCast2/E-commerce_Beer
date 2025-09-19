package com.application.presentation.dto.usuario.request;

import com.application.persistence.entity.usuario.enums.EIdentificacion;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public record CompleteUsuarioProfileRequest(
        @NotNull EIdentificacion tipoIdentificacion,
        @NotBlank String numeroIdentificacion,
        @NotBlank String telefono,
        @NotBlank String password
) {
}
