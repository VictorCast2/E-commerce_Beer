package com.E_commerce_Beer.Enum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EmunTipoDocumento {
    CC("Cédula de Ciudadanía"),
    CE("Cédula de Extranjería"),
    DE("Documento de Extranjería"),
    NIT("Número de Identificación Tributaria"),
    PP("Pasaporte"),
    RUT("Registro Único Tributario"),
    TE("Tarjeta de Extranjería"),
    TI("Tarjeta de Identidad");

    private final String descripcion;

    public static EmunTipoDocumento obtenerPorDescripcion(String descripcion) {
        for (EmunTipoDocumento tipo : values()) {
            if (tipo.getDescripcion().equalsIgnoreCase(descripcion)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Tipo de identificacion invalida: " + descripcion);
    }

}