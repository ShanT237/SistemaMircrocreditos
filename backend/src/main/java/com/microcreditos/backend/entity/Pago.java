package com.microcreditos.backend.entity;

import com.microcreditos.backend.enums.MetodoPago;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pago")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cronograma_pago_id", nullable = false, unique = true)
    private CronogramaPago cronogramaPago;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "operador_id", nullable = false)
    private Operador operador;

    @Column(nullable = false)
    private LocalDateTime fechaPago = LocalDateTime.now();

    @Positive
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal valorPagado;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private MetodoPago metodoPago;

    @Column(length = 100)
    private String comprobante;
}