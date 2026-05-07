package com.microcreditos.backend.service;

import com.microcreditos.backend.entity.Operador;
import com.microcreditos.backend.repository.OperadorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OperadorService {

    private final OperadorRepository operadorRepository;

    public List<Operador> listarTodos() {
        return operadorRepository.findAll();
    }

    public Operador buscarPorId(Long id) {
        return operadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Operador no encontrado con id: " + id));
    }

    public Operador crear(Operador operador) {
        if (operadorRepository.existsByEmail(operador.getEmail())) {
            throw new RuntimeException("Ya existe un operador con el email: " + operador.getEmail());
        }
        return operadorRepository.save(operador);
    }

    public Operador actualizar(Long id, Operador datos) {
        Operador existente = buscarPorId(id);
        existente.setNombre(datos.getNombre());
        existente.setEmail(datos.getEmail());
        existente.setRol(datos.getRol());
        existente.setActivo(datos.getActivo());
        return operadorRepository.save(existente);
    }

    public void eliminar(Long id) {
        buscarPorId(id);
        operadorRepository.deleteById(id);
    }
}