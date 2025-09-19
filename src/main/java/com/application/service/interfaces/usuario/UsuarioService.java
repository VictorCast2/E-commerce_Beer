package com.application.service.interfaces.usuario;

import com.application.persistence.entity.usuario.Usuario;
import com.application.presentation.dto.general.response.GeneralResponse;
import com.application.presentation.dto.usuario.request.CompleteUsuarioProfileRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface UsuarioService {

    Usuario getUsuarioByCorreo(String correo);

    GeneralResponse completeUserProfile(OAuth2User principal, CompleteUsuarioProfileRequest completeProfileRequest);
}
