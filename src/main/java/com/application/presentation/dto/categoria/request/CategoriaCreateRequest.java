package com.application.presentation.dto.categoria.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Validated
public record CategoriaCreateRequest(
        @NotNull MultipartFile imagen,
        @NotBlank String nombre,
        @NotBlank String descripcion,
        boolean activo,
        @NotEmpty List<String> subcategorias
) {
}
