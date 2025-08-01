package com.application.usuario.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"mensaje"})
public record UsuarioResponse(String mensaje) {
}