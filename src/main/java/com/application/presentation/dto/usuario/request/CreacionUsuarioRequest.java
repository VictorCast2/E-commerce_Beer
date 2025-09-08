package com.application.presentation.dto.usuario.request;

import com.application.persistence.entity.empresa.Empresa;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

@Validated
public record CreacionUsuarioRequest(
        String cedula,
        String tipoIdentificacion,
        @Size(max = 50, message = "El nombre no puede tener más de 50 caracteres")
        String nombres,
        @Size(max = 50, message = "El apellido no puede tener más de 50 caracteres")
        String apellidos,
        String imagen,
        @Size(min = 10, max = 10, message = "El teléfono debe tener 10 dígitos")
        String telefono,
        @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "El correo debe ser válido")
        @Email(message = "El correo debe ser válido")
        String correo,
        String password,
        String rol
) {
}