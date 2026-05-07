package com.microcreditos.backend.controller;

import com.microcreditos.backend.entity.Solicitud;
import com.microcreditos.backend.service.SolicitudService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/solicitudes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SolicitudController {

    private final SolicitudService solicitudService;

    @GetMapping
    public ResponseEntity<List<Solicitud>> listar() {
        return ResponseEntity.ok(solicitudService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Solicitud> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(solicitudService.buscarPorId(id));
    }

    @GetMapping("/solicitante/{solicitanteId}")
    public ResponseEntity<List<Solicitud>> listarPorSolicitante(
            @PathVariable Long solicitanteId) {
        return ResponseEntity.ok(solicitudService.listarPorSolicitante(solicitanteId));
    }

    @PostMapping
    public ResponseEntity<Solicitud> crear(
            @RequestParam Long solicitanteId,
            @RequestParam Long productoId,
            @RequestParam Long operadorId,
            @Valid @RequestBody Solicitud solicitud) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(solicitudService.crear(solicitanteId, productoId, operadorId, solicitud));
    }

    @PutMapping("/{id}/aprobar")
    public ResponseEntity<Solicitud> aprobar(@PathVariable Long id) {
        return ResponseEntity.ok(solicitudService.aprobar(id));
    }

    @PutMapping("/{id}/rechazar")
    public ResponseEntity<Solicitud> rechazar(@PathVariable Long id) {
        return ResponseEntity.ok(solicitudService.rechazar(id));
    }
}