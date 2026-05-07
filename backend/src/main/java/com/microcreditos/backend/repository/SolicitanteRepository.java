package com.microcreditos.backend.repository;

import com.microcreditos.backend.entity.Solicitante;
import com.microcreditos.backend.enums.EstadoSolicitante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface SolicitanteRepository extends JpaRepository<Solicitante, Long> {
    Optional<Solicitante> findByCedula(String cedula);
    boolean existsByCedula(String cedula);
    List<Solicitante> findByEstado(EstadoSolicitante estado);
    List<Solicitante> findByNombreContainingIgnoreCase(String nombre);
}