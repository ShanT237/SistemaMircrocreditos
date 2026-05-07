package com.microcreditos.backend.controller;

import com.microcreditos.backend.entity.Desembolso;
import com.microcreditos.backend.service.DesembolsoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/desembolsos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DesembolsoController {

    private final DesembolsoService desembolsoService;

    @GetMapping
    public ResponseEntity<List<Desembolso>> listar() {
        return ResponseEntity.ok(desembolsoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Desembolso> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(desembolsoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Desembolso> registrar(
            @RequestParam Long solicitudId,
            @RequestParam Long operadorId,
            @Valid @RequestBody Desembolso desembolso) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(desembolsoService.registrar(solicitudId, operadorId, desembolso));
    }
}