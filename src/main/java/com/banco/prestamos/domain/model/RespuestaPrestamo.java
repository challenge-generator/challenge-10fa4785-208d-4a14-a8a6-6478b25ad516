package com.banco.prestamos.domain.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record RespuestaPrestamo(
    @NotNull(message = "El número de operación no puede ser nulo")
    String numeroOperacion,
    
    @NotNull(message = "El identificador del cliente no puede ser nulo")
    UUID clienteId,
    
    @NotNull(message = "El estado de la respuesta no puede ser nulo")
    EstadoRespuesta estado,
    
    @Positive(message = "La tasa de interés debe ser positiva")
    BigDecimal tasaInteres,
    
    @Positive(message = "El monto aprobado debe ser positivo")
    BigDecimal montoAprobado,
    
    Integer plazoMeses,
    
    String numeroContrato,
    
    @NotNull(message = "La fecha de respuesta no puede ser nula")
    LocalDateTime fechaRespuesta,
    
    String motivoRechazo
) {
    public enum EstadoRespuesta {
        APROBADA,
        RECHAZADA,
        ERROR
    }
    
    public RespuestaPrestamo {
        if (tasaInteres != null && tasaInteres.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("La tasa de interés debe ser positiva");
        }
        if (montoAprobado != null && montoAprobado.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto aprobado debe ser positivo");
        }
        if (fechaRespuesta == null) {
            fechaRespuesta = LocalDateTime.now();
        }
    }
    
    public static RespuestaPrestamo aprobada(String numeroOperacion, UUID clienteId, BigDecimal tasaInteres,
                                           BigDecimal montoAprobado, Integer plazoMeses, String numeroContrato) {
        return new RespuestaPrestamo(
            numeroOperacion,
            clienteId,
            EstadoRespuesta.APROBADA,
            tasaInteres,
            montoAprobado,
            plazoMeses,
            numeroContrato,
            LocalDateTime.now(),
            null
        );
    }
    
    public static RespuestaPrestamo rechazada(String numeroOperacion, UUID clienteId, String motivoRechazo) {
        return new RespuestaPrestamo(
            numeroOperacion,
            clienteId,
            EstadoRespuesta.RECHAZADA,
            BigDecimal.ZERO,
            BigDecimal.ZERO,
            null,
            null,
            LocalDateTime.now(),
            motivoRechazo
        );
    }
    
    public static RespuestaPrestamo error(String numeroOperacion, UUID clienteId) {
        return new RespuestaPrestamo(
            numeroOperacion,
            clienteId,
            EstadoRespuesta.ERROR,
            BigDecimal.ZERO,
            BigDecimal.ZERO,
            null,
            null,
            LocalDateTime.now(),
            "Error en el procesamiento de la solicitud"
        );
    }
}