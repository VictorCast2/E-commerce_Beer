package com.application.configuration.Custom;

import com.application.persistence.entity.rol.Rol;
import com.application.persistence.entity.rol.enums.ERol;
import com.application.persistence.entity.usuario.Usuario;
import com.application.persistence.entity.usuario.enums.EIdentificacion;
import com.application.persistence.repository.RolRepository;
import com.application.persistence.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private UsuarioRepository usuarioRepository;
    private RolRepository rolRepository;
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
            // === Atributos de Google ===
            email = oAuth2User.getAttribute("email");
            nombre = oAuth2User.getAttribute("given_name");
            apellido = oAuth2User.getAttribute("family_name");
            imagen = oAuth2User.getAttribute("picture");
        } else if ("apple".equals(registrationId)) {
            // === Atributos de Apple ===
            email = oAuth2User.getAttribute("email");

            Map<String, Object> name = oAuth2User.getAttribute("name");
            if (name != null) {
                nombre = (String) name.get("firstName");
                apellido = (String) name.get("lastName");
            }

            // Apple no siempre envía nombre después del primer login
            if (nombre == null) nombre = "Usuario";
            if (apellido == null) apellido = "";
            imagen = null;
        }

        // Si no hay email no podemos autenticar
        if (email == null) {
            throw new OAuth2AuthenticationException("No se pudo obtener el correo electrónico del usuario " + registrationId);
        }

        // === Verificar si ya existe ===
        Optional<Usuario> existente = usuarioRepository.findByCorreo(email);

        Usuario usuario;
        if (existente.isPresent()) {
            usuario = existente.get();
        } else {
            // Buscar o crear rol por defecto
            ERol eRol = ERol.PERSONA_CONTACTO;
            Rol rol = rolRepository.findByName(eRol)
                    .orElseGet(() -> rolRepository.save(Rol.builder().name(eRol).build()));

            // Crear nuevo usuario
            usuario = Usuario.builder()
                    .nombres(nombre != null ? nombre : "Usuario")
                    .apellidos(apellido != null ? apellido : " ")
                    .correo(email)
                    .password(encoder.encode("OAuth2")) // Ponerle una contraseña por defecto
                    .rol(rol)
                    .imagen(imagen)
                    .tipoIdentificacion(EIdentificacion.CC)
                    .isEnabled(true)
                    .accountNonExpired(true)
                    .accountNonLocked(true)
                    .credentialsNonExpired(true)
                    .build();
            usuarioRepository.save(usuario);
        }

        // === Devolvemos un usuario compatible con Spring Security ===
        return new DefaultOAuth2User(
                Set.of(() -> usuario.getRol().getName().name()), // authorities
                oAuth2User.getAttributes(),
                "sub" // atributo usado como "username" (para Google y Apple es "sub")
        );

    }

}