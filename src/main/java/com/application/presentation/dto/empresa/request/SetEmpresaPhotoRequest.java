package com.application.presentation.dto.empresa.request;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

@Validated
public record SetEmpresaPhotoRequest(
        MultipartFile imagenEmpresaNueva,
        String imagenEmpresaOriginal
) {
}
