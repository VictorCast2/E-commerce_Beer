package com.application.service.interfaces.comentario;

import com.application.configuration.custom.CustomUserPrincipal;
import com.application.persistence.entity.comentario.Comentario;
import com.application.presentation.dto.comentario.request.ComentarioCreateRequest;
import com.application.presentation.dto.comentario.response.ComentarioResponse;
import com.application.presentation.dto.general.response.BaseResponse;

import java.util.List;

public interface ComentarioService {

    // Util
    Comentario getComentarioById(Long id);

    // Consulta
    List<ComentarioResponse> getComentarios();
    List<ComentarioResponse> getComentariosActivosByHistoriaId(Long historiaId);

    // CRUD
    BaseResponse createComentario(Long historiaId, CustomUserPrincipal principal, ComentarioCreateRequest comentarioRequest);

    // Util
    ComentarioResponse toResponse(Comentario comentario);
}
