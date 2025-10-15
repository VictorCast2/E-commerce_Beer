package com.application.service.interfaces.historia;

import com.application.persistence.entity.historia.Historia;
import com.application.presentation.dto.historia.response.HistoriaResponse;

public interface HistoriaService {
    // Consulta
    Historia getHistoriaById(Long id);

    // Utils
    HistoriaResponse toResponse(Historia historia);
}
