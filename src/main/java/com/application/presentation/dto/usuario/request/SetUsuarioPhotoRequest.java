package com.application.presentation.dto.usuario.request;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

@Validated
public record SetUsuarioPhotoRequest(
        MultipartFile imagenUsuarioNueva,
        String imagenUsuarioOriginal
) {
}
