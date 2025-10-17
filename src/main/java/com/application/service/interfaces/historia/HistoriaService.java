package com.application.service.interfaces.historia;

import com.application.persistence.entity.historia.Historia;
import com.application.presentation.dto.general.response.GeneralResponse;
import com.application.presentation.dto.historia.request.HistoriaCreateRequest;
import com.application.presentation.dto.historia.response.HistoriaResponse;

import java.util.List;

public interface HistoriaService {
    // Consulta
    Historia getHistoriaById(Long id);
    HistoriaResponse getHistoriaResponseById(Long id);
    List<HistoriaResponse> getHistorias();
    List<HistoriaResponse> getHistoriasActivas();

    // CRUD
    GeneralResponse createHistoria(HistoriaCreateRequest historiaRequest);

    // Utils
    HistoriaResponse toResponse(Historia historia);
}
