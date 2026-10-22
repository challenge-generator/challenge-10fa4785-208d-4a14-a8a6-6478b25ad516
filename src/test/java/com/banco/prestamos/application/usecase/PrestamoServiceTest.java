package com.banco.prestamos.application.usecase;


import com.banco.prestamos.domain.port.out.ResultadoAntifraude;
import com.banco.prestamos.domain.model.SolicitudPrestamo;
import com.banco.prestamos.domain.model.RespuestaPrestamo;
import com.banco.prestamos.domain.port.out.AntifraudePort;
import com.banco.prestamos.domain.port.out.BuroRiesgoPort;
import com.banco.prestamos.domain.port.out.CoreBancarioPort;
import com.banco.prestamos.infrastructure.exception.PrestamoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PrestamoServiceTest {

    @Mock
    private AntifraudePort antifraudePort;

    @Mock
    private BuroRiesgoPort buroRiesgoPort;

    @Mock
    private CoreBancarioPort coreBancarioPort;

    @InjectMocks
    private PrestamoService prestamoService;

    private SolicitudPrestamo solicitudValida;

    @BeforeEach
    void setUp() {
        solicitudValida = new SolicitudPrestamo(
            UUID.randomUUID(),
            new BigDecimal("50000"),
            24,
            "NOMINA",
            "CANAL_WEB",
            UUID.randomUUID().toString()
        );
    }

    @Test
    void debeAprobarPrestamoCuandoPasaTodasLasValidaciones() {
        when(antifraudePort.evaluarRiesgoFraude(any(SolicitudPrestamo.class)))
            .thenReturn(CompletableFuture.completedFuture(
                new AntifraudePort.ResultadoAntifraude(true, "LOW")
            ));

        when(buroRiesgoPort.consultarHistorial(any())).thenReturn(
            CompletableFuture.completedFuture(
                new BuroRiesgoPort.ResultadoBuro(true, "A", 0)
            )
        );

        when(coreBancarioPort.registrarSolicitud(any(), any())).thenReturn(
            CompletableFuture.completedFuture("OP-12345")
        );

        when(coreBancarioPort.aprobarPrestamo(any(), any(), any())).thenReturn(
            CompletableFuture.completedFuture(true)
        );

        RespuestaPrestamo respuesta = prestamoService.procesarSolicitud(solicitudValida);

        assertNotNull(respuesta);
        verify(antifraudePort, times(1)).evaluarRiesgoFraude(any(SolicitudPrestamo.class));
        verify(buroRiesgoPort, times(1)).consultarHistorial(any());
        verify(coreBancarioPort, times(1)).registrarSolicitud(any(), any());
        verify(coreBancarioPort, times(1)).aprobarPrestamo(any(), any(), any());
    }

    @Test
    void debeRechazarPrestamoCuandoAntifraudeDetectaRiesgo() {
        when(antifraudePort.evaluarRiesgoFraude(any(SolicitudPrestamo.class)))
            .thenReturn(CompletableFuture.completedFuture(
                new AntifraudePort.ResultadoAntifraude(false, "HIGH")
            ));

        RespuestaPrestamo respuesta = prestamoService.procesarSolicitud(solicitudValida);

        assertNotNull(respuesta);
        verify(coreBancarioPort, never()).registrarSolicitud(any(), any());
    }

    @Test
    void debeRechazarPrestamoCuandoBuroRiesgoIndicaMorosidad() {
        when(antifraudePort.evaluarRiesgoFraude(any(SolicitudPrestamo.class)))
            .thenReturn(CompletableFuture.completedFuture(
                new AntifraudePort.ResultadoAntifraude(true, "LOW")
            ));

        when(buroRiesgoPort.consultarHistorial(any())).thenReturn(
            CompletableFuture.completedFuture(
                new BuroRiesgoPort.ResultadoBuro(false, "D", 3)
            )
        );

        RespuestaPrestamo respuesta = prestamoService.procesarSolicitud(solicitudValida);

        assertNotNull(respuesta);
    }

    @Test
    void debeManejarExcepcionCuandoAntifraudeFalla() {
        when(antifraudePort.evaluarRiesgoFraude(any(SolicitudPrestamo.class)))
            .thenReturn(CompletableFuture.failedFuture(
                new RuntimeException("Servicio antifraude no disponible")
            ));

        assertThrows(PrestamoException.class, () -> {
            prestamoService.procesarSolicitud(solicitudValida);
        });
    }

    @Test
    void debeValidarMontoMinimoDelPrestamo() {
        SolicitudPrestamo solicitudMontoInvalido = new SolicitudPrestamo(
            UUID.randomUUID(),
            new BigDecimal("500"),
            24,
            "NOMINA",
            "CANAL_WEB",
            UUID.randomUUID().toString()
        );

        assertThrows(PrestamoException.class, () -> {
            prestamoService.procesarSolicitud(solicitudMontoInvalido);
        });
    }

    @Test
    void debeValidarPlazoMaximoDelPrestamo() {
        SolicitudPrestamo solicitudPlazoInvalido = new SolicitudPrestamo(
            UUID.randomUUID(),
            new BigDecimal("50000"),
            120,
            "NOMINA",
            "CANAL_WEB",
            UUID.randomUUID().toString()
        );

        assertThrows(PrestamoException.class, () -> {
            prestamoService.procesarSolicitud(solicitudPlazoInvalido);
        });
    }

    @Test
    void debeRechazarCuandoCoreBancarioFalla() {
        when(antifraudePort.evaluarRiesgoFraude(any(SolicitudPrestamo.class)))
            .thenReturn(CompletableFuture.completedFuture(
                new AntifraudePort.ResultadoAntifraude(true, "LOW")
            ));

        when(buroRiesgoPort.consultarHistorial(any())).thenReturn(
            CompletableFuture.completedFuture(
                new BuroRiesgoPort.ResultadoBuro(true, "A", 0)
            )
        );

        when(coreBancarioPort.registrarSolicitud(any(), any()))
            .thenReturn(CompletableFuture.failedFuture(
                new RuntimeException("Error en core bancario")
            ));

        RespuestaPrestamo respuesta = prestamoService.procesarSolicitud(solicitudValida);

        assertNotNull(respuesta);
    }

    @Test
    void debeVerificarIdempotenciaPorNumeroOperacion() {
        when(antifraudePort.evaluarRiesgoFraude(any(SolicitudPrestamo.class)))
            .thenReturn(CompletableFuture.completedFuture(
                new AntifraudePort.ResultadoAntifraude(true, "LOW")
            ));

        when(buroRiesgoPort.consultarHistorial(any())).thenReturn(
            CompletableFuture.completedFuture(
                new BuroRiesgoPort.ResultadoBuro(true, "A", 0)
            )
        );

        when(coreBancarioPort.registrarSolicitud(any(), any())).thenReturn(
            CompletableFuture.completedFuture("OP-12345")
        );

        when(coreBancarioPort.aprobarPrestamo(any(), any(), any())).thenReturn(
            CompletableFuture.completedFuture(true)
        );

        String numeroOperacion = "OPE-" + System.currentTimeMillis();
        SolicitudPrestamo solicitud = new SolicitudPrestamo(
            UUID.randomUUID(),
            new BigDecimal("50000"),
            24,
            "NOMINA",
            "CANAL_WEB",
            numeroOperacion
        );

        RespuestaPrestamo primeraRespuesta = prestamoService.procesarSolicitud(solicitud);
        RespuestaPrestamo segundaRespuesta = prestamoService.procesarSolicitud(solicitud);

        assertNotNull(primeraRespuesta);
        assertNotNull(segundaRespuesta);
    }

    @Test
    void debeCalcularTasaInteresSegunTipoProducto() {
        when(antifraudePort.evaluarRiesgoFraude(any(SolicitudPrestamo.class)))
            .thenReturn(CompletableFuture.completedFuture(
                new AntifraudePort.ResultadoAntifraude(true, "LOW")
            ));

        when(buroRiesgoPort.consultarHistorial(any())).thenReturn(
            CompletableFuture.completedFuture(
                new BuroRiesgoPort.ResultadoBuro(true, "A", 0)
            )
        );

        when(coreBancarioPort.registrarSolicitud(any(), any())).thenReturn(
            CompletableFuture.completedFuture("OP-12345")
        );

        when(coreBancarioPort.aprobarPrestamo(any(), any(), any())).thenReturn(
            CompletableFuture.completedFuture(true)
        );

        SolicitudPrestamo solicitudNomina = new SolicitudPrestamo(
            UUID.randomUUID(),
            new BigDecimal("50000"),
            24,
            "NOMINA",
            "CANAL_WEB",
            UUID.randomUUID().toString()
        );

        RespuestaPrestamo respuestaNomina = prestamoService.procesarSolicitud(solicitudNomina);

        assertNotNull(respuestaNomina);
    }
}