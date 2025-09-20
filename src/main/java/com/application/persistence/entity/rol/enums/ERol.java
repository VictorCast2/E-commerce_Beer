package com.application.persistence.entity.rol.enums;

public enum ERol {
    ADMIN, // Administrador de la Aplicacion
    INVITADO, // Usuario no logueado
    PERSONA_CONTACTO, // Usuario afiliado a una empresa
    PERSONA_JURIDICA, // Usuario que cuanta con un NIT
    PERSONA_NATURAL // Usuario registrado con un tipo de identificación diferente a NIT
}