package com.example.Profesor.exception;

/**
 * Excepción lanzada cuando no se encuentra un profesor.
 */
public class ProfesorNotFoundException extends RuntimeException {

    private static final Long serialVersionUID = 1L;

    public ProfesorNotFoundException(Long id) {
        super("Profesor no encontrado con ID: " + id);
    }

    public ProfesorNotFoundException(String message) {
        super(message);
    }
}
