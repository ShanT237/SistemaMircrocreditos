package com.microcreditos.backend.entity;

import com.microcreditos.backend.enums.EstadoSolicitante;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "solicitante")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Solicitante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true, length = 20)
    private String cedula;

    @NotBlank
    @Column(nullable = false, length = 120)
    private String nombre;

    @Email
    @NotBlank
    @Column(nullable = false, length = 100)
    private String email;

    @NotBlank
    @Column(nullable = false, length = 20)
    private String telefono;

    @Positive
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal ingresos;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoSolicitante estado = EstadoSolicitante.ACTIVO;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();
}