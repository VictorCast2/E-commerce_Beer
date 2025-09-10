package com.application.presentation.dto.usuario.request;

import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

@Validated
public record EditarUsuarioRequest(
        String cedula,
        String tipoIdentificacion,
        @Size(max = 50, message = "El nombre no puede tener más de 50 caracteres")
        String nombres,
        @Size(max = 50, message = "El apellido no puede tener más de 50 caracteres")
        String apellidos,
        String imagen,
        @Size(min = 10, max = 10, message = "El teléfono debe tener 10 dígitos")
        String telefono,
        String rol,
        String empresa
) {
}