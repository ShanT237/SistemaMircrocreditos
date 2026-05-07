package com.microcreditos.backend.entity;

import com.microcreditos.backend.enums.EstadoDesembolso;
import com.microcreditos.backend.enums.MetodoPago;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "desembolso")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Desembolso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solicitud_id", nullable = false, unique = true)
    private Solicitud solicitud;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "operador_id", nullable = false)
    private Operador operador;

    @Positive
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal monto;

    @Column(nullable = false)
    private LocalDateTime fechaDesembolso = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private MetodoPago metodoPago;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoDesembolso estado = EstadoDesembolso.PROGRAMADO;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal cuotaMensual;

    @OneToMany(mappedBy = "desembolso", cascade = CascadeType.ALL)
    private List<CronogramaPago> cronograma;
}