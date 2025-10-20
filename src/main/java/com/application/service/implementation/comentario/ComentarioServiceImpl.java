package com.application.service.implementation.comentario;

import com.application.persistence.entity.comentario.Comentario;
import com.application.persistence.repository.ComentarioRepository;
import com.application.service.interfaces.comentario.ComentarioService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ComentarioServiceImpl implements ComentarioService {

    private final ComentarioRepository comentarioRepository;

    /**
     * Obtener un comentario por id.
     * Este método es de uso interno para otros métodos del servicio
     *
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
}
