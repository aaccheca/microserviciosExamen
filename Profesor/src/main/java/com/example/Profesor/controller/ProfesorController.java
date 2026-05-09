package com.example.Profesor.controller;

import com.example.Profesor.dto.ProfesorCreateDTO;
import com.example.Profesor.dto.ProfesorPatchDTO;
import com.example.Profesor.dto.ProfesorResponseDTO;
import com.example.Profesor.dto.ProfesorUpdateDTO;
import com.example.Profesor.service.ProfesorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de profesores.
 * Expone endpoints CRUD con paginación y búsqueda.
 */
@RestController
@RequestMapping("/api/profesores")
@Validated
public class ProfesorController {

    private final ProfesorService profesorService;

    @Autowired
    public ProfesorController(ProfesorService profesorService) {
        this.profesorService = profesorService;
    }

    /**
     * GET /api/profesores
     * Obtiene todos los profesores con paginación.
     * Parámetros opcionales: page (0 por defecto), size (10 por defecto), sort (ordenamiento)
     */
    @GetMapping
    public ResponseEntity<Page<ProfesorResponseDTO>> getAllProfesores(
            @PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<ProfesorResponseDTO> profesores = profesorService.getAllProfesores(pageable);
        return ResponseEntity.ok(profesores);
    }

    /**
     * GET /api/profesores/{id}
     * Obtiene un profesor por su ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProfesorResponseDTO> getProfesorById(@PathVariable Long id) {
        ProfesorResponseDTO profesor = profesorService.getProfesorById(id);
        return ResponseEntity.ok(profesor);
    }

    /**
     * GET /api/profesores/search?nombre=...
     * Busca profesores por nombre (búsqueda parcial, sin paginación).
     */
    @GetMapping("/search")
    public ResponseEntity<List<ProfesorResponseDTO>> searchProfesores(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String email) {

        List<ProfesorResponseDTO> results;

        // Si se proporcionan ambos parámetros, busca por nombre O email
        if (nombre != null && !nombre.trim().isEmpty() && email != null && !email.trim().isEmpty()) {
            results = profesorService.getProfesoresByNombreOrEmail(nombre, email);
        }
        // Si solo se proporciona nombre
        else if (nombre != null && !nombre.trim().isEmpty()) {
            results = profesorService.getProfesoresByNombre(nombre);
        }
        // Si solo se proporciona email
        else if (email != null && !email.trim().isEmpty()) {
            results = profesorService.getProfesoresByEmail(email);
        }
        // Si no se proporciona ningún parámetro, devolver lista vacía o todos?
        // Por conveniencia, devolvemos todos si no hay criterios
        else {
            results = profesorService.getAllProfesores(Pageable.unpaged()).getContent();
        }

        return ResponseEntity.ok(results);
    }

    /**
     * POST /api/profesores
     * Crea un nuevo profesor.
     * Valida que el email no esté ya registrado.
     */
    @PostMapping
    public ResponseEntity<ProfesorResponseDTO> createProfesor(
            @Valid @RequestBody ProfesorCreateDTO createDTO) {
        ProfesorResponseDTO created = profesorService.createProfesor(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * PUT /api/profesores/{id}
     * Actualiza completamente un profesor.
     * Requiere todos los campos.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProfesorResponseDTO> updateProfesor(
            @PathVariable Long id,
            @Valid @RequestBody ProfesorUpdateDTO updateDTO) {
        ProfesorResponseDTO updated = profesorService.updateProfesor(id, updateDTO);
        return ResponseEntity.ok(updated);
    }

    /**
     * PATCH /api/profesores/{id}
     * Actualiza parcialmente un profesor.
     * Solo actualiza los campos proporcionados.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<ProfesorResponseDTO> patchProfesor(
            @PathVariable Long id,
            @Valid @RequestBody ProfesorPatchDTO patchDTO) {
        ProfesorResponseDTO updated = profesorService.patchProfesor(id, patchDTO);
        return ResponseEntity.ok(updated);
    }

    /**
     * DELETE /api/profesores/{id}
     * Elimina un profesor.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfesor(@PathVariable Long id) {
        profesorService.deleteProfesor(id);
        return ResponseEntity.noContent().build();
    }
}
