package com.application.presentation.exception;

/**
 * Excepción personalizada que indica que un usuario no fue encontrado en el sistema.
 *
 * Se lanza cuando una operación intenta acceder a un usuario que no existe
 * en la base de datos o no cumple con los criterios de búsqueda.
 */
public class UsuarioNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L; // Buena práctica para excepciones serializables

    private final String userId; // Campo opcional para identificar el usuario problemático

    /**
     * Crea una excepción sin mensaje ni ID de usuario.
     */
    public UsuarioNotFoundException() {
        super("Usuario no encontrado.");
        this.userId = null;
    }

    /**
     * Crea una excepción con un mensaje personalizado.
     *
     * @param message Descripción del error.
     */
    public UsuarioNotFoundException(String message) {
        super(message);
        this.userId = null;
    }

    /**
     * Crea una excepción con un mensaje y el ID del usuario afectado.
     *
     * @param message Descripción del error.
     * @param userId ID del usuario que no fue encontrado.
     */
    public UsuarioNotFoundException(String message, String userId) {
        super(message);
        this.userId = userId;
    }

    /**
     * Crea una excepción con mensaje, causa y el ID del usuario afectado.
     */
    public UsuarioNotFoundException(String message, Throwable cause, String userId) {
        super(message, cause);
        this.userId = userId;
    }

    /**
     * Obtiene el ID del usuario no encontrado (si aplica).
     *
     * @return El ID del usuario o null si no se proporcionó.
     */
    public String getUserId() {
        return userId;
    }

}