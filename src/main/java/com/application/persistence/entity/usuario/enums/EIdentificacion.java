package com.application.persistence.entity.usuario.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EIdentificacion {
    CC("Cédula de Ciudadanía"),
    CE("Cédula de Extranjería"),
    DE("Documento de Extranjería"),
    NIT("Número de Identificación Tributaria"),
    PP("Pasaporte"),
    TE("Tarjeta de Extranjería"),
    TI("Tarjeta de Identidad");

    private final String descripcion;
}