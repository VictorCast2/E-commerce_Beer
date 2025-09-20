package com.application.configuration.Custom;

import com.application.persistence.entity.rol.Rol;
import com.application.persistence.entity.rol.enums.ERol;
import com.application.persistence.entity.usuario.Usuario;
import com.application.persistence.repository.RolRepository;
import com.application.persistence.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOauth2UserService extends DefaultOAuth2UserService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String email = oAuth2User.getAttribute("email");
        String nombre = oAuth2User.getAttribute("given_name");
        String apellido = oAuth2User.getAttribute("family_name");

        System.out.println("MENSAJE DE PRUEBA");

        Usuario usuario = usuarioRepository.findByCorreo(email)
                .orElse(null);

        if (usuario == null) {

            Rol rolInvitado = rolRepository.findByName(ERol.INVITADO)
                    .orElseGet(() -> rolRepository.save(Rol.builder().name(ERol.INVITADO).build()));

            usuario = Usuario.builder()
                    .correo(email)
                    .nombres(nombre)
                    .apellidos(apellido)
                    .rol(rolInvitado)
                    .build();

            usuarioRepository.save(usuario);
        }

        return new CustomUserPrincipal(usuario, oAuth2User.getAttributes());
    }
}



