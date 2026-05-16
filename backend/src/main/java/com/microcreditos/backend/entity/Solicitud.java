package com.microcreditos.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.microcreditos.backend.enums.EstadoSolicitud;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "solicitud")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Solicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "solicitante_id", nullable = false)
    private Solicitante solicitante;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "producto_id", nullable = false)
    private ProductoCredito producto;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "operador_id", nullable = false)
    private Operador operador;

    @Positive
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal monto;

    @Positive
    @Column(nullable = false)
    private Integer plazoMeses;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoSolicitud estado = EstadoSolicitud.PENDIENTE;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaSolicitud = LocalDateTime.now();

    @Column
    private LocalDateTime fechaActualizacion;

    @JsonIgnore
    @OneToMany(mappedBy = "solicitud", cascade = CascadeType.ALL)
    private List<SolicitudGarante> garantes;

    @JsonIgnore
    @OneToMany(mappedBy = "solicitud", cascade = CascadeType.ALL)
    private List<SolicitudRevision> revisiones;

    @JsonIgnore
    @OneToOne(mappedBy = "solicitud", cascade = CascadeType.ALL)
    private Desembolso desembolso;
}