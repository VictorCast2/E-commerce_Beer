package com.application.service.implementation.historia;

import com.application.persistence.repository.HistoriaRepository;
import com.application.presentation.dto.historia.request.HistoriaRequest;
import com.application.presentation.dto.historia.response.HistoriaResponse;
import com.application.service.interfaces.historia.HistoriaService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class HistoriaServiceImpl implements HistoriaService {

    private final HistoriaRepository historiaRepository;

    @Override
    public HistoriaResponse crearHistoria(HistoriaRequest historiaRequest) {
        return null;
    }

    @Override
    public HistoriaResponse actualizarHistoria(HistoriaRequest historiaRequest) {
        return null;
    }

    @Override
    public HistoriaResponse eliminarHistoria(HistoriaRequest historiaRequest) {
        return null;
    }

    @Override
    public HistoriaResponse encontrarHistoria(HistoriaRequest historiaRequest) {
        return null;
    }
}