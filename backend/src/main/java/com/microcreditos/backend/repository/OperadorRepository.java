package com.microcreditos.backend.repository;

import com.microcreditos.backend.entity.Operador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface OperadorRepository extends JpaRepository<Operador, Long> {
    Optional<Operador> findByEmail(String email);
    boolean existsByEmail(String email);
}