package com.application.service.implementation.usuario;

import com.application.configuration.Custom.CustomUserPrincipal;
import com.application.persistence.entity.rol.Rol;
import com.application.persistence.entity.rol.enums.ERol;
import com.application.persistence.entity.usuario.Usuario;
import com.application.persistence.entity.usuario.enums.EIdentificacion;
import com.application.persistence.repository.RolRepository;
import com.application.persistence.repository.UsuarioRepository;
import com.application.presentation.dto.general.response.GeneralResponse;
import com.application.presentation.dto.general.response.RegisterResponse;
import com.application.presentation.dto.usuario.request.CompleteUsuarioProfileRequest;
import com.application.presentation.dto.usuario.request.CreateUsuarioRequest;
import com.application.presentation.dto.usuario.request.SetUsuarioPhotoRequest;
import com.application.presentation.dto.usuario.request.UpdateUsuarioRequest;
import com.application.service.implementation.ImagenServiceImpl;
import com.application.service.interfaces.usuario.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService, UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;

    private final ImagenServiceImpl imagenService;
    private final PasswordEncoder encoder;

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
        usuario.setImagen("perfil-user.jpg");
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

    @Override
    public RegisterResponse createUser(@Valid CreateUsuarioRequest usuarioRequest) {

        String correo = usuarioRequest.correo();
        if (usuarioRepository.existsByCorreo(correo)) {
            return new RegisterResponse("El correo " + correo + " ya está registrado", false);
        }

        Usuario usuario = Usuario.builder()
                .tipoIdentificacion(usuarioRequest.tipoIdentificacion())
                .numeroIdentificacion(usuarioRequest.numeroIdentificacion())
                .imagen("perfil-user.jpg")
                .nombres(usuarioRequest.nombres())
                .apellidos(usuarioRequest.apellidos())
                .telefono(usuarioRequest.telefono())
                .correo(usuarioRequest.correo())
                .password(encoder.encode(usuarioRequest.password()))
                .build();

        if (usuarioRequest.tipoIdentificacion().equals(EIdentificacion.NIT)) {
            Rol rolPersonaJuridica = rolRepository.findByName(ERol.PERSONA_JURIDICA)
                    .orElseThrow(() -> new EntityNotFoundException("Error: el rol PERSONA_JURIDICA no existe en la BD"));
            usuario.setRol(rolPersonaJuridica);
        } else {
            Rol rolPersonaNatural = rolRepository.findByName(ERol.PERSONA_NATURAL)
                    .orElseThrow(() -> new EntityNotFoundException("Error: el rol PERSONA_NATURAL no exite en la BD"));
            usuario.setRol(rolPersonaNatural);
        }

        usuarioRepository.save(usuario);

        return new RegisterResponse("Usuario creado exitosamente", true);
    }

    @Override
    public GeneralResponse updateUser(CustomUserPrincipal principal, UpdateUsuarioRequest usuarioRequest) {

        Usuario usuarioActualizado = this.getUsuarioByCorreo(principal.getCorreo());

        usuarioActualizado.setTipoIdentificacion(usuarioRequest.tipoIdentificacion());
        usuarioActualizado.setNumeroIdentificacion(usuarioRequest.numeroIdentificacion());
        usuarioActualizado.setNombres(usuarioRequest.nombres());
        usuarioActualizado.setApellidos(usuarioRequest.apellidos());
        usuarioActualizado.setTelefono(usuarioRequest.telefono());
        usuarioActualizado.setCorreo(usuarioRequest.correo());

        usuarioRepository.save(usuarioActualizado);
        return new GeneralResponse("Sus datos se han actualizado exitosamente");
    }

    @Override
    public GeneralResponse setUserPhoto(CustomUserPrincipal principal, SetUsuarioPhotoRequest usuarioPhotoRequest) {

        Usuario usuarioPhoto = this.getUsuarioByCorreo(principal.getCorreo());

        String imagen = imagenService.asignarImagen(usuarioPhotoRequest.imagenUsuarioNueva(), "perfil-usuario");
        if (imagen != null) {
            usuarioPhoto.setImagen(imagen);
        } else {
            usuarioPhoto.setImagen(usuarioPhotoRequest.imagenUsuarioOriginal());
        }

        usuarioRepository.save(usuarioPhoto);

        return new GeneralResponse("Imagen asignada exitosamente");
    }

}
