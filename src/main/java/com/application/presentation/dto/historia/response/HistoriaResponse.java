package com.application.presentation.dto.historia.response;

import java.time.LocalDate;

public record HistoriaResponse(
        Long id,
        String imagen,
        String titulo,
        String descripcion,
        String historiaCompleta,
        LocalDate fecha,
        boolean activo
) {
}
