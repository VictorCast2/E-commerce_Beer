package com.application.configuration.Custom;

import com.application.persistence.entity.rol.Rol;
import com.application.persistence.entity.rol.enums.ERol;
import com.application.persistence.entity.usuario.Usuario;
import com.application.persistence.repository.RolRepository;
import com.application.persistence.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(request);

        String email = oAuth2User.getAttribute("email");
        String nombre = oAuth2User.getAttribute("given_name");
        String apellido = oAuth2User.getAttribute("family_name");
        String imagen = oAuth2User.getAttribute("picture");
        String password = "OAuth2";

        // Verificar si el usuario ya existe
        Optional<Usuario> existente = usuarioRepository.findByCorreo(email);

        if (existente.isEmpty()) {
            // Buscar o crear rol USER
            ERol eRol = ERol.PERSONA_CONTACTO;
            Rol rol = rolRepository.findByName(eRol)
                    .orElseGet(() -> rolRepository.save(Rol.builder().name(eRol).build()));

            // Crear usuario nuevo desde OAuth2
            Usuario nuevo = Usuario.builder()
                    .nombres(nombre)
                    .apellidos(apellido)
                    .cedula(null)
                    .telefono(null)
                    .rol(rol)
                    .correo(email)
                    .password(encoder.encode(password))
                    .imagen(imagen)
                    .empresa(null)
                    .isEnabled(true)
                    .accountNonExpired(true)
                    .accountNonLocked(true)
                    .credentialsNonExpired(true)
                    .build();
            usuarioRepository.save(nuevo);
        }
        return oAuth2User;
    }

}