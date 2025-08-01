package com.application.utils;

import com.application.usuario.entity.Usuario;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomUserDetails implements UserDetails {

    private String correo;
    private String password;
    private List<GrantedAuthority> authorities = new ArrayList<>();
    private boolean isEnabled;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;

    public CustomUserDetails(Usuario usuario) {
        this.correo = usuario.getCorreo();
        this.password = usuario.getContrasenna();

        // Agregar el rol como autoridad
        this.authorities.add(
                new SimpleGrantedAuthority("ROLE_" + usuario.getRol().getERol().name())
        );

        this.isEnabled = usuario.isEnabled();
        this.accountNonExpired = usuario.isAccountNoExpired();
        this.accountNonLocked = usuario.isAccountNoLocked();
        this.credentialsNonExpired = usuario.isCredentialNoExpired();
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
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

}