package com.E_commerce_Beer.Enum;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter
public enum EnumRol implements GrantedAuthority {

    ADMINISTRADOR("Administrador"),
    CLIENTE("Cliente");

    private final String descripcion;

    EnumRol(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String getAuthority() {
        return this.name();
    }

    @Override
    public String toString() {
        return descripcion;
    }

}