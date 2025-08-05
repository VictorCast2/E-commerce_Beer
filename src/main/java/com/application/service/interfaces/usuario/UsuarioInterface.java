package com.application.service.interfaces.usuario;

import com.application.presentation.dto.usuario.request.ActualizaccionUsuarioRequest;
import com.application.presentation.dto.usuario.request.CreacionUsuarioRequest;
import com.application.presentation.dto.usuario.response.UsuarioResponse;
import org.springframework.stereotype.Repository;
import javax.validation.Valid;

@Repository
public interface UsuarioInterface {
    UsuarioResponse deleteUsuario(String correo);
    UsuarioResponse crearUsuario(@Valid CreacionUsuarioRequest request);
    UsuarioResponse actualizarUsuario(String correo, @Valid ActualizaccionUsuarioRequest request);
}