package com.banco.prestamos.infrastructure.exception;

import java.time.Instant;
import java.util.UUID;

public class PrestamoException extends RuntimeException {

    private final String codigoError;
    private final String numeroOperacion;
    private final UUID clienteId;
    private final Instant timestamp;
    private final String canal;
    private final Severity severity;

    public enum Severity {
        INFO, WARN, ERROR, CRITICAL
    }

    public PrestamoException(String mensaje) {
        super(mensaje);
        this.codigoError = "PRESTAMO_001";
        this.numeroOperacion = null;
        this.clienteId = null;
        this.timestamp = Instant.now();
        this.canal = null;
        this.severity = Severity.ERROR;
    }

    public PrestamoException(String mensaje, String codigoError) {
        super(mensaje);
        this.codigoError = codigoError;
        this.numeroOperacion = null;
        this.clienteId = null;
        this.timestamp = Instant.now();
        this.canal = null;
        this.severity = Severity.ERROR;
    }

    public PrestamoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
        this.codigoError = "PRESTAMO_001";
        this.numeroOperacion = null;
        this.clienteId = null;
        this.timestamp = Instant.now();
        this.canal = null;
        this.severity = Severity.ERROR;
    }

    public PrestamoException(String mensaje, String codigoError, Throwable causa) {
        super(mensaje, causa);
        this.codigoError = codigoError;
        this.numeroOperacion = null;
        this.clienteId = null;
        this.timestamp = Instant.now();
        this.canal = null;
        this.severity = Severity.ERROR;
    }

    private PrestamoException(Builder builder) {
        super(builder.mensaje);
        this.codigoError = builder.codigoError;
        this.numeroOperacion = builder.numeroOperacion;
        this.clienteId = builder.clienteId;
        this.timestamp = builder.timestamp != null ? builder.timestamp : Instant.now();
        this.canal = builder.canal;
        this.severity = builder.severity != null ? builder.severity : Severity.ERROR;
    }

    public String getCodigoError() {
        return codigoError;
    }

    public String getNumeroOperacion() {
        return numeroOperacion;
    }

    public UUID getClienteId() {
        return clienteId;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public String getCanal() {
        return canal;
    }

    public Severity getSeverity() {
        return severity;
    }

    public String getDetallesCompletos() {
        StringBuilder sb = new StringBuilder();
        sb.append("PrestamoException{");
        sb.append("codigoError=").append(codigoError);
        sb.append(", mensaje=").append(getMessage());
        if (numeroOperacion != null) {
            sb.append(", numeroOperacion=").append(numeroOperacion);
        }
        if (clienteId != null) {
            sb.append(", clienteId=").append(clienteId);
        }
        sb.append(", timestamp=").append(timestamp);
        if (canal != null) {
            sb.append(", canal=").append(canal);
        }
        sb.append(", severity=").append(severity);
        sb.append("}");
        return sb.toString();
    }

    public boolean esReintentable() {
        return severity == Severity.WARN || severity == Severity.ERROR;
    }

    public boolean esCritico() {
        return severity == Severity.CRITICAL;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String mensaje;
        private String codigoError = "PRESTAMO_001";
        private String numeroOperacion;
        private UUID clienteId;
        private Instant timestamp;
        private String canal;
        private Severity severity = Severity.ERROR;

        public Builder mensaje(String mensaje) {
            this.mensaje = mensaje;
            return this;
        }

        public Builder codigoError(String codigoError) {
            this.codigoError = codigoError;
            return this;
        }

        public Builder numeroOperacion(String numeroOperacion) {
            this.numeroOperacion = numeroOperacion;
            return this;
        }

        public Builder clienteId(UUID clienteId) {
            this.clienteId = clienteId;
            return this;
        }

        public Builder timestamp(Instant timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder canal(String canal) {
            this.canal = canal;
            return this;
        }

        public Builder severity(Severity severity) {
            this.severity = severity;
            return this;
        }

        public PrestamoException build() {
            if (mensaje == null || mensaje.isBlank()) {
                throw new IllegalStateException("El mensaje de la excepción no puede ser nulo o vacío");
            }
            return new PrestamoException(this);
        }
    }
}