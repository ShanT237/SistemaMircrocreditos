package com.microcreditos.backend.controller;

import com.microcreditos.backend.entity.Solicitante;
import com.microcreditos.backend.service.SolicitanteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/solicitantes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SolicitanteController {

    private final SolicitanteService solicitanteService;

    @GetMapping
    public ResponseEntity<List<Solicitante>> listar(
            @RequestParam(required = false) String nombre) {
        if (nombre != null && !nombre.isEmpty()) {
            return ResponseEntity.ok(solicitanteService.buscarPorNombre(nombre));
        }
        return ResponseEntity.ok(solicitanteService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Solicitante> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(solicitanteService.buscarPorId(id));
    }

    @GetMapping("/cedula/{cedula}")
    public ResponseEntity<Solicitante> buscarPorCedula(@PathVariable String cedula) {
        return ResponseEntity.ok(solicitanteService.buscarPorCedula(cedula));
    }

    @PostMapping
    public ResponseEntity<Solicitante> crear(@Valid @RequestBody Solicitante solicitante) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(solicitanteService.crear(solicitante));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Solicitante> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody Solicitante solicitante) {
        return ResponseEntity.ok(solicitanteService.actualizar(id, solicitante));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        solicitanteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}