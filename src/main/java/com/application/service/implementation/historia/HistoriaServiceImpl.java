package com.application.service.implementation.historia;

import com.application.persistence.entity.historia.Historia;
import com.application.persistence.repository.HistoriaRepository;
import com.application.presentation.dto.historia.response.HistoriaResponse;
import com.application.service.interfaces.historia.HistoriaService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    /**
     * Obtiene una historia como DTO de respuesta por su ID
     * Este método es para la presentación de la historia en la pagina de Historia-Descripción
     *
     * @param id ID de la historia a buscar
     * @return DTO con la información de la historia, no incluye sus comentarios.
     */
    @Override
    public HistoriaResponse getHistoriaResponseById(Long id) {
        Historia historia = this.getHistoriaById(id);
        return this.toResponse(historia);
    }

    /**
     * Obtiene todas las historias registradas (activas e inactivas)
     * Uso común para el panel de administrador en la vista de blog
     *
     * @return Lista de DTO con los datos de la historia
     * @apiNote Solo se mostrarán los atributos imagen, título, fecha y activo.
     */
    @Override
    public List<HistoriaResponse> getHistorias() {
        List<Historia> historias = historiaRepository.findAll();
        return historias.stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * Obtiene todas las historias registradas con estado activo
     * Uso común para el ecommerce en la sección de blog
     *
     * @return Lista de DTO con historias activas
     * @apiNote Solo se mostrarán los atributos imagen, título, descripción y fecha
     */
    @Override
    public List<HistoriaResponse> getHistoriasActivas() {
        List<Historia> historias = historiaRepository.findByActivoTrue();
        return historias.stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * Convierte una entidad Historia a su DTO de respuesta,
     * no incluye la lista de comentarios
     * Para uso interno del Servicio en los métodos de búsqueda
     *
     * @param historia Entidad Historia a convertir
     * @return DTO con la información de la Historia
     */
    @Override
    public HistoriaResponse toResponse(Historia historia) {
        return new HistoriaResponse(
                historia.getHistoriaId(),
                historia.getImagen(),
                historia.getTitulo(),
                historia.getDescripcion(),
                historia.getHistoriaCompleta(),
                historia.getFecha(),
                historia.isActivo()
        );
    }
}
