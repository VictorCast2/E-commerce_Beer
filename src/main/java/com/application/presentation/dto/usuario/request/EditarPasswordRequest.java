package com.application.presentation.dto.usuario.request;

import org.springframework.validation.annotation.Validated;

@Validated
public record EditarPasswordRequest(
        String correo,
        String password,
        String nuevaPassword
) {
}