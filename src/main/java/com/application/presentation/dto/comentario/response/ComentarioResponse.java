package com.application.presentation.dto.comentario.response;

import java.time.LocalDate;

public record ComentarioResponse(
        Long id,
        String titulo,
        String mensaje,
        int calificacion,
        LocalDate fecha,
        boolean estado,
        ComentarioUsuarioResponse usuarioResponse,
        ComentarioBlogResponse blogResponse
) {
}
