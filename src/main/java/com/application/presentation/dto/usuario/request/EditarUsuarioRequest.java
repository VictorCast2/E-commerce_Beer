package com.application.presentation.dto.usuario.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EditarUsuarioRequest {

    private String cedula;

    @Size(max = 50, message = "El nombre no puede tener más de 50 caracteres")
    private String nombres;

    @Size(max = 50, message = "El apellido no puede tener más de 50 caracteres")
    private String apellidos;

    private String imagen;

    @Size(min = 10, max = 10, message = "El teléfono debe tener 10 dígitos")
    private String telefono;

    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "El correo debe ser válido")
    @Email(message = "El correo debe ser válido")
    private String correo;

    private String password;

    private String rol;

    private String empresa;

}