package com.microcreditos.backend.controller;

import com.microcreditos.backend.entity.ProductoCredito;
import com.microcreditos.backend.service.ProductoCreditoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProductoCreditoController {

    private final ProductoCreditoService productoCreditoService;

    @GetMapping
    public ResponseEntity<List<ProductoCredito>> listar(
            @RequestParam(required = false, defaultValue = "true") boolean soloActivos) {
        if (soloActivos) {
            return ResponseEntity.ok(productoCreditoService.listarActivos());
        }
        return ResponseEntity.ok(productoCreditoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoCredito> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(productoCreditoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<ProductoCredito> crear(
            @Valid @RequestBody ProductoCredito producto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productoCreditoService.crear(producto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoCredito> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ProductoCredito producto) {
        return ResponseEntity.ok(productoCreditoService.actualizar(id, producto));
    }
}