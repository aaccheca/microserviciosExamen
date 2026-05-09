package com.example.Profesor.dto;

/**
 * DTO para las respuestas de profesor.
 * Incluye el ID generado.
 */
public class ProfesorResponseDTO {

    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String especialidad;
    private String telefono;

    // Constructores
    public ProfesorResponseDTO() {
    }

    public ProfesorResponseDTO(Long id, String nombre, String apellido, String email, String especialidad, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.especialidad = especialidad;
        this.telefono = telefono;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
