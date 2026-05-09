package com.example.Profesor.service;

import com.example.Profesor.dto.ProfesorCreateDTO;
import com.example.Profesor.dto.ProfesorPatchDTO;
import com.example.Profesor.dto.ProfesorResponseDTO;
import com.example.Profesor.dto.ProfesorUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Servicio que define las operaciones de negocio para la entidad Profesor.
 */
public interface ProfesorService {

    /**
     * Obtiene todos los profesores con paginación.
     * @param pageable Configuración de paginación (página, tamaño, ordenamiento)
     * @return Página de profesores
     */
    Page<ProfesorResponseDTO> getAllProfesores(Pageable pageable);

    /**
     * Obtiene un profesor por su ID.
     * @param id ID del profesor
     * @return DTO del profesor encontrado
     * @throws ProfesorNotFoundException si el profesor no existe
     */
    ProfesorResponseDTO getProfesorById(Long id);

    /**
     * Busca profesores por nombre (búsqueda parcial, sin paginación).
     * @param nombre Nombre o parte del nombre a buscar
     * @return Lista de profesores que coinciden
     */
    List<ProfesorResponseDTO> getProfesoresByNombre(String nombre);

    /**
     * Busca profesores por email (búsqueda parcial, sin paginación).
     * @param email Email o parte del email a buscar
     * @return Lista de profesores que coinciden
     */
    List<ProfesorResponseDTO> getProfesoresByEmail(String email);

    /**
     * Busca profesores por nombre o email (búsqueda parcial, sin paginación).
     * @param nombre Nombre o parte del nombre
     * @param email Email o parte del email
     * @return Lista de profesores que coinciden con cualquiera de los criterios
     */
    List<ProfesorResponseDTO> getProfesoresByNombreOrEmail(String nombre, String email);

    /**
     * Crea un nuevo profesor.
     * @param createDTO Datos del profesor a crear
     * @return DTO del profesor creado (con ID generado)
     * @throws EmailExistsException si el email ya está registrado
     */
    ProfesorResponseDTO createProfesor(ProfesorCreateDTO createDTO);

    /**
     * Actualiza completamente un profesor (PUT).
     * @param id ID del profesor a actualizar
     * @param updateDTO Datos actualizados del profesor
     * @return DTO del profesor actualizado
     * @throws ProfesorNotFoundException si el profesor no existe
     * @throws EmailExistsException si el nuevo email ya está registrado por otro profesor
     */
    ProfesorResponseDTO updateProfesor(Long id, ProfesorUpdateDTO updateDTO);

    /**
     * Actualiza parcialmente un profesor (PATCH).
     * @param id ID del profesor a actualizar
     * @param patchDTO Campos a actualizar (solo los proporcionados)
     * @return DTO del profesor actualizado
     * @throws ProfesorNotFoundException si el profesor no existe
     * @throws EmailExistsException si el email actualizado ya está registrado por otro profesor
     */
    ProfesorResponseDTO patchProfesor(Long id, ProfesorPatchDTO patchDTO);

    /**
     * Elimina un profesor por su ID.
     * @param id ID del profesor a eliminar
     * @throws ProfesorNotFoundException si el profesor no existe
     */
    void deleteProfesor(Long id);
}
