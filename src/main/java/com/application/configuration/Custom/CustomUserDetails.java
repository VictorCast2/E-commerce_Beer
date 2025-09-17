package com.application.configuration.Custom;

import com.application.persistence.entity.empresa.Empresa;
import com.application.persistence.entity.usuario.Usuario;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
public class CustomUserDetails implements UserDetails {

    // Atributos para la autenticación y autorización del usuario
    private final String correo;
    private final String password;
    private final List<GrantedAuthority> authorities = new ArrayList<>();
    private final boolean isEnabled;
    private final boolean accountNonExpired;
    private final boolean accountNonLocked;
    private final boolean credentialNonExpired;

    // Atributos adicionales de la clase Usuario
    private final String nombres;
    private final String apellidos;
    private final Empresa empresa;

    public CustomUserDetails(Usuario usuario) {
        this.correo = usuario.getCorreo();
        this.password = usuario.getPassword();
        this.authorities.add(new SimpleGrantedAuthority("ROLE_".concat(usuario.getRol().getName().name())));
        this.isEnabled = usuario.isEnabled();
        this.accountNonExpired = usuario.isAccountNonExpired();
        this.accountNonLocked = usuario.isAccountNonLocked();
        this.credentialNonExpired = usuario.isCredentialsNonExpired();
        this.nombres = usuario.getNombres();
        this.apellidos = usuario.getApellidos();
        this.empresa = usuario.getEmpresa();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return correo;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
