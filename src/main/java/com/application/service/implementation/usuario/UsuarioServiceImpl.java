package com.application.service.implementation.usuario;

import com.application.configuration.Custom.CustomUserDetails;
import com.application.persistence.entity.usuario.Usuario;
import com.application.persistence.repository.UsuarioRepository;
import com.application.service.interfaces.usuario.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService, UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

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
}
