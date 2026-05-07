package com.microcreditos.backend.repository;

import com.microcreditos.backend.entity.Garante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface GaranteRepository extends JpaRepository<Garante, Long> {
    Optional<Garante> findByCedula(String cedula);
    boolean existsByCedula(String cedula);
}