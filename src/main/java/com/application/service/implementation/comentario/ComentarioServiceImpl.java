package com.application.service.implementation.comentario;

import com.application.persistence.entity.comentario.Comentario;
import com.application.persistence.repository.ComentarioRepository;
import com.application.presentation.dto.comentario.response.ComentarioBlogResponse;
import com.application.presentation.dto.comentario.response.ComentarioResponse;
import com.application.presentation.dto.comentario.response.ComentarioUsuarioResponse;
import com.application.service.interfaces.comentario.ComentarioService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComentarioServiceImpl implements ComentarioService {

    private final ComentarioRepository comentarioRepository;

    /**
     * Obtener un comentario por id.
     * Este método es de uso interno para otros métodos del servicio
     *
     * @param id ID del comentario a buscar
     * @return la entidad comentario encontrada
     * @throws EntityNotFoundException si el comentario no exite
     */
    @Override
    public Comentario getComentarioById(Long id) {
        return comentarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("El comentario con id " + id + " no existe"));
    }

    /**
     * Obtiene todos los comentarios registrados (activos e inactivos).
     * Uso común para el panel administrativo, vista de reseñas.
     * Los datos a incluir son -> tituloBlog, nombreUsuario, mensaje, calificación y fecha
     *
     * @return Lista de DTOs con datos de cada comentario
     */
    @Override
    public List<ComentarioResponse> getComentarios() {
        List<Comentario> comentarios = comentarioRepository.findAll();
        return comentarios.stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * Convierte una entidad Comentario a su DTO de respuesta, incluyendo
     * algunos datos del Usuario y el título de la Historio.
     * Para uso interno del servicio en los métodos de búsqueda
     *
     * @param comentario Entidad comentario a convertir
     * @return DTO con la información completa del comentario
     */
    @Override
    public ComentarioResponse toResponse(Comentario comentario) {
        return new ComentarioResponse(
                comentario.getComentarioId(),
                comentario.getTitulo(),
                comentario.getMensaje(),
                comentario.getCalificacion(),
                comentario.getFecha(),
                comentario.isActivo(),
                new ComentarioUsuarioResponse(
                        comentario.getUsuario().getNombres(),
                        comentario.getUsuario().getApellidos(),
                        comentario.getUsuario().getCorreo()
                ),
                new ComentarioBlogResponse(
                        comentario.getHistoria().getTitulo()
                )
        );
    }
}
