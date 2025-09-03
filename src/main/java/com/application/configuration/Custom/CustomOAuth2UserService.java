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

import java.util.Map;
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

        String registrationId = request.getClientRegistration().getRegistrationId(); // "google" o "apple"

        String email = null;
        String nombre = null;
        String apellido = null;
        String imagen = null;

        if ("google".equals(registrationId)) {
            // Atributos de Google
            email = oAuth2User.getAttribute("email");
            nombre = oAuth2User.getAttribute("given_name");
            apellido = oAuth2User.getAttribute("family_name");
            imagen = oAuth2User.getAttribute("picture");
        } else if ("apple".equals(registrationId)) {
            // Atributos de Apple (más limitados)
            email = oAuth2User.getAttribute("email");
            // Apple a veces no devuelve nombre completo después del primer login
            Map<String, Object> name = oAuth2User.getAttribute("name");
            if (name != null) {
                nombre = (String) name.get("firstName");
                apellido = (String) name.get("lastName");
            }
            imagen = null; // Apple no da imagen de perfil
        }

        String password = "OAuth2";

        // Verificar si el usuario ya existe
        Optional<Usuario> existente = usuarioRepository.findByCorreo(email);

        if (existente.isEmpty() && email != null) {
            // Buscar o crear rol USER
            ERol eRol = ERol.PERSONA_CONTACTO;
            Rol rol = rolRepository.findByName(eRol)
                    .orElseGet(() -> rolRepository.save(Rol.builder().name(eRol).build()));

            // Crear usuario nuevo desde OAuth2
            Usuario nuevo = Usuario.builder()
                    .nombres(nombre != null ? nombre : "Usuario")
                    .apellidos(apellido != null ? apellido : "")
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