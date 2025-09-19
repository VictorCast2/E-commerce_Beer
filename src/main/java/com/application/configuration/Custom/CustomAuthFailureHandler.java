package com.application.configuration.Custom;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class CustomAuthFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {

        String mensajeError = "Credenciales Inválidas";

        if (exception instanceof LockedException) {
            mensajeError = "Tu cuenta está bloqueada.";
        } else if (exception instanceof DisabledException) {
            mensajeError = "Tu cuenta está deshabilitada.";
        } else if (exception instanceof AccountExpiredException) {
            mensajeError = "Tu sesión ha expirado";
        } else if (exception instanceof CredentialsExpiredException) {
            mensajeError = "Tu contraseña ha expirado";
        } else if (exception instanceof BadCredentialsException) {
            mensajeError = "Usuario o contraseña incorrectos";
        }

        response.sendRedirect("/auth/login?error=" + UriUtils.encode(mensajeError, StandardCharsets.UTF_8));

    }
}
