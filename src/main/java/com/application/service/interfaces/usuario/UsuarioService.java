package com.application.service.interfaces.usuario;

import com.application.configuration.Custom.CustomUserPrincipal;
import com.application.persistence.entity.usuario.Usuario;
import com.application.presentation.dto.general.response.GeneralResponse;
import com.application.presentation.dto.usuario.request.CompleteUsuarioProfileRequest;

public interface UsuarioService {

    Usuario getUsuarioByCorreo(String correo);

    GeneralResponse completeUserProfile(CustomUserPrincipal principal, CompleteUsuarioProfileRequest completeProfileRequest);
}
