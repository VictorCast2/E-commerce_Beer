package com.application.service.implementation.historia;

import com.application.persistence.entity.historia.Historia;
import com.application.persistence.repository.HistoriaRepository;
import com.application.service.interfaces.historia.HistoriaService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HistoriaServiceImpl implements HistoriaService {

    private final HistoriaRepository historiaRepository;

    /**
     * Obtener una Historia por Id
     * Este método es de uso interno para otros métodos del servicio
     *
     * @param id ID de la historia a buscar
     * @return La entidad historia encontrada
     * @throws EntityNotFoundException si la historia no existe
     */
    @Override
    public Historia getHistoriaById(Long id) {
        return historiaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Error: La hsitoria con id: " + id + " no existe."));
    }
}
