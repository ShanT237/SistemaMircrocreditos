package com.microcreditos.backend.entity;

import com.microcreditos.backend.enums.RolOperador;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "operador")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Operador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 120)
    private String nombre;

    @Email
    @NotBlank
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RolOperador rol;

    @Column(nullable = false)
    private Boolean activo = true;
}