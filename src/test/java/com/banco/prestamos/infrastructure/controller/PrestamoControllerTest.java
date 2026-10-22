package com.banco.prestamos.infrastructure.controller;


import com.banco.prestamos.infrastructure.exception.IdempotencyException;
import com.banco.prestamos.domain.model.SolicitudPrestamo;
import com.banco.prestamos.domain.model.RespuestaPrestamo;
import com.banco.prestamos.domain.port.in.PrestamoUseCase;
import com.banco.prestamos.infrastructure.exception.GlobalExceptionHandler;
import com.banco.prestamos.infrastructure.exception.PrestamoException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PrestamoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PrestamoUseCase prestamoUseCase;

    @InjectMocks
    private PrestamoController prestamoController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(prestamoController)
            .setControllerAdvice(new GlobalExceptionHandler())
            .build();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void debeCrearPrestamoCuandoSolicitudEsValida() throws Exception {
        SolicitudPrestamo solicitud = new SolicitudPrestamo(
            UUID.randomUUID(),
            new BigDecimal("50000"),
            24,
            "NOMINA",
            "CANAL_WEB",
            UUID.randomUUID().toString()
        );

        RespuestaPrestamo respuestaEsperada = RespuestaPrestamo.aprobada(
            "OP-12345",
            solicitud.clienteId(),
            new BigDecimal("0.15"),
            new BigDecimal("2500"),
            24
        );

        when(prestamoUseCase.procesarSolicitud(any(SolicitudPrestamo.class)))
            .thenReturn(respuestaEsperada);

        mockMvc.perform(post("/api/v1/prestamos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(solicitud)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.estado").exists());

        verify(prestamoUseCase, times(1)).procesarSolicitud(any(SolicitudPrestamo.class));
    }

    @Test
    void debeRetornarBadRequestCuandoMontoEsInvalido() throws Exception {
        SolicitudPrestamo solicitudInvalida = new SolicitudPrestamo(
            UUID.randomUUID(),
            new BigDecimal("100"),
            24,
            "NOMINA",
            "CANAL_WEB",
            UUID.randomUUID().toString()
        );

        when(prestamoUseCase.procesarSolicitud(any(SolicitudPrestamo.class)))
            .thenThrow(new PrestamoException("Monto mínimo no válido"));

        mockMvc.perform(post("/api/v1/prestamos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(solicitudInvalida)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void debeRetornarBadRequestCuandoPlazoEsInvalido() throws Exception {
        SolicitudPrestamo solicitudInvalida = new SolicitudPrestamo(
            UUID.randomUUID(),
            new BigDecimal("50000"),
            200,
            "NOMINA",
            "CANAL_WEB",
            UUID.randomUUID().toString()
        );

        when(prestamoUseCase.procesarSolicitud(any(SolicitudPrestamo.class)))
            .thenThrow(new PrestamoException("Plazo máximo excedido"));

        mockMvc.perform(post("/api/v1/prestamos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(solicitudInvalida)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void debeRetornarConflictCuandoSolicitudEsDuplicada() throws Exception {
        SolicitudPrestamo solicitud = new SolicitudPrestamo(
            UUID.randomUUID(),
            new BigDecimal("50000"),
            24,
            "NOMINA",
            "CANAL_WEB",
            "OPE-12345"
        );

        when(prestamoUseCase.procesarSolicitud(any(SolicitudPrestamo.class)))
            .thenThrow(new com.banco.prestamos.infrastructure.exception.IdempotencyException(
                "Solicitud duplicada"
            ));

        mockMvc.perform(post("/api/v1/prestamos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(solicitud)))
            .andExpect(status().isConflict());
    }

    @Test
    void debeRetornar500CuandoErrorInternoDelServicio() throws Exception {
        SolicitudPrestamo solicitud = new SolicitudPrestamo(
            UUID.randomUUID(),
            new BigDecimal("50000"),
            24,
            "NOMINA",
            "CANAL_WEB",
            UUID.randomUUID().toString()
        );

        when(prestamoUseCase.procesarSolicitud(any(SolicitudPrestamo.class)))
            .thenThrow(new RuntimeException("Error interno"));

        mockMvc.perform(post("/api/v1/prestamos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(solicitud)))
            .andExpect(status().isInternalServerError());
    }

    @Test
    void debeValidarQueClienteIdNoSeaNulo() throws Exception {
        String jsonInvalido = "{\"monto\":50000,\"plazo\":24,\"tipoProducto\":\"NOMINA\",\"canal\":\"CANAL_WEB\",\"numeroOperacion\":\"OPE-123\"}";

        mockMvc.perform(post("/api/v1/prestamos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonInvalido))
            .andExpect(status().isBadRequest());
    }

    @Test
    void debeValidarQueMontoNoSeaNulo() throws Exception {
        String jsonInvalido = "{\"clienteId\":\"" + UUID.randomUUID() + "\",\"plazo\":24,\"tipoProducto\":\"NOMINA\",\"canal\":\"CANAL_WEB\",\"numeroOperacion\":\"OPE-123\"}";

        mockMvc.perform(post("/api/v1/prestamos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonInvalido))
            .andExpect(status().isBadRequest());
    }

    @Test
    void debeRetornarNotFoundCuandoConsultaPrestamoInexistente() throws Exception {
        UUID clienteId = UUID.randomUUID();

        when(prestamoUseCase.consultarPrestamo(clienteId))
            .thenReturn(java.util.Optional.empty());

        mockMvc.perform(get("/api/v1/prestamos/{clienteId}", clienteId))
            .andExpect(status().isNotFound());
    }

    @Test
    void debeRetornarOkCuandoConsultaPrestamoExistente() throws Exception {
        UUID clienteId = UUID.randomUUID();

        RespuestaPrestamo respuesta = RespuestaPrestamo.aprobada(
            "OP-12345",
            clienteId,
            new BigDecimal("0.15"),
            new BigDecimal("2500"),
            24
        );

        when(prestamoUseCase.consultarPrestamo(clienteId))
            .thenReturn(java.util.Optional.of(respuesta));

        mockMvc.perform(get("/api/v1/prestamos/{clienteId}", clienteId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.estado").exists());
    }

    @Test
    void debeAceptarContentTypeJson() throws Exception {
        SolicitudPrestamo solicitud = new SolicitudPrestamo(
            UUID.randomUUID(),
            new BigDecimal("50000"),
            24,
            "NOMINA",
            "CANAL_WEB",
            UUID.randomUUID().toString()
        );

        RespuestaPrestamo respuesta = RespuestaPrestamo.aprobada(
            "OP-12345",
            solicitud.clienteId(),
            new BigDecimal("0.15"),
            new BigDecimal("2500"),
            24
        );

        when(prestamoUseCase.procesarSolicitud(any(SolicitudPrestamo.class)))
            .thenReturn(respuesta);

        mockMvc.perform(post("/api/v1/prestamos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(solicitud)))
            .andExpect(status().isOk());
    }

    @Test
    void debeRechazarContentTypeNoSoportado() throws Exception {
        SolicitudPrestamo solicitud = new SolicitudPrestamo(
            UUID.randomUUID(),
            new BigDecimal("50000"),
            24,
            "NOMINA",
            "CANAL_WEB",
            UUID.randomUUID().toString()
        );

        mockMvc.perform(post("/api/v1/prestamos")
                .contentType(MediaType.APPLICATION_XML)
                .content("<prestamo/>"))
            .andExpect(status().isUnsupportedMediaType());
    }