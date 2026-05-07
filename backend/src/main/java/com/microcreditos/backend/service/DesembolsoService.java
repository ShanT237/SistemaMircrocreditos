package com.microcreditos.backend.service;

import com.microcreditos.backend.entity.*;
import com.microcreditos.backend.enums.EstadoCuota;
import com.microcreditos.backend.enums.EstadoDesembolso;
import com.microcreditos.backend.enums.EstadoSolicitud;
import com.microcreditos.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DesembolsoService {

    private final DesembolsoRepository desembolsoRepository;
    private final SolicitudRepository solicitudRepository;
    private final OperadorRepository operadorRepository;
    private final CronogramaPagoRepository cronogramaPagoRepository;

    @Transactional
    public Desembolso registrar(Long solicitudId, Long operadorId, Desembolso datos) {

        // RN-05: solo solicitudes APROBADAS
        Solicitud solicitud = solicitudRepository.findById(solicitudId)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));
        if (!solicitud.getEstado().equals(EstadoSolicitud.APROBADA)) {
            throw new RuntimeException("RN-05: Solo se puede desembolsar una solicitud APROBADA");
        }

        // Verificar que no tenga ya un desembolso
        if (desembolsoRepository.existsBySolicitudId(solicitudId)) {
            throw new RuntimeException("Esta solicitud ya tiene un desembolso registrado");
        }

        Operador operador = operadorRepository.findById(operadorId)
                .orElseThrow(() -> new RuntimeException("Operador no encontrado"));

        // RN-06: calcular cuota mensual
        BigDecimal cuota = calcularCuotaMensual(
                solicitud.getMonto(),
                solicitud.getProducto().getTasaInteres(),
                solicitud.getPlazoMeses()
        );

        datos.setSolicitud(solicitud);
        datos.setOperador(operador);
        datos.setMonto(solicitud.getMonto());
        datos.setCuotaMensual(cuota);
        datos.setFechaDesembolso(LocalDateTime.now());
        datos.setEstado(EstadoDesembolso.EJECUTADO);

        Desembolso desembolso = desembolsoRepository.save(datos);

        // Generar cronograma de pagos
        generarCronograma(desembolso, solicitud);

        // Actualizar estado solicitud
        solicitud.setEstado(EstadoSolicitud.CANCELADA);
        solicitudRepository.save(solicitud);

        return desembolso;
    }

    // RN-06: fórmula de cuota mensual
    private BigDecimal calcularCuotaMensual(BigDecimal monto, BigDecimal tasaAnual, int plazoMeses) {
        BigDecimal tasaMensual = tasaAnual.divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP);
        BigDecimal unoPlusTasa = BigDecimal.ONE.add(tasaMensual);
        BigDecimal potencia = unoPlusTasa.pow(plazoMeses, new MathContext(10));
        BigDecimal numerador = monto.multiply(tasaMensual).multiply(potencia);
        BigDecimal denominador = potencia.subtract(BigDecimal.ONE);
        return numerador.divide(denominador, 2, RoundingMode.HALF_UP);
    }

    // Genera todas las filas del cronograma
    private void generarCronograma(Desembolso desembolso, Solicitud solicitud) {
        BigDecimal saldo = solicitud.getMonto();
        BigDecimal tasaMensual = solicitud.getProducto().getTasaInteres()
                .divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP);
        BigDecimal cuota = desembolso.getCuotaMensual();
        List<CronogramaPago> cronograma = new ArrayList<>();

        for (int i = 1; i <= solicitud.getPlazoMeses(); i++) {
            BigDecimal interes = saldo.multiply(tasaMensual).setScale(2, RoundingMode.HALF_UP);
            BigDecimal capital = cuota.subtract(interes).setScale(2, RoundingMode.HALF_UP);
            saldo = saldo.subtract(capital).setScale(2, RoundingMode.HALF_UP);

            CronogramaPago fila = CronogramaPago.builder()
                    .desembolso(desembolso)
                    .numeroCuota(i)
                    .fechaVencimiento(LocalDate.now().plusMonths(i))
                    .valorCuota(cuota)
                    .capital(capital)
                    .interes(interes)
                    .saldoRestante(saldo.max(BigDecimal.ZERO))
                    .estado(EstadoCuota.PENDIENTE)
                    .build();

            cronograma.add(fila);
        }
        cronogramaPagoRepository.saveAll(cronograma);
    }

    public List<Desembolso> listarTodos() {
        return desembolsoRepository.findAll();
    }

    public Desembolso buscarPorId(Long id) {
        return desembolsoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Desembolso no encontrado con id: " + id));
    }
}