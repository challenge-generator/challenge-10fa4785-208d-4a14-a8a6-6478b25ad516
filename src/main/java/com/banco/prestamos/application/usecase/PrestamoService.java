package com.banco.prestamos.application.usecase;


import com.banco.prestamos.domain.port.out.ResultadoAntifraude;
import com.banco.prestamos.domain.model.SolicitudPrestamo;
import com.banco.prestamos.domain.model.RespuestaPrestamo;
import com.banco.prestamos.domain.port.out.AntifraudePort;
import com.banco.prestamos.domain.port.out.BuroRiesgoPort;
import com.banco.prestamos.domain.port.out.CoreBancarioPort;
import com.banco.prestamos.infrastructure.exception.PrestamoException;
import com.banco.prestamos.infrastructure.exception.BuroRiesgoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
public class PrestamoService {

    private static final Logger log = LoggerFactory.getLogger(PrestamoService.class);
    private static final int TIMEOUT_SECONDS = 30;
    private static final BigDecimal MONTO_MINIMO = new BigDecimal("10000");
    private static final BigDecimal MONTO_MAXIMO = new BigDecimal("5000000");
    private static final int PLAZO_MINIMO_MESES = 6;
    private static final int PLAZO_MAXIMO_MESES = 120;
    private static final BigDecimal TASA_BASE = new BigDecimal("0.12");
    private static final BigDecimal TASA_ALTO_RIESGO = new BigDecimal("0.25");

    private final AntifraudePort antifraudePort;
    private final BuroRiesgoPort buroRiesgoPort;
    private final CoreBancarioPort coreBancarioPort;

    public PrestamoService(AntifraudePort antifraudePort,
                          BuroRiesgoPort buroRiesgoPort,
                          CoreBancarioPort coreBancarioPort) {
        this.antifraudePort = antifraudePort;
        this.buroRiesgoPort = buroRiesgoPort;
        this.coreBancarioPort = coreBancarioPort;
    }

    public RespuestaPrestamo procesarSolicitud(SolicitudPrestamo solicitud) {
        log.info("Iniciando procesamiento de solicitud para cliente: {} canal: {}",
                solicitud.clienteId(), solicitud.canal());

        validarSolicitud(solicitud);

        String numeroOperacion = generarNumeroOperacion(solicitud);

        try {
            AntifraudePort.ResultadoAntifraude resultadoAntifraude = evaluarRiesgoFraude(solicitud)
                    .get(TIMEOUT_SECONDS, TimeUnit.SECONDS);

            if (!resultadoAntifraude.aprobado()) {
                log.warn("Solicitud rechazada por antifraude: {}", resultadoAntifraude.motivo());
                return RespuestaPrestamo.rechazada(numeroOperacion,
                        solicitud.clienteId(),
                        "Fraude detectado: " + resultadoAntifraude.motivo());
            }

            BuroRiesgoPort.ResultadoBuro resultadoBuro = consultarBuroRiesgo(solicitud);

            if (resultadoBuro.tieneMorosidad()) {
                log.warn("Cliente {} tiene morosidad en buró de riesgos", solicitud.clienteId());
                return RespuestaPrestamo.rechazada(numeroOperacion,
                        solicitud.clienteId(),
                        "Cliente con morosidad activa en buró de riesgos");
            }

            BigDecimal tasaInteres = calcularTasa(resultadoBuro, solicitud.monto());
            BigDecimal cuotaMensual = calcularCuota(solicitud.monto(), tasaInteres, solicitud.plazoMeses());

            boolean aprobadoCore = coreBancarioPort.aprobarPrestamo(solicitud.clienteId(),
                    solicitud.monto(), tasaInteres, solicitud.plazoMeses(), numeroOperacion);

            if (!aprobadoCore) {
                log.warn("Core bancario rechazó la operación para cliente: {}", solicitud.clienteId());
                return RespuestaPrestamo.rechazada(numeroOperacion,
                        solicitud.clienteId(),
                        "Rechazo del core bancario");
            }

            log.info("Préstamo aprobado para cliente: {} número operación: {}",
                    solicitud.clienteId(), numeroOperacion);

            return RespuestaPrestamo.aprobada(numeroOperacion,
                    solicitud.clienteId(),
                    tasaInteres,
                    cuotaMensual,
                    solicitud.plazoMeses(),
                    Instant.now().plus(Duration.ofDays(30)).toString());

        } catch (BuroRiesgoException e) {
            log.error("Error al consultar buró de riesgos: {}", e.getMessage());
            throw new PrestamoException("Error al verificar historial crediticio: " + e.getMessage(), e);
        } catch (Exception e) {
            log.error("Error procesamiento solicitud: {}", e.getMessage(), e);
            return RespuestaPrestamo.error(numeroOperacion, solicitud.clienteId());
        }
    }

    private void validarSolicitud(SolicitudPrestamo solicitud) {
        if (solicitud == null) {
            throw new PrestamoException("La solicitud no puede ser nula");
        }
        if (solicitud.clienteId() == null) {
            throw new PrestamoException("El ID del cliente es obligatorio");
        }
        if (solicitud.monto() == null || solicitud.monto().compareTo(MONTO_MINIMO) < 0
                || solicitud.monto().compareTo(MONTO_MAXIMO) > 0) {
            throw new PrestamoException("El monto debe estar entre " + MONTO_MINIMO + " y " + MONTO_MAXIMO);
        }
        if (solicitud.plazoMeses() == null || solicitud.plazoMeses() < PLAZO_MINIMO_MESES
                || solicitud.plazoMeses() > PLAZO_MAXIMO_MESES) {
            throw new PrestamoException("El plazo debe estar entre " + PLAZO_MINIMO_MESES + " y " + PLAZO_MAXIMO_MESES + " meses");
        }
        if (solicitud.canal() == null || solicitud.canal().isBlank()) {
            throw new PrestamoException("El canal de origen es obligatorio");
        }
    }

    private CompletableFuture<AntifraudePort.ResultadoAntifraude> evaluarRiesgoFraude(SolicitudPrestamo solicitud) {
        return antifraudePort.evaluarRiesgoFraude(solicitud);
    }

    private BuroRiesgoPort.ResultadoBuro consultarBuroRiesgo(SolicitudPrestamo solicitud) {
        return buroRiesgoPort.consultarHistorial(solicitud.clienteId());
    }

    private BigDecimal calcularTasa(BuroRiesgoPort.ResultadoBuro resultadoBuro, BigDecimal monto) {
        int score = resultadoBuro.scoreCrediticio();
        boolean tieneHistorial = resultadoBuro.tieneHistorial();

        if (!tieneHistorial || score < 600) {
            return TASA_ALTO_RIESGO;
        } else if (score >= 600 && score < 700) {
            return TASA_BASE.add(new BigDecimal("0.03"));
        } else if (score >= 700 && score < 800) {
            return TASA_BASE;
        } else {
            return TASA_BASE.subtract(new BigDecimal("0.02"));
        }
    }

    private BigDecimal calcularCuota(BigDecimal monto, BigDecimal tasaAnual, Integer plazoMeses) {
        BigDecimal tasaMensual = tasaAnual.divide(new BigDecimal("12"), 10, java.math.RoundingMode.HALF_UP);
        BigDecimal factor = tasaMensual.multiply(
                BigDecimal.ONE.add(tasaMensual).pow(plazoMeses)
        ).divide(
                BigDecimal.ONE.add(tasaMensual).pow(plazoMeses).subtract(BigDecimal.ONE),
                10, java.math.RoundingMode.HALF_UP
        );
        return monto.multiply(factor).setScale(2, java.math.RoundingMode.HALF_UP);
    }

    private String generarNumeroOperacion(SolicitudPrestamo solicitud) {
        String timestamp = String.valueOf(Instant.now().toEpochMilli());
        String uuid = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        String canal = solicitud.canal().substring(0, Math.min(3, solicitud.canal().length())).toUpperCase();
        return "PR-" + canal + "-" + timestamp + "-" + uuid;
    }
}