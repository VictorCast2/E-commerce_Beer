package com.application.presentation.dto.usuario.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"mensaje"})
public record UsuarioResponse(String mensaje) {
}