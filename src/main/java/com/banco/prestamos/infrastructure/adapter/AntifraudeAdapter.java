package com.banco.prestamos.infrastructure.adapter;


import com.banco.prestamos.domain.port.out.ResultadoAntifraude;
import com.banco.prestamos.domain.model.SolicitudPrestamo;
import com.banco.prestamos.domain.port.out.AntifraudePort;
import com.banco.prestamos.infrastructure.exception.AntifraudeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Component
public class AntifraudeAdapter implements AntifraudePort {

    private static final Logger log = LoggerFactory.getLogger(AntifraudeAdapter.class);
    private static final BigDecimal UMBRAL_MONTO_SOSPECHOSO = new BigDecimal("1000000");
    private static final int PUNTUAJE_MINIMO_APROBACION = 50;
    private final Random random = new Random();

    @Override
    public CompletableFuture<ResultadoAntifraude> evaluarRiesgoFraude(SolicitudPrestamo solicitud) {
        log.info("Evaluando riesgo de fraude para cliente: {}", solicitud.clienteId());

        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(random.nextInt(500) + 100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            return ejecutarEvaluacion(solicitud);
        });
    }

    private ResultadoAntifraude ejecutarEvaluacion(SolicitudPrestamo solicitud) {
        boolean esMontoSospechoso = solicitud.monto().compareTo(UMBRAL_MONTO_SOSPECHOSO) > 0;
        boolean tieneDocumentacionCompleta = solicitud.documentos() != null
                && !solicitud.documentos().isEmpty();
        boolean esCanalConfiable = esCanalValido(solicitud.canal());
        boolean tieneHistorial = solicitud.clienteId() != null;

        int puntuajeRiesgo = calcularPuntuajeRiesgo(solicitud);
        boolean aprobado = puntuajeRiesgo >= PUNTUAJE_MINIMO_APROBACION
                && tieneHistorial
                && esCanalConfiable;

        String motivo = aprobado ? "Aprobado" : generarMotivoRechazo(
                esMontoSospechoso,
                tieneDocumentacionCompleta,
                esCanalConfiable,
                tieneHistorial,
                puntuajeRiesgo
        );

        log.debug("Evaluación antifraude completada - aprobado: {}, motivo: {}, puntuaje: {}",
                aprobado, motivo, puntuajeRiesgo);

        return new ResultadoAntifraude(aprobado, motivo, puntuajeRiesgo);
    }

    private int calcularPuntuajeRiesgo(SolicitudPrestamo solicitud) {
        int puntuaje = 100;

        if (solicitud.monto().compareTo(UMBRAL_MONTO_SOSPECHOSO) > 0) {
            puntuaje -= 30;
        }

        if (solicitud.plazoMeses() != null && solicitud.plazoMeses() > 84) {
            puntuaje -= 10;
        }

        if (!esCanalValido(solicitud.canal())) {
            puntuaje -= 25;
        }

        if (solicitud.documentos() == null || solicitud.documentos().isEmpty()) {
            puntuaje -= 20;
        }

        return Math.max(0, Math.min(100, puntuaje));
    }

    private boolean esCanalValido(String canal) {
        if (canal == null || canal.isBlank()) {
            return false;
        }
        String canalLower = canal.toLowerCase();
        return canalLower.equals("web")
                || canalLower.equals("mobile")
                || canalLower.equals("sucursal")
                || canalLower.equals("callcenter");
    }

    private String generarMotivoRechazo(boolean montoSospechoso, boolean documentacionIncompleta,
                                        boolean canalInvalido, boolean sinHistorial, int puntuaje) {
        if (!sinHistorial) {
            return "Sin historial de cliente";
        }
        if (canalInvalido) {
            return "Canal no confiable";
        }
        if (montoSospechoso) {
            return "Monto sospechoso detectado";
        }
        if (!documentacionIncompleta) {
            return "Documentación incompleta";
        }
        if (puntuaje < PUNTUAJE_MINIMO_APROBACION) {
            return "Score de riesgo bajo: " + puntuaje;
        }
        return "Riesgo no determinado";
    }
}