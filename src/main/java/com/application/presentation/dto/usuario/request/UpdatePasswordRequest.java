package com.application.presentation.dto.usuario.request;

import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

@Validated
public record UpdatePasswordRequest(
        @NotBlank String currentPassword,
        @NotBlank String newPassword
) {
}
