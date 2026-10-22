package com.banco.prestamos.domain.port.out;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public interface BuroRiesgoPort {

    InformacionBuro consultarHistoriaCrediticia(UUID clienteId, BigDecimal montoSolicitado);

    record InformacionBuro(
        UUID clienteId,
        String nombreCliente,
        int scoreCrediticio,
        BigDecimal deudaActual,
        int cantidadConsultasUltimos30Dias,
        boolean tieneMorosidades,
        String categoriaRiesgo,
        LocalDateTime fechaUltimaConsulta
    ) {
        public boolean esAptoParaCredito(BigDecimal montoSolicitado, int scoreMinimo) {
            return scoreCrediticio >= scoreMinimo 
                && !tieneMorosidades 
                && deudaActual.compareTo(montoSolicitado.multiply(BigDecimal.valueOf(3))) < 0;
        }

        public String nivelRiesgo() {
            if (scoreCrediticio >= 750) return "BAJO";
            if (scoreCrediticio >= 600) return "MEDIO";
            return "ALTO";
        }
    }

    class BuroRiesgoException extends RuntimeException {
        public BuroRiesgoException(String message) {
            super(message);
        }

        public BuroRiesgoException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    class BuroRiesgoNoDisponibleException extends BuroRiesgoException {
        public BuroRiesgoNoDisponibleException(String message) {
            super(message);
        }

        public BuroRiesgoNoDisponibleException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    class ClienteNoEncontradoEnBuroException extends BuroRiesgoException {
        public ClienteNoEncontradoEnBuroException(UUID clienteId) {
            super("Cliente no encontrado en bureau de riesgos: " + clienteId);
        }
    }
}