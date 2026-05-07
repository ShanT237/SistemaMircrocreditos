package com.microcreditos.backend.service;

import com.microcreditos.backend.entity.*;
import com.microcreditos.backend.enums.EstadoSolicitud;
import com.microcreditos.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SolicitudService {

    private final SolicitudRepository solicitudRepository;
    private final SolicitanteRepository solicitanteRepository;
    private final ProductoCreditoRepository productoCreditoRepository;
    private final OperadorRepository operadorRepository;

    public List<Solicitud> listarTodas() {
        return solicitudRepository.findAll();
    }

    public List<Solicitud> listarPorSolicitante(Long solicitanteId) {
        return solicitudRepository.findBySolicitanteId(solicitanteId);
    }

    public Solicitud buscarPorId(Long id) {
        return solicitudRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada con id: " + id));
    }

    public Solicitud crear(Long solicitanteId, Long productoId, Long operadorId, Solicitud datos) {

        // RN-01: solicitante debe estar ACTIVO
        Solicitante solicitante = solicitanteRepository.findById(solicitanteId)
                .orElseThrow(() -> new RuntimeException("Solicitante no encontrado"));
        if (!solicitante.getEstado().name().equals("ACTIVO")) {
            throw new RuntimeException("RN-01: El solicitante debe estar ACTIVO para solicitar un crédito");
        }

        // RN-03: máximo 2 solicitudes activas
        long activas = solicitudRepository.countBySolicitanteIdAndEstadoIn(
                solicitanteId,
                List.of(EstadoSolicitud.PENDIENTE, EstadoSolicitud.APROBADA)
        );
        if (activas >= 2) {
            throw new RuntimeException("RN-03: El solicitante ya tiene 2 solicitudes activas");
        }

        // RN-02 y RN-04: validar monto y plazo contra el producto
        ProductoCredito producto = productoCreditoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        if (datos.getMonto().compareTo(producto.getMontoMin()) < 0 ||
                datos.getMonto().compareTo(producto.getMontoMax()) > 0) {
            throw new RuntimeException("RN-02: El monto debe estar entre "
                    + producto.getMontoMin() + " y " + producto.getMontoMax());
        }
        if (datos.getPlazoMeses() > producto.getPlazoMaxMeses()) {
            throw new RuntimeException("RN-04: El plazo no puede superar " + producto.getPlazoMaxMeses() + " meses");
        }

        Operador operador = operadorRepository.findById(operadorId)
                .orElseThrow(() -> new RuntimeException("Operador no encontrado"));

        datos.setSolicitante(solicitante);
        datos.setProducto(producto);
        datos.setOperador(operador);
        datos.setEstado(EstadoSolicitud.PENDIENTE);
        datos.setFechaSolicitud(LocalDateTime.now());

        return solicitudRepository.save(datos);
    }

    public Solicitud aprobar(Long id) {
        Solicitud solicitud = buscarPorId(id);
        if (!solicitud.getEstado().equals(EstadoSolicitud.PENDIENTE)) {
            throw new RuntimeException("Solo se pueden aprobar solicitudes en estado PENDIENTE");
        }
        solicitud.setEstado(EstadoSolicitud.APROBADA);
        solicitud.setFechaActualizacion(LocalDateTime.now());
        return solicitudRepository.save(solicitud);
    }

    public Solicitud rechazar(Long id) {
        Solicitud solicitud = buscarPorId(id);
        if (!solicitud.getEstado().equals(EstadoSolicitud.PENDIENTE)) {
            throw new RuntimeException("Solo se pueden rechazar solicitudes en estado PENDIENTE");
        }
        solicitud.setEstado(EstadoSolicitud.RECHAZADA);
        solicitud.setFechaActualizacion(LocalDateTime.now());
        return solicitudRepository.save(solicitud);
    }
}