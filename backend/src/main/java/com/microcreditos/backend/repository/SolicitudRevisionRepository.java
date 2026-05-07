package com.microcreditos.backend.repository;

import com.microcreditos.backend.entity.SolicitudRevision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SolicitudRevisionRepository extends JpaRepository<SolicitudRevision, Long> {
    List<SolicitudRevision> findBySolicitudId(Long solicitudId);
}