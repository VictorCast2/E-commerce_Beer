package com.application.service.interfaces.historia;

import com.application.persistence.entity.historia.Historia;

public interface HistoriaService {
    // Consulta
    Historia getHistoriaById(Long id);
}
