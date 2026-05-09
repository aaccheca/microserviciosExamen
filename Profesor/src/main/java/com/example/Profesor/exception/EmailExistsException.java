package com.example.Profesor.exception;

/**
 * Excepción lanzada cuando ya existe un profesor con el email proporcionado.
 */
public class EmailExistsException extends RuntimeException {

    private static final Long serialVersionUID = 1L;

    public EmailExistsException(String email) {
        super("Ya existe un profesor con el email: " + email);
    }
}
