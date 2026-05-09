package com.example.Profesor.service;

import com.example.Profesor.dto.ProfesorCreateDTO;
import com.example.Profesor.dto.ProfesorPatchDTO;
import com.example.Profesor.dto.ProfesorResponseDTO;
import com.example.Profesor.dto.ProfesorUpdateDTO;
import com.example.Profesor.exception.EmailExistsException;
import com.example.Profesor.exception.ProfesorNotFoundException;
import com.example.Profesor.model.entity.Profesor;
import com.example.Profesor.repository.ProfesorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación del servicio de gestión de profesores.
 * Contiene la lógica de negocio y es transaccional.
 */
@Service
@Transactional
public class ProfesorServiceImpl implements ProfesorService {

    private final ProfesorRepository profesorRepository;

    @Autowired
    public ProfesorServiceImpl(ProfesorRepository profesorRepository) {
        this.profesorRepository = profesorRepository;
    }

    /**
     * Obtiene todos los profesores con paginación.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProfesorResponseDTO> getAllProfesores(Pageable pageable) {
        return profesorRepository.findAll(pageable)
                .map(this::convertToResponseDTO);
    }

    /**
     * Obtiene un profesor por ID.
     */
    @Override
    @Transactional(readOnly = true)
    public ProfesorResponseDTO getProfesorById(Long id) {
        Profesor profesor = profesorRepository.findById(id)
                .orElseThrow(() -> new ProfesorNotFoundException(id));
        return convertToResponseDTO(profesor);
    }

    /**
     * Busca profesores por nombre (búsqueda parcial, sin paginación).
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProfesorResponseDTO> getProfesoresByNombre(String nombre) {
        return profesorRepository.findByNombreContainingIgnoreCase(nombre)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca profesores por email (búsqueda parcial, sin paginación).
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProfesorResponseDTO> getProfesoresByEmail(String email) {
        return profesorRepository.findByEmailContainingIgnoreCase(email)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca profesores por nombre o email (búsqueda parcial, sin paginación).
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProfesorResponseDTO> getProfesoresByNombreOrEmail(String nombre, String email) {
        return profesorRepository.findByNombreContainingIgnoreCaseOrEmailContainingIgnoreCase(nombre, email)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Crea un nuevo profesor.
     * Valida que el email no esté ya registrado.
     */
    @Override
    public ProfesorResponseDTO createProfesor(ProfesorCreateDTO createDTO) {
        // Validar que el email no exista
        if (profesorRepository.existsByEmail(createDTO.getEmail())) {
            throw new EmailExistsException(createDTO.getEmail());
        }

        Profesor profesor = convertToEntity(createDTO);
        Profesor savedProfesor = profesorRepository.save(profesor);
        return convertToResponseDTO(savedProfesor);
    }

    /**
     * Actualiza completamente un profesor (PUT).
     */
    @Override
    public ProfesorResponseDTO updateProfesor(Long id, ProfesorUpdateDTO updateDTO) {
        Profesor existingProfesor = profesorRepository.findById(id)
                .orElseThrow(() -> new ProfesorNotFoundException(id));

        // Si el email cambió, validar que no exista
        if (!existingProfesor.getEmail().equals(updateDTO.getEmail()) &&
                profesorRepository.existsByEmail(updateDTO.getEmail())) {
            throw new EmailExistsException(updateDTO.getEmail());
        }

        // Actualizar todos los campos
        existingProfesor.setNombre(updateDTO.getNombre());
        existingProfesor.setApellido(updateDTO.getApellido());
        existingProfesor.setEmail(updateDTO.getEmail());
        existingProfesor.setEspecialidad(updateDTO.getEspecialidad());
        existingProfesor.setTelefono(updateDTO.getTelefono());

        Profesor updatedProfesor = profesorRepository.save(existingProfesor);
        return convertToResponseDTO(updatedProfesor);
    }

    /**
     * Actualiza parcialmente un profesor (PATCH).
     * Solo actualiza los campos que no sean nulos en el DTO.
     */
    @Override
    public ProfesorResponseDTO patchProfesor(Long id, ProfesorPatchDTO patchDTO) {
        Profesor existingProfesor = profesorRepository.findById(id)
                .orElseThrow(() -> new ProfesorNotFoundException(id));

        // Actualizar solo los campos no nulos
        if (patchDTO.getNombre() != null) {
            existingProfesor.setNombre(patchDTO.getNombre());
        }
        if (patchDTO.getApellido() != null) {
            existingProfesor.setApellido(patchDTO.getApellido());
        }
        if (patchDTO.getEmail() != null) {
            // Validar que el nuevo email no exista (si no es el mismo)
            if (!existingProfesor.getEmail().equals(patchDTO.getEmail()) &&
                    profesorRepository.existsByEmail(patchDTO.getEmail())) {
                throw new EmailExistsException(patchDTO.getEmail());
            }
            existingProfesor.setEmail(patchDTO.getEmail());
        }
        if (patchDTO.getEspecialidad() != null) {
            existingProfesor.setEspecialidad(patchDTO.getEspecialidad());
        }
        if (patchDTO.getTelefono() != null) {
            existingProfesor.setTelefono(patchDTO.getTelefono());
        }

        Profesor updatedProfesor = profesorRepository.save(existingProfesor);
        return convertToResponseDTO(updatedProfesor);
    }

    /**
     * Elimina un profesor por ID.
     */
    @Override
    public void deleteProfesor(Long id) {
        if (!profesorRepository.existsById(id)) {
            throw new ProfesorNotFoundException(id);
        }
        profesorRepository.deleteById(id);
    }

    // ==================== MÉTODOS DE CONVERSIÓN ====================

    /**
     * Convierte una entidad Profesor a ProfesorResponseDTO.
     */
    private ProfesorResponseDTO convertToResponseDTO(Profesor profesor) {
        return new ProfesorResponseDTO(
                profesor.getId(),
                profesor.getNombre(),
                profesor.getApellido(),
                profesor.getEmail(),
                profesor.getEspecialidad(),
                profesor.getTelefono()
        );
    }

    /**
     * Convierte un ProfesorCreateDTO a entidad Profesor.
     */
    private Profesor convertToEntity(ProfesorCreateDTO createDTO) {
        return new Profesor(
                createDTO.getNombre(),
                createDTO.getApellido(),
                createDTO.getEmail(),
                createDTO.getEspecialidad(),
                createDTO.getTelefono()
        );
    }
}
