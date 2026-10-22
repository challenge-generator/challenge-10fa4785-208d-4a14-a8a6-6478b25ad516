package com.banco.prestamos.domain.port.out;

import com.banco.prestamos.domain.model.SolicitudPrestamo;
import java.util.concurrent.CompletableFuture;

public interface AntifraudePort {
    /**
     * Evalúa el riesgo de fraude de una solicitud de préstamo.
     * @param solicitud La solicitud de préstamo a evaluar
     * @return CompletableFuture con el resultado de la evaluación de fraude
     * @throws AntifraudeException si ocurre un error durante la evaluación
     */
    CompletableFuture<ResultadoAntifraude> evaluarRiesgoFraude(SolicitudPrestamo solicitud);
    
    /**
     * Resultado de la evaluación de antifraude.
     */
    record ResultadoAntifraude(
        boolean riesgoAlto,
        String motivo
    ) {
        public boolean esAprobable() {
            return !riesgoAlto;
        }
    }
    
    /**
     * Excepción lanzada cuando ocurre un error en la evaluación de antifraude.
     */
    class AntifraudeException extends RuntimeException {
        public AntifraudeException(String message) {
            super(message);
        }
        
        public AntifraudeException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}