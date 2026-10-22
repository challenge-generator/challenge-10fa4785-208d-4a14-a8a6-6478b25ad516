package com.banco.prestamos.domain.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record SolicitudPrestamo(
    @NotNull(message = "El número de operación no puede ser nulo")
    String numeroOperacion,
    
    @NotNull(message = "El canal no puede ser nulo")
    @Size(min = 3, max = 10, message = "El canal debe tener entre 3 y 10 caracteres")
    String canal,
    
    @NotNull(message = "El identificador del cliente no puede ser nulo")
    UUID clienteId,
    
    @NotNull(message = "El monto solicitado no puede ser nulo")
    @Positive(message = "El monto solicitado debe ser positivo")
    BigDecimal montoSolicitado,
    
    @NotNull(message = "El plazo en meses no puede ser nulo")
    @Positive(message = "El plazo en meses debe ser positivo")
    Integer plazoMeses,
    
    @NotNull(message = "La fecha de solicitud no puede ser nula")
    LocalDateTime fechaSolicitud,
    
    @NotNull(message = "El propósito del préstamo no puede ser nulo")
    @Size(max = 100, message = "El propósito del préstamo no puede exceder 100 caracteres")
    String proposito,
    
    @NotNull(message = "El estado de la solicitud no puede ser nulo")
    EstadoSolicitud estado,
    
    String motivoRechazo
) {
    public enum EstadoSolicitud {
        RECIBIDA,
        EN_PROCESO,
        APROBADA,
        RECHAZADA
    }
    
    public SolicitudPrestamo {
        if (montoSolicitado != null && montoSolicitado.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto solicitado debe ser positivo");
        }
        if (plazoMeses != null && plazoMeses <= 0) {
            throw new IllegalArgumentException("El plazo en meses debe ser positivo");
        }
        if (fechaSolicitud == null) {
            fechaSolicitud = LocalDateTime.now();
        }
        if (estado == null) {
            estado = EstadoSolicitud.RECIBIDA;
        }
    }
    
    public SolicitudPrestamo conEstado(EstadoSolicitud nuevoEstado) {
        return new SolicitudPrestamo(
            this.numeroOperacion,
            this.canal,
            this.clienteId,
            this.montoSolicitado,
            this.plazoMeses,
            this.fechaSolicitud,
            this.proposito,
            nuevoEstado,
            this.motivoRechazo
        );
    }
    
    public SolicitudPrestamo conMotivoRechazo(String motivo) {
        return new SolicitudPrestamo(
            this.numeroOperacion,
            this.canal,
            this.clienteId,
            this.montoSolicitado,
            this.plazoMeses,
            this.fechaSolicitud,
            this.proposito,
            EstadoSolicitud.RECHAZADA,
            motivo
        );
    }
}