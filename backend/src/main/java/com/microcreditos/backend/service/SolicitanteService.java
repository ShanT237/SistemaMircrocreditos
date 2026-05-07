package com.microcreditos.backend.service;

import com.microcreditos.backend.entity.Solicitante;
import com.microcreditos.backend.enums.EstadoSolicitante;
import com.microcreditos.backend.repository.SolicitanteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SolicitanteService {

    private final SolicitanteRepository solicitanteRepository;

    // ── CRUD ─────────────────────────────────────────────────
    public List<Solicitante> listarTodos() {
        return solicitanteRepository.findAll();
    }

    public List<Solicitante> buscarPorNombre(String nombre) {
        return solicitanteRepository.findByNombreContainingIgnoreCase(nombre);
    }

    public Solicitante buscarPorId(Long id) {
        return solicitanteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitante no encontrado con id: " + id));
    }

    public Solicitante buscarPorCedula(String cedula) {
        return solicitanteRepository.findByCedula(cedula)
                .orElseThrow(() -> new RuntimeException("Solicitante no encontrado con cédula: " + cedula));
    }

    public Solicitante crear(Solicitante solicitante) {
        // RN: cédula única
        if (solicitanteRepository.existsByCedula(solicitante.getCedula())) {
            throw new RuntimeException("Ya existe un solicitante con la cédula: " + solicitante.getCedula());
        }
        return solicitanteRepository.save(solicitante);
    }

    public Solicitante actualizar(Long id, Solicitante datos) {
        Solicitante existente = buscarPorId(id);
        existente.setNombre(datos.getNombre());
        existente.setEmail(datos.getEmail());
        existente.setTelefono(datos.getTelefono());
        existente.setIngresos(datos.getIngresos());
        existente.setEstado(datos.getEstado());
        return solicitanteRepository.save(existente);
    }

    public void eliminar(Long id) {
        buscarPorId(id);
        solicitanteRepository.deleteById(id);
    }
}