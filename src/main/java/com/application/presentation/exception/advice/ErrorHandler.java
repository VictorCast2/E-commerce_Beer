package com.application.presentation.exception.advice;

import com.application.presentation.exception.UsuarioNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {
    private final Map<String, Object> errorMap = new HashMap<>();

    // Maneja cuando un usuario no es encontrado
    @ExceptionHandler(UsuarioNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUsuarioNotFound(UsuarioNotFoundException ex) {
        log.error("Usuario no encontrado: {}", ex.getMessage());
        return buildErrorResponse(
                HttpStatus.NOT_FOUND,
                "El usuario solicitado no existe en el sistema.",
                ex.getMessage()
        );
    }

    // Maneja credenciales incorrectas
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, Object>> handleBadCredentials(BadCredentialsException ex) {
        log.warn("Intento de acceso con credenciales inválidas: {}", ex.getMessage());
        return buildErrorResponse(
                HttpStatus.UNAUTHORIZED,
                "Credenciales inválidas. Verifica tu usuario y contraseña.",
                ex.getMessage()
        );
    }

    // Maneja cualquier otra excepción no controlada
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericError(Exception ex) {
        log.error("Error inesperado: ", ex);
        return buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Ha ocurrido un error inesperado. Por favor intenta más tarde.",
                ex.getMessage()
        );
    }

    // Método privado reutilizable para construir respuestas de error
    private ResponseEntity<Map<String, Object>> buildErrorResponse(HttpStatus status, String message, String details) {
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("status", status.value());
        errorBody.put("error", status.getReasonPhrase());
        errorBody.put("message", message);
        errorBody.put("details", details);
        errorBody.put("timestamp", LocalDateTime.now());
        return new ResponseEntity<>(errorBody, status);
    }

}