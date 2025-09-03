package com.application.service.implementation.comentario;

import com.application.persistence.repository.ComentarioRepository;
import com.application.service.interfaces.comentario.ComentarioService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ComentarioServiceImpl implements ComentarioService {

    private final ComentarioRepository comentarioRepository;

}