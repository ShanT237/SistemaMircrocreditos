package com.microcreditos.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "garante")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Garante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true, length = 20)
    private String cedula;

    @NotBlank
    @Column(nullable = false, length = 120)
    private String nombre;

    @NotBlank
    @Column(nullable = false, length = 20)
    private String telefono;

    @Email
    @NotBlank
    @Column(nullable = false, length = 100)
    private String email;

    @Positive
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal ingresos;

    @Column(nullable = false, length = 60)
    private String relacion;
}