package com.microcreditos.backend.repository;

import com.microcreditos.backend.entity.CronogramaPago;
import com.microcreditos.backend.enums.EstadoCuota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CronogramaPagoRepository extends JpaRepository<CronogramaPago, Long> {
    List<CronogramaPago> findByDesembolsoId(Long desembolsoId);
    List<CronogramaPago> findByDesembolsoIdAndEstado(Long desembolsoId, EstadoCuota estado);
}