package com.application.configuration.Custom;

import com.application.persistence.entity.empresa.Empresa;
import com.application.persistence.entity.usuario.Usuario;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;

@Getter
public class CustomUserPrincipal implements UserDetails, OAuth2User {

    // Atributos para la autenticación y autorización del usuario mediante UserDetails
    private final String correo;
    private final String password;
    private final List<GrantedAuthority> authorities = new ArrayList<>();
    private final boolean isEnabled;
    private final boolean accountNonExpired;
    private final boolean accountNonLocked;
    private final boolean credentialNonExpired;

    // Atributo para Oauth2User
    private final Map<String, Object> attributes;

    // Atributos adicionales de la clase Usuario
    private final String nombres;
    private final String apellidos;
    private final Empresa empresa;

    // Constructor usado cuando el usuario entre por .fromLogin()
    public CustomUserPrincipal(Usuario usuario) {
        this(usuario, Collections.emptyMap());
    }

    // Constructor usado cuando el usuario entra por .oauth2Login()
    public CustomUserPrincipal(Usuario usuario, Map<String, Object> attributes) {
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
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
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

    @Override
    public String getName() {
        return correo;
    }

}