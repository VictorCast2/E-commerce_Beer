package com.application.service.implementation.usuario;

import com.application.configuration.Custom.CustomUserDetails;
import com.application.persistence.entity.empresa.Empresa;
import com.application.persistence.entity.rol.Rol;
import com.application.persistence.entity.rol.enums.ERol;
import com.application.persistence.entity.usuario.enums.EIdentificacion;
import com.application.persistence.repository.EmpresaRepository;
import com.application.persistence.repository.RolRepository;
import com.application.presentation.dto.usuario.request.EditarPasswordRequest;
import com.application.presentation.dto.usuario.request.EditarUsuarioRequest;
import com.application.presentation.dto.usuario.request.CreacionUsuarioRequest;
import com.application.presentation.dto.usuario.response.UsuarioResponse;
import com.application.persistence.entity.usuario.Usuario;
import com.application.persistence.repository.UsuarioRepository;
import com.application.service.interfaces.usuario.UsuarioInterface;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UsuarioServiceImpl implements UsuarioInterface, UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final EmpresaRepository empresaRepository;
    private final RolRepository rolRepository;

    @Lazy
    private PasswordEncoder encoder;

    /**
     * Método para encontrar un usuario por su correo electrónico.
     * Si el usuario no se encuentra, lanza una excepción.
     */
    @Override
    public Usuario encontrarCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Error: el correo " + correo + " no existe."));
    }

    /**
     * Método para crear un nuevo usuario.
     * Utiliza el RolRepository para encontrar el rol correspondiente al usuario.
     * La contraseña se cifra antes de guardarla en la base de datos.
     */
    @Override
    public UsuarioResponse crearUsuario(CreacionUsuarioRequest request) {
        ERol eRol;
        if (request.rol() == null || request.rol().isBlank()) {
            eRol = ERol.valueOf("PERSONA_CONTACTO");
        } else {
            eRol = ERol.valueOf(request.rol().toUpperCase());
        }

        EIdentificacion eIdentificacion;
        if (request.tipoIdentificacion() == null || request.tipoIdentificacion().isBlank()) {
            eIdentificacion = EIdentificacion.valueOf("CC");
        } else {
            eIdentificacion = EIdentificacion.valueOf(request.tipoIdentificacion().toUpperCase());
        }

        // Buscar o crear el rol
        Rol rol = rolRepository.findByName(eRol)
                .orElseGet(() -> rolRepository.save(Rol.builder().name(eRol).build()));

        // Crear el usuario
        Usuario usuario = Usuario.builder()
                .nombres(request.nombres())
                .apellidos(request.apellidos())
                .tipoIdentificacion(eIdentificacion)
                .cedula(request.cedula())
                .telefono(request.telefono())
                .rol(rol)
                .correo(request.correo())
                .password(encoder.encode(request.password()))
                .imagen(request.imagen())
                .isEnabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .build();
        usuarioRepository.save(usuario);
        return new UsuarioResponse(" Usuario creado exitosamente");
    }

    /**
     * Método para cargar un usuario por su correo electrónico.
     * Utiliza el UserDetailsService de Spring Security para autenticar al usuario.
     * Si el usuario no se encuentra, lanza una excepción.
     */
    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        System.out.println("Intentando autenticar al usuario: ".concat(correo));

        Usuario usuario = usuarioRepository.findByCorreo(correo).orElseThrow(() -> {
            System.out.println("Usuario no encontrado en la base de datos ".concat(correo));
            return new UsernameNotFoundException("Usuario no encontrado");
        });

        System.out.println("Usuario encontrado: " +  usuario.getCorreo()+
                " | Rol sin formato: " + usuario.getRol().getName() +
                " | Rol con formato: " + usuario.getAuthorities()+
                " | Contraseña (hash): " + usuario.getPassword()
        );

        return new User(
                usuario.getCorreo(),
                usuario.getPassword(),
                true,
                true,
                true,
                true,
                usuario.getAuthorities()
        );
    }

    /**
     * Método para actualizar un usuario existente.
     * Busca al usuario por su correo electrónico y actualiza sus datos.
     * La contraseña se cifra antes de guardarla en la base de datos.
     * Si el usuario no existe, lanza una excepción.
     */
    @Override
    public UsuarioResponse actualizarUsuario(EditarUsuarioRequest updateUsuarioRequest, CustomUserDetails customUserDetails) {

        Usuario usuarioActualizado  = usuarioRepository.findByCorreo(customUserDetails.getCorreo())
                .orElseThrow(() -> new UsernameNotFoundException("El correo no existe: " + customUserDetails.getCorreo()));

        String correoAnterior = usuarioActualizado.getCorreo();

        if (updateUsuarioRequest.nombres() != null && !updateUsuarioRequest.nombres().isBlank()) {
            usuarioActualizado.setNombres(updateUsuarioRequest.nombres());
        }

        if (updateUsuarioRequest.apellidos() != null && !updateUsuarioRequest.apellidos().isBlank()) {
            usuarioActualizado.setApellidos(updateUsuarioRequest.apellidos());
        }

        if (updateUsuarioRequest.cedula() != null && !updateUsuarioRequest.cedula().isBlank()) {
            usuarioActualizado.setCedula(updateUsuarioRequest.cedula());
        }

        if (updateUsuarioRequest.telefono() != null && !updateUsuarioRequest.telefono().isBlank()) {
            usuarioActualizado.setTelefono(updateUsuarioRequest.telefono());
        }

        if (updateUsuarioRequest.imagen() != null && !updateUsuarioRequest.imagen().isBlank()) {
            usuarioActualizado.setImagen(updateUsuarioRequest.imagen());
        }

        if (updateUsuarioRequest.empresa() != null && !updateUsuarioRequest.empresa().isBlank()) {
            Empresa empresa = empresaRepository.findByNit(updateUsuarioRequest.empresa())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "No existe una empresa con NIT: " + updateUsuarioRequest.empresa()
                    ));
            usuarioActualizado.setEmpresa(empresa);
        }

        if (updateUsuarioRequest.rol() != null && !updateUsuarioRequest.rol().isBlank()) {
            ERol eRol = ERol.valueOf(updateUsuarioRequest.rol().toUpperCase());
            Rol rol = rolRepository.findByName(eRol)
                    .orElseGet(() -> rolRepository.save(Rol.builder().name(eRol).build()));
            usuarioActualizado.setRol(rol);
        }

        // Guardar el usuario actualizado
        System.out.println(
                "Actualizando usuario: " + updateUsuarioRequest.nombres()
                        +  " " + updateUsuarioRequest.apellidos()
        );

        usuarioRepository.save(usuarioActualizado);

        return new UsuarioResponse(" Usuario actualizado exitosamente");
    }

    @Override
    public UsuarioResponse actualizarPassword(EditarPasswordRequest editarPasswordRequest) {

        Usuario usuarioActualizado  = usuarioRepository.findByCorreo(editarPasswordRequest.correo())
                .orElseThrow(() -> new UsernameNotFoundException("El correo no existe: " + editarPasswordRequest.correo()));

        return null;
    }

    /**
     * Método para eliminar un usuario por su correo electrónico.
     * Busca al usuario en la base de datos y lo elimina si existe.
     * Devuelve un mensaje de éxito en caso de eliminación exitosa.
     */
    @Override
    public UsuarioResponse deleteUsuario(String correo) {
        if (correo == null || correo.isBlank()) {
            throw new IllegalArgumentException("El correo no puede ser nulo ni vacío");
        }
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("El correo no existe: " + correo));
        System.out.println("Eliminando usuario: " + usuario.getNombres() + " " + usuario.getApellidos());
        Rol rol = usuario.getRol();
        usuarioRepository.delete(usuario);
        rolRepository.delete(rol);
        return new UsuarioResponse(" Usuario eliminado exitosamente");
    }

}