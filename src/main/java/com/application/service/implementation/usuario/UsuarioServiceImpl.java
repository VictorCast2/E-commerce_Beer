package com.application.service.implementation.usuario;

import com.application.configuration.Custom.CustomUserDetails;
import com.application.persistence.entity.rol.Rol;
import com.application.persistence.entity.rol.enums.ERol;
import com.application.persistence.entity.usuario.Usuario;
import com.application.persistence.entity.usuario.enums.EIdentificacion;
import com.application.persistence.repository.UsuarioRepository;
import com.application.presentation.dto.general.response.GeneralResponse;
import com.application.presentation.dto.usuario.request.CompleteUsuarioProfileRequest;
import com.application.service.interfaces.usuario.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService, UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("ERROR: el correo '" + correo + "' no existe"));

        return new CustomUserDetails(usuario);
    }

    @Override
    public Usuario getUsuarioByCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new EntityNotFoundException("Error: El correo '" + correo + "' no exite"));
    }

    @Override
    public GeneralResponse completeUserProfile(OAuth2User principal, CompleteUsuarioProfileRequest completeProfileRequest) {
        Usuario usuario = this.getUsuarioByCorreo(principal.getName());
        String encryptedPassword = encoder.encode(completeProfileRequest.password());

        usuario.setTipoIdentificacion(completeProfileRequest.tipoIdentificacion());
        usuario.setNumeroIdentificacion(completeProfileRequest.numeroIdentificacion());
        usuario.setNombres(completeProfileRequest.nombres());
        usuario.setApellidos(completeProfileRequest.apellidos());
        usuario.setTelefono(completeProfileRequest.telefono());
        usuario.setPassword(encryptedPassword);

        if (completeProfileRequest.tipoIdentificacion().equals(EIdentificacion.NIT)) {
            usuario.setRol(Rol.builder().name(ERol.PERSONA_JURIDICA).build());
        } else {
            usuario.setRol(Rol.builder().name(ERol.PERSONA_NATURAL).build());
        }

        usuarioRepository.save(usuario);
        return new GeneralResponse("Registro completado exitosamente.");
    }
}
