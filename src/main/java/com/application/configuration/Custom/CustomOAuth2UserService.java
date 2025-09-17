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

        // === Atributos de Google ===
        String email = oAuth2User.getAttribute("email");
        String nombre = oAuth2User.getAttribute("given_name");
        String apellido = oAuth2User.getAttribute("family_name");
        String imagen = oAuth2User.getAttribute("picture");

        // Si no hay email no podemos autenticar
        if (email == null) {
            throw new OAuth2AuthenticationException(" No se pudo obtener el correo electrónico del usuario: " + email);
        }

        // === Verificar si ya existe ===
        Usuario existente = usuarioService.encontrarCorreo(email);
        ERol eRol = ERol.PERSONA_CONTACTO;
        Rol rol = rolRepository.findByName(eRol)
                .orElseGet(() -> rolRepository.save(Rol.builder().name(eRol).build()));

        if (existente == null) {
            CreacionUsuarioRequest nuevoUsuarioRequest = new CreacionUsuarioRequest(
                    null, "CC", nombre, apellido, imagen, null, email, "OAuth2", rol.getName().name());
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
                new SimpleGrantedAuthority("ROLE_" + rol.getName().name()));

        return new DefaultOAuth2User(
                authorities,
                oAuth2User.getAttributes(),
                request.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName());

    }

}