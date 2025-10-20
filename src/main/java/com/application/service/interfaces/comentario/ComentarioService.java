package com.application.service.interfaces.comentario;

import com.application.persistence.entity.comentario.Comentario;
import com.application.presentation.dto.comentario.response.ComentarioResponse;

public interface ComentarioService {

    // Util
    Comentario getComentarioById(Long id);

    // Util
    ComentarioResponse toResponse(Comentario comentario);
}
