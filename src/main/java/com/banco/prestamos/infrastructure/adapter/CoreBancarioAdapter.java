package com.banco.prestamos.infrastructure.adapter;

import com.banco.prestamos.domain.model.RespuestaPrestamo;
import com.banco.prestamos.domain.model.SolicitudPrestamo;
import com.banco.prestamos.domain.port.out.CoreBancarioPort;
import com.banco.prestamos.infrastructure.exception.PrestamoException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Component
public class CoreBancarioAdapter implements CoreBancarioPort {

    private static final Logger logger = LoggerFactory.getLogger(CoreBancarioAdapter.class);
    private static final String SERVICIO_CORE = "coreBancario";

    @Override
    @Retry(name = SERVICIO_CORE)
    @CircuitBreaker(name = SERVICIO_CORE, fallbackMethod = "registrarPrestamoFallback")
    public CompletableFuture<RespuestaPrestamo> registrarPrestamo(SolicitudPrestamo solicitud) {
        logger.info("Registrando préstamo en core bancario para cliente: {}, monto: {}",
                solicitud.clienteId(), solicitud.monto());

        try {
            String numeroOperacion = generarNumeroOperacion();
            BigDecimal tasaInteres = calcularTasaInteres(solicitud.monto(), solicitud.plazoMeses());

            boolean registrado = invocarCoreBancario(solicitud, numeroOperacion, tasaInteres);

            if (registrado) {
                logger.info("Préstamo registrado exitosamente en core: {}", numeroOperacion);
                return CompletableFuture.completedFuture(
                    RespuestaPrestamo.aprobada(numeroOperacion, solicitud.clienteId(), tasaInteres)
                );
            } else {
                logger.warn("Core bancario rechazó el registro del préstamo");
                return CompletableFuture.completedFuture(
                    RespuestaPrestamo.rechazada(numeroOperacion, solicitud.clienteId(), "Rechazo por políticas del core")
                );
            }
        } catch (Exception e) {
            logger.error("Error al registrar préstamo en core bancario: {}", e.getMessage(), e);
            throw new PrestamoException("Error en comunicación con core bancario: " + e.getMessage(), e);
        }
    }

    private String generarNumeroOperacion() {
        String timestamp = String.valueOf(System.currentTimeMillis()).substring(5);
        String uuid = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return String.format("OP%s%s", timestamp, uuid);
    }

    private BigDecimal calcularTasaInteres(BigDecimal monto, Integer plazoMeses) {
        BigDecimal tasaBase = new BigDecimal("0.15");
        BigDecimal ajustePlazo = BigDecimal.ZERO;

        if (plazoMeses > 36) {
            ajustePlazo = new BigDecimal("0.03");
        } else if (plazoMeses > 24) {
            ajustePlazo = new BigDecimal("0.02");
        } else if (plazoMeses > 12) {
            ajustePlazo = new BigDecimal("0.01");
        }

        if (monto.compareTo(new BigDecimal("100000")) > 0) {
            ajustePlazo = ajustePlazo.subtract(new BigDecimal("0.005"));
        }

        return tasaBase.add(ajustePlazo);
    }

    private boolean invocarCoreBancario(SolicitudPrestamo solicitud, String numeroOperacion, BigDecimal tasaInteres) {
        logger.debug("Invocando servicio de core bancario con operación: {}", numeroOperacion);
        return true;
    }

    private CompletableFuture<RespuestaPrestamo> registrarPrestamoFallback(
            SolicitudPrestamo solicitud, Throwable t) {
        logger.warn("Fallback activado para core bancario. Error: {}", t.getMessage());
        String numeroOperacion = "FALLBACK-" + System.currentTimeMillis();
        return CompletableFuture.completedFuture(
            RespuestaPrestamo.error(numeroOperacion, solicitud.clienteId())
        );
    }
}