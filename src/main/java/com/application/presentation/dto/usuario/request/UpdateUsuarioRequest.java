package com.application.presentation.dto.usuario.request;

import com.application.persistence.entity.usuario.enums.EIdentificacion;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public record UpdateUsuarioRequest(
        @NotNull EIdentificacion tipoIdentificacion,
        @NotBlank String numeroIdentificacion,
        @NotBlank String nombres,
        @NotBlank String apellidos,
        @NotBlank String telefono,
        @Email String correo,
        @NotBlank String password
        // De ser necesario, colocar un dto para actualizar también la empresa
) {
}
