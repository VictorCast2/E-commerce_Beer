package com.application.service.implementation.comentario;

import com.application.persistence.repository.ComentarioRepository;
import com.application.presentation.dto.comentario.request.ComentarioRequest;
import com.application.presentation.dto.comentario.response.ComentarioResponse;
import com.application.service.interfaces.comentario.ComentarioService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ComentarioServiceImpl implements ComentarioService {

    private final ComentarioRepository comentarioRepository;

    @Override
    public ComentarioResponse crearHistoria(ComentarioRequest comentarioRequest) {
        return null;
    }

    @Override
    public ComentarioResponse actualizarHistoria(ComentarioRequest comentarioRequest) {
        return null;
    }

    @Override
    public ComentarioResponse eliminarHistoria(ComentarioRequest comentarioRequest) {
        return null;
    }

    @Override
    public ComentarioResponse encontrarHistoria(ComentarioRequest comentarioRequest) {
        return null;
    }
}