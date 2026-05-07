package com.microcreditos.backend.repository;

import com.microcreditos.backend.entity.Desembolso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface DesembolsoRepository extends JpaRepository<Desembolso, Long> {
    Optional<Desembolso> findBySolicitudId(Long solicitudId);
    boolean existsBySolicitudId(Long solicitudId);
}