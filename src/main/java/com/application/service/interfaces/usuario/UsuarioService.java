package com.application.service.interfaces.usuario;

import com.application.configuration.custom.CustomUserPrincipal;
import com.application.persistence.entity.usuario.Usuario;
import com.application.presentation.dto.general.response.GeneralResponse;
import com.application.presentation.dto.general.response.RegisterResponse;
import com.application.presentation.dto.usuario.request.CompleteUsuarioProfileRequest;
import com.application.presentation.dto.usuario.request.CreateUsuarioRequest;
import com.application.presentation.dto.usuario.request.UpdateUsuarioRequest;

public interface UsuarioService {

    Usuario getUsuarioByCorreo(String correo);

    GeneralResponse completeUserProfile(CustomUserPrincipal principal, CompleteUsuarioProfileRequest completeProfileRequest);
    RegisterResponse createUser(CreateUsuarioRequest usuarioRequest);
    GeneralResponse updateUser(CustomUserPrincipal principal, UpdateUsuarioRequest usuarioRequest);
}
