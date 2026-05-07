package com.microcreditos.backend.controller;

import com.microcreditos.backend.entity.Operador;
import com.microcreditos.backend.service.OperadorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/operadores")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class OperadorController {

    private final OperadorService operadorService;

    @GetMapping
    public ResponseEntity<List<Operador>> listar() {
        return ResponseEntity.ok(operadorService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Operador> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(operadorService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Operador> crear(@Valid @RequestBody Operador operador) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(operadorService.crear(operador));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Operador> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody Operador operador) {
        return ResponseEntity.ok(operadorService.actualizar(id, operador));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        operadorService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}