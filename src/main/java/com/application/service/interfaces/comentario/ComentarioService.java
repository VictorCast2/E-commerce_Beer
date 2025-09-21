package com.application.service.interfaces.comentario;

import com.application.presentation.dto.comentario.request.ComentarioRequest;
import com.application.presentation.dto.comentario.response.ComentarioResponse;
import javax.validation.Valid;

public interface ComentarioService {
    ComentarioResponse crearHistoria(@Valid ComentarioRequest comentarioRequest);
    ComentarioResponse actualizarHistoria(@Valid ComentarioRequest comentarioRequest);
    ComentarioResponse eliminarHistoria(@Valid ComentarioRequest comentarioRequest);
    ComentarioResponse encontrarHistoria(@Valid ComentarioRequest comentarioRequest);
}