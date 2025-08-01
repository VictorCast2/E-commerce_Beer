package com.application.usuario.dto.request;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotBlank;

@JsonPropertyOrder({"correo", "password"})
public record AuthLoginRequest(@NotBlank(message = "el correo no puede estar en blanco") String correo,
                               @NotBlank(message = "la contraseña no puede estar en blanco") String password) {
}