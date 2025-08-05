package com.application.service.implementation.usuario;

import com.application.persistence.entity.rol.Rol;
import com.application.persistence.entity.rol.enums.ERol;
import com.application.persistence.repository.RolRepository;
import com.application.presentation.dto.usuario.request.ActualizaccionUsuarioRequest;
import com.application.presentation.dto.usuario.request.CreacionUsuarioRequest;
import com.application.presentation.dto.usuario.response.UsuarioResponse;
import com.application.persistence.entity.usuario.Usuario;
import com.application.persistence.repository.UsuarioRepository;
import com.application.service.interfaces.usuario.UsuarioInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServicesImpl implements UsuarioInterface, UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;

    @Lazy
    @Autowired
    private PasswordEncoder encoder;

    public UsuarioServicesImpl(UsuarioRepository usuarioRepository, RolRepository rolRepository, PasswordEncoder encoder) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.encoder = encoder;
    }

    /**
     * Método para crear un nuevo usuario.
     * Utiliza el RolRepository para encontrar el rol correspondiente al usuario.
     * La contraseña se cifra antes de guardarla en la base de datos.
     */
    @Override
    public UsuarioResponse crearUsuario(CreacionUsuarioRequest request) {

        if (request.getRol() == null || request.getRol().isBlank()) {
            throw new IllegalArgumentException("El rol no puede ser nulo ni vacío");
        }

        ERol eRol = ERol.valueOf(request.getRol().toUpperCase());


        // Buscar o crear el rol
        Rol rol = rolRepository.findByName(eRol)
                .orElseGet(() -> rolRepository.save(Rol.builder().name(eRol).build()));

        // Crear el usuario
        Usuario usuario = Usuario.builder()
                .nombres(request.getNombres())
                .apellidos(request.getApellidos())
                .cedula(request.getCedula())
                .telefono(request.getTelefono())
                .rol(rol)
                .correo(request.getCorreo())
                .password(encoder.encode(request.getPassword()))
                .imagen(request.getImagen())
                .empresa(null)
                .isEnabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .build();
        usuarioRepository.save(usuario);
        return new UsuarioResponse("✅ Usuario creado exitosamente");
    }

    @Override
    public UsuarioResponse actualizarUsuario(String correo, ActualizaccionUsuarioRequest request) {
        return null;
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

    /**
     * @param correo
     * @return
     * @throws UsernameNotFoundException
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

}