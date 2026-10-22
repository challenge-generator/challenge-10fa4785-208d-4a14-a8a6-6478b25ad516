package com.banco.prestamos.infrastructure.controller;



import com.banco.prestamos.domain.model.EstadoRespuesta;
import com.banco.prestamos.domain.model.EstadoSolicitud;
import com.banco.prestamos.domain.model.RespuestaPrestamo;
import com.banco.prestamos.domain.model.SolicitudPrestamo;
import com.banco.prestamos.domain.port.in.PrestamoUseCase;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/prestamos")
@Tag(name = "Préstamos", description = "API para gestión de solicitudes de préstamo")
public class PrestamoController {

    private static final Logger logger = LoggerFactory.getLogger(PrestamoController.class);
    private static final String IDEMPOTENCY_KEY_HEADER = "X-Idempotency-Key";

    private final PrestamoUseCase prestamoUseCase;

    public PrestamoController(PrestamoUseCase prestamoUseCase) {
        this.prestamoUseCase = prestamoUseCase;
    }

    @PostMapping
    @Operation(summary = "Crear solicitud de préstamo", 
               description = "Procesa una nueva solicitud de préstamo con garantía de idempotencia")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Solicitud procesada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
        @ApiResponse(responseCode = "409", description = "Solicitud duplicada (idempotencia)"),
        @ApiResponse(responseCode = "503", description = "Servicio no disponible")
    })
    @CircuitBreaker(name = "prestamoService", fallbackMethod = "procesarPrestamoFallback")
    public ResponseEntity<RespuestaPrestamo> procesarPrestamo(
            @RequestHeader(value = IDEMPOTENCY_KEY_HEADER, required = false) String idempotencyKey,
            @RequestBody SolicitudPrestamoRequest request) {

        logger.info("Recibida solicitud de préstamo para cliente: {}, monto: {}, idempotencyKey: {}",
                request.clienteId(), request.monto(), idempotencyKey);

        if (idempotencyKey == null || idempotencyKey.isBlank()) {
            logger.warn("Solicitud sin clave de idempotencia");
        }

        SolicitudPrestamo solicitud = new SolicitudPrestamo(
            UUID.fromString(request.clienteId()),
            request.monto(),
            request.plazoMeses(),
            request.tipoPrestamo(),
            request.destino(),
            SolicitudPrestamo.EstadoSolicitud.EN_PROCESO,
            LocalDate.now(),
            null
        );

        RespuestaPrestamo respuesta = prestamoUseCase.procesarSolicitud(solicitud, idempotencyKey);

        HttpHeaders headers = new HttpHeaders();
        if (respuesta.numeroOperacion() != null) {
            headers.set("X-Numero-Operacion", respuesta.numeroOperacion());
        }

        HttpStatus status = respuesta.estado() == RespuestaPrestamo.EstadoRespuesta.APROBADA 
            ? HttpStatus.CREATED 
            : HttpStatus.OK;

        return ResponseEntity.status(status).headers(headers).body(respuesta);
    }

    @GetMapping("/{numeroOperacion}")
    @Operation(summary = "Consultar estado de préstamo", 
               description = "Retorna el estado de una solicitud de préstamo por número de operación")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación encontrada"),
        @ApiResponse(responseCode = "404", description = "Operación no encontrada")
    })
    public ResponseEntity<RespuestaPrestamo> consultarPrestamo(
            @Parameter(description = "Número de operación del préstamo") 
            @PathVariable String numeroOperacion) {

        logger.info("Consultando préstamo con número de operación: {}", numeroOperacion);
        RespuestaPrestamo respuesta = prestamoUseCase.consultarEstado(numeroOperacion);
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Verifica el estado del servicio")
    public ResponseEntity<Map<String, String>> healthCheck() {
        return ResponseEntity.ok(Map.of("status", "UP", "service", "prestamos"));
    }

    private ResponseEntity<RespuestaPrestamo> procesarPrestamoFallback(
            SolicitudPrestamoRequest request, Throwable t) {
        logger.error("Fallback activado para procesarPrestamo. Error: {}", t.getMessage());
        RespuestaPrestamo errorResponse = RespuestaPrestamo.error(
            "FALLBACK-" + System.currentTimeMillis(),
            UUID.fromString(request.clienteId())
        );
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(errorResponse);
    }

    public record SolicitudPrestamoRequest(
        String clienteId,
        BigDecimal monto,
        Integer plazoMeses,
        String tipoPrestamo,
        String destino
    ) {}
}