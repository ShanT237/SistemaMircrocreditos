package com.microcreditos.backend.repository;

import com.microcreditos.backend.entity.Solicitud;
import com.microcreditos.backend.enums.EstadoSolicitud;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {
    @Override
    @EntityGraph(attributePaths = {"solicitante", "producto", "operador"})
    List<Solicitud> findAll();

    @EntityGraph(attributePaths = {"solicitante", "producto", "operador"})
    List<Solicitud> findBySolicitanteId(Long solicitanteId);

    long countBySolicitanteIdAndEstadoIn(Long solicitanteId, List<EstadoSolicitud> estados);
}