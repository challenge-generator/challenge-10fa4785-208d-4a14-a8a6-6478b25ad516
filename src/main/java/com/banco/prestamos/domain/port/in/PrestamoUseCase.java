package com.banco.prestamos.domain.port.in;

import com.banco.prestamos.domain.model.RespuestaPrestamo;
import com.banco.prestamos.domain.model.SolicitudPrestamo;
import java.util.UUID;

public interface PrestamoUseCase {

    RespuestaPrestamo procesarSolicitud(SolicitudPrestamo solicitud);

    RespuestaPrestamo consultarEstadoSolicitud(UUID solicitudId);

    boolean verificarIdempotencia(String numeroOperacion, String canal);

    SolicitudPrestamo crearSolicitud(
        UUID clienteId,
        String numeroOperacion,
        String canal,
        java.math.BigDecimal monto,
        int plazoMeses,
        String proposito
    );

    record SolicitudPrestamoCommand(
        UUID clienteId,
        String numeroOperacion,
        String canal,
        java.math.BigDecimal monto,
        int plazoMeses,
        String proposito,
        String numeroDocumento
    ) {
        public SolicitudPrestamoCommand {
            if (monto == null || monto.compareTo(java.math.BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("El monto debe ser mayor a cero");
            }
            if (plazoMeses <= 0 || plazoMeses > 360) {
                throw new IllegalArgumentException("El plazo debe estar entre 1 y 360 meses");
            }
            if (clienteId == null) {
                throw new IllegalArgumentException("El ID del cliente es obligatorio");
            }
            if (numeroOperacion == null || numeroOperacion.isBlank()) {
                throw new IllegalArgumentException("El numero de operacion es obligatorio");
            }
            if (canal == null || canal.isBlank()) {
                throw new IllegalArgumentException("El canal es obligatorio");
            }
        }
    }

    record RespuestaPrestamoView(
        UUID solicitudId,
        String numeroOperacion,
        String estado,
        String numeroPrestamo,
        java.math.BigDecimal montoAprobado,
        java.math.BigDecimal tasaInteres,
        Integer plazoMeses,
        String mensaje,
        String motivoRechazo
    ) {
        public static RespuestaPrestamoView desdeRespuesta(RespuestaPrestamo respuesta, UUID solicitudId) {
            return new RespuestaPrestamoView(
                solicitudId,
                respuesta.numeroOperacion(),
                respuesta.estado().name(),
                respuesta.numeroPrestamo(),
                respuesta.montoAprobado(),
                respuesta.tasaInteres(),
                respuesta.plazoMeses(),
                respuesta.mensaje(),
                respuesta.motivoRechazo()
            );
        }
    }

    class PrestamoUseCaseException extends RuntimeException {
        public PrestamoUseCaseException(String message) {
            super(message);
        }

        public PrestamoUseCaseException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    class SolicitudDuplicadaException extends PrestamoUseCaseException {
        public SolicitudDuplicadaException(String numeroOperacion, String canal) {
            super("Ya existe una solicitud con numero de operacion " + numeroOperacion + " para el canal " + canal);
        }
    }

    class SolicitudInvalidaException extends PrestamoUseCaseException {
        public SolicitudInvalidaException(String message) {
            super(message);
        }
    }
}