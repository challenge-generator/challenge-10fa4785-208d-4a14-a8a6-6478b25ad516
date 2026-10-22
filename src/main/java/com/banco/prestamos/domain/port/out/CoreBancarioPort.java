package com.banco.prestamos.domain.port.out;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public interface CoreBancarioPort {

    ResultadoRegistroPrestamo registrarPrestamo(
        UUID clienteId,
        String numeroOperacion,
        BigDecimal montoSolicitado,
        int plazoMeses,
        BigDecimal tasaInteres,
        String canalOrigen
    );

    InformacionCliente obtenerInformacionCliente(UUID clienteId);

    boolean verificarIdentidadCliente(UUID clienteId, String numeroDocumento);

    record ResultadoRegistroPrestamo(
        String numeroPrestamo,
        UUID clienteId,
        BigDecimal montoAprobado,
        BigDecimal tasaInteresAprobada,
        int plazoAprobado,
        LocalDateTime fechaAprobacion,
        String estado,
        String mensaje
    ) {
        public boolean esExitoso() {
            return "APROBADO".equals(estado);
        }

        public static ResultadoRegistroPrestamo aprobado(
            String numeroPrestamo,
            UUID clienteId,
            BigDecimal monto,
            BigDecimal tasa,
            int plazo
        ) {
            return new ResultadoRegistroPrestamo(
                numeroPrestamo,
                clienteId,
                monto,
                tasa,
                plazo,
                LocalDateTime.now(),
                "APROBADO",
                "Prestamo registrado exitosamente"
            );
        }

        public static ResultadoRegistroPrestamo rechazado(
            String numeroPrestamo,
            UUID clienteId,
            String motivo
        ) {
            return new ResultadoRegistroPrestamo(
                numeroPrestamo,
                clienteId,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                0,
                LocalDateTime.now(),
                "RECHAZADO",
                motivo
            );
        }
    }

    record InformacionCliente(
        UUID clienteId,
        String numeroDocumento,
        String nombreCompleto,
        String tipoDocumento,
        LocalDate fechaNacimiento,
        String segmento,
        BigDecimal ingresoMensual,
        boolean tieneCupoActivo,
        BigDecimal cupoDisponible,
        LocalDateTime fechaAlta
    ) {
        public boolean puedeSolicitarPrestamo(BigDecimal montoSolicitado) {
            return tieneCupoActivo 
                && cupoDisponible.compareTo(montoSolicitado) >= 0
                && ingresoMensual.compareTo(montoSolicitado.divide(BigDecimal.valueOf(12))) > 0;
        }
    }

    class CoreBancarioException extends RuntimeException {
        public CoreBancarioException(String message) {
            super(message);
        }

        public CoreBancarioException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    class ClienteNoEncontradoException extends CoreBancarioException {
        public ClienteNoEncontradoException(UUID clienteId) {
            super("Cliente no encontrado en core bancario: " + clienteId);
        }
    }

    class ErrorRegistroPrestamoException extends CoreBancarioException {
        public ErrorRegistroPrestamoException(String message) {
            super(message);
        }

        public ErrorRegistroPrestamoException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}