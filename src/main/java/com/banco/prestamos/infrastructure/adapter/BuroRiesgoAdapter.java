package com.banco.prestamos.infrastructure.adapter;

import com.banco.prestamos.domain.port.out.BuroRiesgoPort;
import com.banco.prestamos.infrastructure.exception.BuroRiesgoException;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

@Component
public class BuroRiesgoAdapter implements BuroRiesgoPort {

    private static final Logger log = LoggerFactory.getLogger(BuroRiesgoAdapter.class);
    private static final BigDecimal INGRESO_MINIMO = new BigDecimal("10000");
    private final Random random = new Random();

    @Override
    @Retry(name = "buroRiesgoRetry")
    public ResultadoBuro consultarHistorial(UUID clienteId) {
        log.info("Consultando historial en buró de riesgo para cliente: {}", clienteId);

        try {
            simularLlamadaExterno();
            return construirResultado(clienteId);
        } catch (Exception e) {
            log.error("Error al consultar buró de riesgo para cliente {}: {}", clienteId, e.getMessage());
            throw new BuroRiesgoException("Error al consultar buró de riesgo: " + e.getMessage(), e);
        }
    }

    private void simularLlamadaExterno() {
        boolean debeFallar = random.nextInt(100) < 10;
        if (debeFallar) {
            throw new RuntimeException("Servicio de buró de riesgo no disponible temporalmente");
        }
    }

    private ResultadoBuro construirResultado(UUID clienteId) {
        int hashCode = clienteId.hashCode();
        int scoreBase = Math.abs(hashCode % 850);
        int score = Math.max(300, scoreBase + 300);

        boolean tieneMorosidad = score < 600 && random.nextBoolean();
        boolean tieneHistorial = score >= 500;
        int cantidadCreditos = Math.abs(hashCode % 20);
        int creditosVencidos = tieneMorosidad ? random.nextInt(5) + 1 : 0;
        BigDecimal deudaTotal = new BigDecimal(Math.abs(hashCode % 1000000));
        BigDecimal ingresoMensual = new BigDecimal(Math.abs((hashCode * 2) % 50000) + INGRESO_MINIMO.intValue());
        LocalDate ultimaConsulta = LocalDate.now().minusDays(random.nextInt(90));
        String categoria = determinarCategoria(score);

        return new ResultadoBuro(
                clienteId,
                score,
                tieneMorosidad,
                tieneHistorial,
                cantidadCreditos,
                creditosVencidos,
                deudaTotal,
                ingresoMensual,
                ultimaConsulta,
                categoria
        );
    }

    private String determinarCategoria(int score) {
        if (score >= 800) return "A";
        if (score >= 700) return "B";
        if (score >= 600) return "C";
        if (score >= 500) return "D";
        return "E";
    }
}