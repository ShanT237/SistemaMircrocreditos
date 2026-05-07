package com.microcreditos.backend.entity;

import com.microcreditos.backend.enums.EstadoCuota;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "cronograma_pago")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CronogramaPago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "desembolso_id", nullable = false)
    private Desembolso desembolso;

    @Column(nullable = false)
    private Integer numeroCuota;

    @Column(nullable = false)
    private LocalDate fechaVencimiento;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal valorCuota;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal capital;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal interes;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal saldoRestante;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoCuota estado = EstadoCuota.PENDIENTE;

    @OneToOne(mappedBy = "cronogramaPago", cascade = CascadeType.ALL)
    private Pago pago;
}