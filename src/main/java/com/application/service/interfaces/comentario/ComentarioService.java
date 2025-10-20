package com.application.service.interfaces.comentario;

import com.application.persistence.entity.comentario.Comentario;
import com.application.presentation.dto.comentario.response.ComentarioResponse;

import java.util.List;

public interface ComentarioService {

    // Util
    Comentario getComentarioById(Long id);

    // Consulta
    List<ComentarioResponse> getComentarios();

    // Util
    ComentarioResponse toResponse(Comentario comentario);
}
