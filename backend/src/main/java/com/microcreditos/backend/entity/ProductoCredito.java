package com.microcreditos.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "producto_credito")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoCredito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String nombre;

    @Positive
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal montoMin;

    @Positive
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal montoMax;

    @Positive
    @Column(nullable = false, precision = 6, scale = 4)
    private BigDecimal tasaInteres;

    @Positive
    @Column(nullable = false)
    private Integer plazoMaxMeses;

    @Column(nullable = false)
    private Boolean activo = true;
}