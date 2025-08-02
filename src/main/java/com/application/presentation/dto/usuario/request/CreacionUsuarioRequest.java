package com.application.presentation.dto.usuario.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class CreacionUsuarioRequest {

        @NotBlank(message = "La cédula es obligatoria")
        private String cedula;

        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 50, message = "El nombre no puede tener más de 50 caracteres")
        private String nombres;

        @NotBlank(message = "El apellido es obligatorio")
        @Size(max = 50, message = "El apellido no puede tener más de 50 caracteres")
        private String apellidos;

        @NotBlank(message = "El teléfono es obligatorio")
        @Size(min = 10, max = 10, message = "El teléfono debe tener 10 dígitos")
        private String telefono;

        @NotBlank(message = "El correo es obligatorio")
        @Email(message = "El correo debe ser válido")
        private String correo;

        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
        private String contrasenna;

        @NotBlank(message = "El rol es obligatorio")
        private Set<String> roles;
}