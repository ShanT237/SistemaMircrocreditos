package com.microcreditos.backend.repository;

import com.microcreditos.backend.entity.SolicitudGarante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SolicitudGaranteRepository extends JpaRepository<SolicitudGarante, Long> {
    List<SolicitudGarante> findBySolicitudId(Long solicitudId);
}