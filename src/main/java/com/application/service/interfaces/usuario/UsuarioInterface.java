package com.application.service.interfaces.usuario;

import com.application.configuration.Custom.CustomUserDetails;
import com.application.presentation.dto.usuario.request.EditarPasswordRequest;
import com.application.presentation.dto.usuario.request.EditarUsuarioRequest;
import com.application.presentation.dto.usuario.request.CreacionUsuarioRequest;
import com.application.presentation.dto.usuario.response.UsuarioResponse;
import javax.validation.Valid;

public interface UsuarioInterface {
    UsuarioResponse deleteUsuario(@Valid String correo);
    UsuarioResponse crearUsuario(@Valid CreacionUsuarioRequest request);
    UsuarioResponse actualizarUsuario(@Valid EditarUsuarioRequest updateUsuarioRequest, @Valid CustomUserDetails customUserDetails);
    UsuarioResponse actualizarPassword(@Valid EditarPasswordRequest editarPasswordRequest);
}