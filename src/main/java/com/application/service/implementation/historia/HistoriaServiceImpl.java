package com.application.service.implementation.historia;

import com.application.persistence.repository.HistoriaRepository;
import com.application.service.interfaces.historia.HistoriaService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class HistoriaServiceImpl implements HistoriaService {

    private final HistoriaRepository historiaRepository;

}