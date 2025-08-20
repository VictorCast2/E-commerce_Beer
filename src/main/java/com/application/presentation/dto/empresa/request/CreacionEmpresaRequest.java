package com.application.presentation.dto.empresa.request;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreacionEmpresaRequest {

    private String nit;

    private String razonSocial;

    private String ciudad;

    private String direccion;

    private String telefono;

    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "El correo debe ser válido")
    @Email(message = "El correo debe ser válido")
    private String correo;

    private String eSector;

    private boolean activo;

}