package com.application.configuration.Custom;

import com.application.persistence.entity.rol.Rol;
import com.application.persistence.entity.rol.enums.ERol;
import com.application.persistence.entity.usuario.Usuario;
import com.application.persistence.repository.RolRepository;
import com.application.presentation.dto.usuario.request.CreacionUsuarioRequest;
import com.application.service.implementation.usuario.UsuarioServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Map;
import java.util.Set;

@Service
@AllArgsConstructor
@Transactional
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private UsuarioServiceImpl usuarioService;
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

            // === Nombre y apellido vienen en un mapa anidado ===
            Map<String, Object> name = oAuth2User.getAttribute("name");
            if (name != null) {
                nombre = (String) name.get("firstName");
                apellido = (String) name.get("lastName");
            }

            // Apple no siempre envía nombre después del primer login
            if (nombre == null) nombre = "Usuario";
            if (apellido == null) apellido = "";
        }

        // Si no hay email no podemos autenticar
        if (email == null) {
            throw new OAuth2AuthenticationException("No se pudo obtener el correo electrónico del usuario " + registrationId);
        }

        // === Verificar si ya existe ===
        Usuario existente = usuarioService.encontrarCorreo(email);

        ERol eRol = ERol.PERSONA_CONTACTO;
        Rol rol = rolRepository.findByName(eRol)
                .orElseGet(() -> rolRepository.save(Rol.builder().name(eRol).build()));

        if (existente == null) {
            CreacionUsuarioRequest nuevoUsuarioRequest = new CreacionUsuarioRequest(
                    null, "CC", nombre, apellido, imagen, null, email, "OAuth2", rol.getName().name()
            );
            usuarioService.crearUsuario(nuevoUsuarioRequest);
            System.out.println("✅ Usuario guardado en DB: " + nuevoUsuarioRequest.correo());
        } else {
            existente.setNombres(nombre);
            existente.setApellidos(apellido);
            existente.setImagen(imagen);
            // usuarioService.actualizarUsuario(existente);
            System.out.println("✅ Usuario actualizado en DB: " + existente.getCorreo());
        }

        Set<GrantedAuthority> authorities = Set.of(
                new SimpleGrantedAuthority("ROLE_" + rol.getName().name())
        );

        return new DefaultOAuth2User(
                authorities,
                oAuth2User.getAttributes(),
                request.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName()
        );

    }

}