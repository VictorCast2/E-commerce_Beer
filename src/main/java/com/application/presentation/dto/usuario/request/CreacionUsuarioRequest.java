package com.application.presentation.dto.usuario.request;

import com.application.persistence.entity.rol.enums.ERol;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreacionUsuarioRequest(
        @NotBlank(message = "La cédula es obligatoria")
        String cedula,

        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 50, message = "El nombre no puede tener más de 50 caracteres")
        String nombres,

        @NotBlank(message = "El apellido es obligatorio")
        @Size(max = 50, message = "El apellido no puede tener más de 50 caracteres")
        String apellidos,

        @NotBlank(message = "El teléfono es obligatorio")
        @Size(min = 10, max = 10, message = "El teléfono debe tener 10 dígitos")
        int telefono,

        @NotBlank(message = "El correo es obligatorio")
        @Email(message = "El correo debe ser válido")
        String correo,

        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 4, message = "La contraseña debe tener al menos 6 caracteres")
        String contrasenna,

        @NotBlank(message = "El rol es obligatorio")
        ERol rol
        ) {
}