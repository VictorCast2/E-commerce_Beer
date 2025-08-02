package com.application.service.interfaces.usuario;

import com.application.presentation.dto.usuario.request.AuthLoginRequest;
import com.application.presentation.dto.usuario.request.CreacionUsuarioRequest;
import com.application.presentation.dto.usuario.response.UsuarioResponse;

import javax.validation.Valid;

public interface UsuarioInterface {
    UsuarioResponse deleteUsuario(String correo);
    UsuarioResponse loginUser(AuthLoginRequest request);
    UsuarioResponse crearUsuario(@Valid CreacionUsuarioRequest request);
}