package com.application.service.interfaces.historia;

import com.application.presentation.dto.historia.request.HistoriaRequest;
import com.application.presentation.dto.historia.response.HistoriaResponse;

import javax.validation.Valid;

public interface HistoriaService {
    HistoriaResponse crearHistoria(@Valid HistoriaRequest historiaRequest);
    HistoriaResponse actualizarHistoria(@Valid HistoriaRequest historiaRequest);
    HistoriaResponse eliminarHistoria(@Valid HistoriaRequest historiaRequest);
    HistoriaResponse encontrarHistoria(@Valid HistoriaRequest historiaRequest);
}