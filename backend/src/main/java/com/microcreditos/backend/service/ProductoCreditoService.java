package com.microcreditos.backend.service;

import com.microcreditos.backend.entity.ProductoCredito;
import com.microcreditos.backend.repository.ProductoCreditoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoCreditoService {

    private final ProductoCreditoRepository productoCreditoRepository;

    public List<ProductoCredito> listarActivos() {
        return productoCreditoRepository.findByActivoTrue();
    }

    public List<ProductoCredito> listarTodos() {
        return productoCreditoRepository.findAll();
    }

    public ProductoCredito buscarPorId(Long id) {
        return productoCreditoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));
    }

    public ProductoCredito crear(ProductoCredito producto) {
        if (producto.getMontoMin().compareTo(producto.getMontoMax()) >= 0) {
            throw new RuntimeException("El monto mínimo debe ser menor al monto máximo");
        }
        return productoCreditoRepository.save(producto);
    }

    public ProductoCredito actualizar(Long id, ProductoCredito datos) {
        ProductoCredito existente = buscarPorId(id);
        existente.setNombre(datos.getNombre());
        existente.setMontoMin(datos.getMontoMin());
        existente.setMontoMax(datos.getMontoMax());
        existente.setTasaInteres(datos.getTasaInteres());
        existente.setPlazoMaxMeses(datos.getPlazoMaxMeses());
        existente.setActivo(datos.getActivo());
        return productoCreditoRepository.save(existente);
    }
}