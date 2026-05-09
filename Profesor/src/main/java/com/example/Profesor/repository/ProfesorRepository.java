package com.example.Profesor.repository;

import com.example.Profesor.model.entity.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfesorRepository extends JpaRepository<Profesor, Long> {

    /**
     * Verifica si existe un profesor con el email especificado.
     * @param email Email a buscar
     * @return true si existe, false en caso contrario
     */
    boolean existsByEmail(String email);

    /**
     * Busca un profesor por email.
     * @param email Email del profesor
     * @return Optional con el profesor encontrado
     */
    Optional<Profesor> findByEmail(String email);

    /**
     * Busca profesores por nombre (búsqueda parcial, ignorando mayúsculas/minúsculas).
     * @param nombre Nombre o parte del nombre a buscar
     * @return Lista de profesores que coinciden
     */
    java.util.List<Profesor> findByNombreContainingIgnoreCase(String nombre);

    /**
     * Busca profesores por email (búsqueda parcial, ignorando mayúsculas/minúsculas).
     * @param email Email o parte del email a buscar
     * @return Lista de profesores que coinciden
     */
    java.util.List<Profesor> findByEmailContainingIgnoreCase(String email);

    /**
     * Busca profesores por nombre O email (búsqueda parcial, ignorando mayúsculas/minúsculas).
     * @param nombre Nombre o parte del nombre
     * @param email Email o parte del email
     * @return Lista de profesores que coinciden con cualquiera de los criterios
     */
    java.util.List<Profesor> findByNombreContainingIgnoreCaseOrEmailContainingIgnoreCase(String nombre, String email);
}
