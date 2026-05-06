package com.microcreditos.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "solicitud_garante")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SolicitudGarante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solicitud_id", nullable = false)
    private Solicitud solicitud;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "garante_id", nullable = false)
    private Garante garante;

    @Column(nullable = false)
    private LocalDateTime fechaVinculacion = LocalDateTime.now();
}