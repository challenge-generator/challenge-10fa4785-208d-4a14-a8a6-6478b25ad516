package com.banco.prestamos.infrastructure.exception;

import java.time.Instant;
import java.util.UUID;

public class IdempotencyException extends RuntimeException {

    private final String numeroOperacion;
    private final String canal;
    private final Instant timestamp;
    private final Motivo motivo;
    private final String requestId;
    private final boolean reintentoPermitido;

    public enum Motivo {
        DUPLICADO_DETECTADO("La operación ya fue procesada anteriormente"),
        CLAVE_DUPLICADA("La clave de idempotencia ya existe en el sistema"),
        OPERACION_CONFLICTO("Conflicto con otra operación en curso"),
        EXPIRADO("La solicitud original expiró y no se puede reintentar"),
        ESTADO_INVALIDO("El estado de la operación no permite reintento"),
        DATOS_INCONSISTENTES("Los datos de la solicitud no coinciden con la original");

        private final String descripcion;

        Motivo(String descripcion) {
            this.descripcion = descripcion;
        }

        public String getDescripcion() {
            return descripcion;
        }
    }

    public IdempotencyException(String mensaje) {
        super(mensaje);
        this.numeroOperacion = null;
        this.canal = null;
        this.timestamp = Instant.now();
        this.motivo = null;
        this.requestId = null;
        this.reintentoPermitido = false;
    }

    public IdempotencyException(String mensaje, Motivo motivo) {
        super(mensaje);
        this.numeroOperacion = null;
        this.canal = null;
        this.timestamp = Instant.now();
        this.motivo = motivo;
        this.requestId = null;
        this.reintentoPermitido = calcularReintento(motivo);
    }

    public IdempotencyException(String mensaje, String numeroOperacion, String canal) {
        super(mensaje);
        this.numeroOperacion = numeroOperacion;
        this.canal = canal;
        this.timestamp = Instant.now();
        this.motivo = Motivo.DUPLICADO_DETECTADO;
        this.requestId = null;
        this.reintentoPermitido = false;
    }

    public IdempotencyException(String mensaje, String numeroOperacion, String canal, Motivo motivo) {
        super(mensaje);
        this.numeroOperacion = numeroOperacion;
        this.canal = canal;
        this.timestamp = Instant.now();
        this.motivo = motivo;
        this.requestId = null;
        this.reintentoPermitido = calcularReintento(motivo);
    }

    public IdempotencyException(String mensaje, String numeroOperacion, String canal, Motivo motivo, String requestId) {
        super(mensaje);
        this.numeroOperacion = numeroOperacion;
        this.canal = canal;
        this.timestamp = Instant.now();
        this.motivo = motivo;
        this.requestId = requestId;
        this.reintentoPermitido = calcularReintento(motivo);
    }

    public IdempotencyException(String mensaje, Throwable causa) {
        super(mensaje, causa);
        this.numeroOperacion = null;
        this.canal = null;
        this.timestamp = Instant.now();
        this.motivo = null;
        this.requestId = null;
        this.reintentoPermitido = false;
    }

    private IdempotencyException(Builder builder) {
        super(builder.mensaje);
        this.numeroOperacion = builder.numeroOperacion;
        this.canal = builder.canal;
        this.timestamp = builder.timestamp != null ? builder.timestamp : Instant.now();
        this.motivo = builder.motivo;
        this.requestId = builder.requestId;
        this.reintentoPermitido = builder.reintentoPermitido;
    }

    private static boolean calcularReintento(Motivo motivo) {
        if (motivo == null) {
            return false;
        }
        return motivo == Motivo.EXPIRADO || motivo == Motivo.ESTADO_INVALIDO;
    }

    public String getNumeroOperacion() {
        return numeroOperacion;
    }

    public String getCanal() {
        return canal;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public Motivo getMotivo() {
        return motivo;
    }

    public String getRequestId() {
        return requestId;
    }

    public boolean isReintentoPermitido() {
        return reintentoPermitido;
    }

    public String getClaveIdempotencia() {
        if (numeroOperacion != null && canal != null) {
            return canal + ":" + numeroOperacion;
        }
        return null;
    }

    public String getDetallesCompletos() {
        StringBuilder sb = new StringBuilder();
        sb.append("IdempotencyException{");
        sb.append("motivo=").append(motivo != null ? motivo.name() : "N/A");
        sb.append(", mensaje=").append(getMessage());
        if (numeroOperacion != null) {
            sb.append(", numeroOperacion=").append(numeroOperacion);
        }
        if (canal != null) {
            sb.append(", canal=").append(canal);
        }
        if (requestId != null) {
            sb.append(", requestId=").append(requestId);
        }
        sb.append(", timestamp=").append(timestamp);
        sb.append(", reintentoPermitido=").append(reintentoPermitido);
        sb.append("}");
        return sb.toString();
    }

    public static IdempotencyException duplicado(String numeroOperacion, String canal) {
        return new IdempotencyException(
            "La operación " + numeroOperacion + " ya fue procesada en el canal " + canal,
            numeroOperacion,
            canal,
            Motivo.DUPLICADO_DETECTADO
        );
    }

    public static IdempotencyException claveDuplicada(String clave) {
        return new IdempotencyException(
            "La clave de idempotencia '" + clave + "' ya existe",
            Motivo.CLAVE_DUPLICADA
        );
    }

    public static IdempotencyException expirado(String numeroOperacion, String canal) {
        return new IdempotencyException(
            "La solicitud original ha expirado y no puede ser reintentada",
            numeroOperacion,
            canal,
            Motivo.EXPIRADO
        );
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String mensaje;
        private String numeroOperacion;
        private String canal;
        private Instant timestamp;
        private Motivo motivo;
        private String requestId;
        private boolean reintentoPermitido = false;

        public Builder mensaje(String mensaje) {
            this.mensaje = mensaje;
            return this;
        }

        public Builder numeroOperacion(String numeroOperacion) {
            this.numeroOperacion = numeroOperacion;
            return this;
        }

        public Builder canal(String canal) {
            this.canal = canal;
            return this;
        }

        public Builder timestamp(Instant timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder motivo(Motivo motivo) {
            this.motivo = motivo;
            return this;
        }

        public Builder requestId(String requestId) {
            this.requestId = requestId;
            return this;
        }

        public Builder reintentoPermitido(boolean reintentoPermitido) {
            this.reintentoPermitido = reintentoPermitido;
            return this;
        }

        public IdempotencyException build() {
            if (mensaje == null || mensaje.isBlank()) {
                throw new IllegalStateException("El mensaje de la excepción no puede ser nulo o vacío");
            }
            return new IdempotencyException(this);
        }
    }
}