package com.application.service.implementation.usuario;

import com.application.configuration.Custom.CustomUserPrincipal;
import com.application.persistence.entity.rol.Rol;
import com.application.persistence.entity.rol.enums.ERol;
import com.application.persistence.entity.usuario.Usuario;
import com.application.persistence.entity.usuario.enums.EIdentificacion;
import com.application.persistence.repository.RolRepository;
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
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService, UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("ERROR: el correo '" + correo + "' no existe"));

        return new CustomUserPrincipal(usuario);
    }

    @Override
    public Usuario getUsuarioByCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new EntityNotFoundException("Error: El correo '" + correo + "' no exite"));
    }

    @Override
    public GeneralResponse completeUserProfile(CustomUserPrincipal principal, CompleteUsuarioProfileRequest completeProfileRequest) {
        Usuario usuario = this.getUsuarioByCorreo(principal.getCorreo());
        String encryptedPassword = encoder.encode(completeProfileRequest.password());

        usuario.setTipoIdentificacion(completeProfileRequest.tipoIdentificacion());
        usuario.setNumeroIdentificacion(completeProfileRequest.numeroIdentificacion());
        usuario.setNombres(completeProfileRequest.nombres());
        usuario.setApellidos(completeProfileRequest.apellidos());
        usuario.setTelefono(completeProfileRequest.telefono());
        usuario.setPassword(encryptedPassword);

        if (completeProfileRequest.tipoIdentificacion().equals(EIdentificacion.NIT)) {
            Rol rolPersonaJuridica = rolRepository.findByName(ERol.PERSONA_JURIDICA)
                            .orElseThrow(() -> new EntityNotFoundException("Error: el rol PERSONA_JURIDICA no existe en la BD"));
            usuario.setRol(rolPersonaJuridica);
        } else {
            Rol rolPersonaNatural = rolRepository.findByName(ERol.PERSONA_NATURAL)
                            .orElseThrow(() -> new EntityNotFoundException("Error: el rol PERSONA_NATURAL no exite en la BD"));
            usuario.setRol(rolPersonaNatural);
        }

        usuarioRepository.save(usuario);
        return new GeneralResponse("Registro completado exitosamente.");
    }
}
