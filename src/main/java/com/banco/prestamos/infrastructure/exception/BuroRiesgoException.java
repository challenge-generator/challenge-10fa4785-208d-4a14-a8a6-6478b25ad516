package com.banco.prestamos.infrastructure.exception;

import java.time.Instant;
import java.util.UUID;

public class BuroRiesgoException extends RuntimeException {

    private final String codigoRespuesta;
    private final UUID clienteId;
    private final Instant timestamp;
    private final String numeroOperacion;
    private final TipoError tipoError;
    private final boolean reintentoAutomatico;
    private final int intentosRealizados;
    private final int maximoReintentos;

    public enum TipoError {
        CONEXION("No se pudo establecer conexión con el buró de riesgos"),
        TIMEOUT("La conexión con el buró de riesgos excedió el tiempo límite"),
        AUTENTICACION("Falló la autenticación con el buró de riesgos"),
        DATOS_INVALIDOS("Los datos enviados al buró de riesgos son inválidos"),
        SERVICIO_NO_DISPONIBLE("El servicio del buró de riesgos no está disponible"),
        RESPUESTA_INVALIDA("La respuesta del buró de riesgos no tiene el formato esperado"),
        CUPO_EXCEDIDO("Se excedió el número de consultas permitidas al buró"),
        ERROR_DESCONOCIDO("Ocurrió un error desconocido al consultar el buró"),
        CONSULTA_DUPLICADA("Ya existe una consulta reciente para este cliente");

        private final String descripcion;

        TipoError(String descripcion) {
            this.descripcion = descripcion;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public boolean esReintentable() {
            return this == CONEXION || this == TIMEOUT || this == SERVICIO_NO_DISPONIBLE;
        }
    }

    public BuroRiesgoException(String mensaje) {
        super(mensaje);
        this.codigoRespuesta = null;
        this.clienteId = null;
        this.timestamp = Instant.now();
        this.numeroOperacion = null;
        this.tipoError = TipoError.ERROR_DESCONOCIDO;
        this.reintentoAutomatico = false;
        this.intentosRealizados = 0;
        this.maximoReintentos = 0;
    }

    public BuroRiesgoException(String mensaje, TipoError tipoError) {
        super(mensaje);
        this.codigoRespuesta = null;
        this.clienteId = null;
        this.timestamp = Instant.now();
        this.numeroOperacion = null;
        this.tipoError = tipoError;
        this.reintentoAutomatico = tipoError.esReintentable();
        this.intentosRealizados = 0;
        this.maximoReintentos = tipoError.esReintentable() ? 3 : 0;
    }

    public BuroRiesgoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
        this.codigoRespuesta = null;
        this.clienteId = null;
        this.timestamp = Instant.now();
        this.numeroOperacion = null;
        this.tipoError = TipoError.ERROR_DESCONOCIDO;
        this.reintentoAutomatico = false;
        this.intentosRealizados = 0;
        this.maximoReintentos = 0;
    }

    public BuroRiesgoException(String mensaje, TipoError tipoError, Throwable causa) {
        super(mensaje, causa);
        this.codigoRespuesta = null;
        this.clienteId = null;
        this.timestamp = Instant.now();
        this.numeroOperacion = null;
        this.tipoError = tipoError;
        this.reintentoAutomatico = tipoError.esReintentable();
        this.intentosRealizados = 0;
        this.maximoReintentos = tipoError.esReintentable() ? 3 : 0;
    }

    public BuroRiesgoException(String mensaje, String codigoRespuesta, UUID clienteId, TipoError tipoError) {
        super(mensaje);
        this.codigoRespuesta = codigoRespuesta;
        this.clienteId = clienteId;
        this.timestamp = Instant.now();
        this.numeroOperacion = null;
        this.tipoError = tipoError;
        this.reintentoAutomatico = tipoError.esReintentable();
        this.intentosRealizados = 0;
        this.maximoReintentos = tipoError.esReintentable() ? 3 : 0;
    }

    private BuroRiesgoException(Builder builder) {
        super(builder.mensaje);
        this.codigoRespuesta = builder.codigoRespuesta;
        this.clienteId = builder.clienteId;
        this.timestamp = builder.timestamp != null ? builder.timestamp : Instant.now();
        this.numeroOperacion = builder.numeroOperacion;
        this.tipoError = builder.tipoError != null ? builder.tipoError : TipoError.ERROR_DESCONOCIDO;
        this.reintentoAutomatico = builder.reintentoAutomatico;
        this.intentosRealizados = builder.intentosRealizados;
        this.maximoReintentos = builder.maximoReintentos;
    }

    public String getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public UUID getClienteId() {
        return clienteId;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public String getNumeroOperacion() {
        return numeroOperacion;
    }

    public TipoError getTipoError() {
        return tipoError;
    }

    public boolean isReintentoAutomatico() {
        return reintentoAutomatico;
    }

    public int getIntentosRealizados() {
        return intentosRealizados;
    }

    public int getMaximoReintentos() {
        return maximoReintentos;
    }

    public boolean puedeReintentar() {
        return reintentoAutomatico && intentosRealizados < maximoReintentos;
    }

    public String getDetallesCompletos() {
        StringBuilder sb = new StringBuilder();
        sb.append("BuroRiesgoException{");
        sb.append("tipoError=").append(tipoError.name());
        sb.append(", mensaje=").append(getMessage());
        if (codigoRespuesta != null) {
            sb.append(", codigoRespuesta=").append(codigoRespuesta);
        }
        if (clienteId != null) {
            sb.append(", clienteId=").append(clienteId);
        }
        if (numeroOperacion != null) {
            sb.append(", numeroOperacion=").append(numeroOperacion);
        }
        sb.append(", timestamp=").append(timestamp);
        sb.append(", reintentoAutomatico=").append(reintentoAutomatico);
        sb.append(", intentosRealizados=").append(intentosRealizados);
        sb.append(", maximoReintentos=").append(maximoReintentos);
        sb.append("}");
        return sb.toString();
    }

    public BuroRiesgoException conIntentos(int intentos) {
        BuroRiesgoException e = new Builder()
            .mensaje(this.getMessage())
            .codigoRespuesta(this.codigoRespuesta)
            .clienteId(this.clienteId)
            .numeroOperacion(this.numeroOperacion)
            .tipoError(this.tipoError)
            .timestamp(this.timestamp)
            .reintentoAutomatico(this.reintentoAutomatico)
            .intentosRealizados(intentos)
            .maximoReintentos(this.maximoReintentos)
            .build();
        e.initCause(this.getCause());
        return e;
    }

    public static BuroRiesgoException conexion(String mensaje) {
        return new BuroRiesgoException(mensaje, TipoError.CONEXION);
    }

    public static BuroRiesgoException timeout(String mensaje) {
        return new BuroRiesgoException(mensaje, TipoError.TIMEOUT);
    }

    public static BuroRiesgoException servicioNoDisponible(String mensaje) {
        return new BuroRiesgoException(mensaje, TipoError.SERVICIO_NO_DISPONIBLE);
    }

    public static BuroRiesgoException autenticacion(String mensaje, Throwable causa) {
        return new BuroRiesgoException(mensaje, TipoError.AUTENTICACION, causa);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String mensaje;
        private String codigoRespuesta;
        private UUID clienteId;
        private Instant timestamp;
        private String numeroOperacion;
        private TipoError tipoError;
        private boolean reintentoAutomatico = false;
        private int intentosRealizados = 0;
        private int maximoReintentos = 0;

        public Builder mensaje(String mensaje) {
            this.mensaje = mensaje;
            return this;
        }

        public Builder codigoRespuesta(String codigoRespuesta) {
            this.codigoRespuesta = codigoRespuesta;
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

        public Builder numeroOperacion(String numeroOperacion) {
            this.numeroOperacion = numeroOperacion;
            return this;
        }

        public Builder tipoError(TipoError tipoError) {
            this.tipoError = tipoError;
            if (tipoError != null && tipoError.esReintentable()) {
                this.reintentoAutomatico = true;
                this.maximoReintentos = 3;
            }
            return this;
        }

        public Builder reintentoAutomatico(boolean reintentoAutomatico) {
            this.reintentoAutomatico = reintentoAutomatico;
            return this;
        }

        public Builder intentosRealizados(int intentosRealizados) {
            this.intentosRealizados = intentosRealizados;
            return this;
        }

        public Builder maximoReintentos(int maximoReintentos) {
            this.maximoReintentos = maximoReintentos;
            return this;
        }

        public BuroRiesgoException build() {
            if (mensaje == null || mensaje.isBlank()) {
                throw new IllegalStateException("El mensaje de la excepción no puede ser nulo o vacío");
            }
            return new BuroRiesgoException(this);
        }
    }
}