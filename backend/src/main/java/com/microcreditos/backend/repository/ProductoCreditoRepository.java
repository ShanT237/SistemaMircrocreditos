package com.microcreditos.backend.repository;

import com.microcreditos.backend.entity.ProductoCredito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductoCreditoRepository extends JpaRepository<ProductoCredito, Long> {
    List<ProductoCredito> findByActivoTrue();
}