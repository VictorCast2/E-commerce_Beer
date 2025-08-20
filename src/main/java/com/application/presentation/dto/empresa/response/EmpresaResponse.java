package com.application.presentation.dto.empresa.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"mensaje"})
public record EmpresaResponse(String mensaje) {
}