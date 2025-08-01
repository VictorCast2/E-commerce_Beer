package com.application.service.implementation.usuario;

import com.application.persistence.entity.rol.Rol;
import com.application.persistence.repository.RolRepository;
import com.application.presentation.dto.usuario.request.AuthLoginRequest;
import com.application.presentation.dto.usuario.request.CreacionUsuarioRequest;
import com.application.presentation.dto.usuario.response.UsuarioResponse;
import com.application.persistence.entity.usuario.Usuario;
import com.application.persistence.repository.UsuarioRepository;
import com.application.service.interfaces.usuario.UsuarioInterface;
import com.application.utils.CustomUserDetails;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.validation.Valid;

@Data
@Service
public class UsuarioServicesImpl implements UserDetailsService, UsuarioInterface {

    @Autowired
    private final UsuarioRepository usuarioRepository;

    @Autowired
    private final RolRepository rolRepository;

    @Lazy
    @Autowired
    private PasswordEncoder encoder;

    /**
     * Método para crear un nuevo usuario.
     * Utiliza el RolRepository para encontrar el rol correspondiente al usuario.
     * La contraseña se cifra antes de guardarla en la base de datos.
     */
    @Override
    public UsuarioResponse crearUsuario(@Valid CreacionUsuarioRequest request) {
        Rol rol = rolRepository.findByERol(request.rol())
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado: " + request.rol()));

        Usuario usuario = Usuario.builder()
                .nombres(request.nombres())
                .apellidos(request.apellidos())
                .cedula(request.cedula())
                .telefono(request.telefono())
                .correo(request.correo())
                .contrasenna(encoder.encode(request.contrasenna()))
                .rol(rol)
                .empresa(null)
                .isEnabled(true)
                .accountNoExpired(true)
                .accountNoLocked(true)
                .credentialNoExpired(true)
                .build();

        usuarioRepository.save(usuario);
        return new UsuarioResponse("✅ Usuario creado exitosamente");
    }

    /**
     * Método para iniciar sesión de un usuario.
     * Utiliza el correo y la contraseña proporcionados en el AuthLoginRequest.
     * Si las credenciales son correctas, se establece la autenticación en el contexto de seguridad.
     */
    @Override
    public UsuarioResponse loginUser(AuthLoginRequest request) {
        Authentication authentication = this.authenticate(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new UsuarioResponse("🔐 Inicio de sesión exitoso");
    }

    /**
     * Método privado para autenticar al usuario.
     * Comprueba si las credenciales son correctas y devuelve un objeto Authentication.
     * Si las credenciales son incorrectas, lanza una excepción BadCredentialsException.
     */
    private Authentication authenticate(AuthLoginRequest request) {
        UserDetails userDetails = loadUserByUsername(request.correo());

        if (!encoder.matches(request.password(), userDetails.getPassword())) {
            throw new BadCredentialsException("❌ Contraseña incorrecta");
        }

        return new UsernamePasswordAuthenticationToken(
                userDetails, // <- ya no usamos el String del correo, sino el objeto
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );
    }

    /**
     * Método para encontrar un usuario por su correo electrónico.
     * Si el usuario no existe, lanza una excepción UsernameNotFoundException.
     */
    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("El correo no existe: " + correo));
        return new CustomUserDetails(usuario);
    }

    /**
     * Método para eliminar un usuario por su correo electrónico.
     * Busca al usuario en la base de datos y lo elimina si existe.
     * Devuelve un mensaje de éxito en caso de eliminación exitosa.
     */
    @Override
    public UsuarioResponse deleteUsuario(String correo) {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("El correo no existe: " + correo));
        usuarioRepository.delete(usuario);
        return new UsuarioResponse("🗑️ Usuario eliminado exitosamente");
    }

}