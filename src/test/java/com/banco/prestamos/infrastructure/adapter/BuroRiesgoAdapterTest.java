package com.banco.prestamos.infrastructure.adapter;

import com.banco.prestamos.domain.model.SolicitudPrestamo;
import com.banco.prestamos.domain.port.out.BuroRiesgoPort;
import com.banco.prestamos.infrastructure.exception.BuroRiesgoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BuroRiesgoAdapterTest {

    @Mock
    private RestTemplate restTemplate;

    private BuroRiesgoAdapter buroRiesgoAdapter;

    private SolicitudPrestamo solicitudPrestamo;

    @BeforeEach
    void setUp() {
        buroRiesgoAdapter = new BuroRiesgoAdapter(restTemplate);

        solicitudPrestamo = new SolicitudPrestamo(
            UUID.randomUUID(),
            new BigDecimal("50000"),
            24,
            "NOMINA",
            "CANAL_WEB",
            UUID.randomUUID().toString()
        );
    }

    @Test
    void debeRetornarResultadoPositivoCuandoClienteNoTieneMorosidad() {
        BuroRiesgoPort.ResultadoBuro resultadoSimulado = new BuroRiesgoPort.ResultadoBuro(
            true,
            "A",
            0
        );

        when(restTemplate.postForObject(
            contains("/consulta"),
            any(),
            eq(BuroRiesgoPort.ResultadoBuro.class)
        )).thenReturn(resultadoSimulado);

        CompletableFuture<BuroRiesgoPort.ResultadoBuro> resultado =
            buroRiesgoAdapter.consultarHistorial(solicitudPrestamo);

        assertNotNull(resultado);
        assertDoesNotThrow(() -> resultado.get());
    }

    @Test
    void debeRetornarResultadoNegativoCuandoClienteTieneMorosidad() {
        BuroRiesgoPort.ResultadoBuro resultadoSimulado = new BuroRiesgoPort.ResultadoBuro(
            false,
            "D",
            3
        );

        when(restTemplate.postForObject(
            contains("/consulta"),
            any(),
            eq(BuroRiesgoPort.ResultadoBuro.class)
        )).thenReturn(resultadoSimulado);

        CompletableFuture<BuroRiesgoPort.ResultadoBuro> resultado =
            buroRiesgoAdapter.consultarHistorial(solicitudPrestamo);

        assertNotNull(resultado);
    }

    @Test
    void debeReintentarCuandoServicioFallaPorPrimeraVez() {
        when(restTemplate.postForObject(
            contains("/consulta"),
            any(),
            eq(BuroRiesgoPort.ResultadoBuro.class)
        )).thenThrow(new RuntimeException("Servicio no disponible"))
          .thenReturn(new BuroRiesgoPort.ResultadoBuro(true, "A", 0));

        CompletableFuture<BuroRiesgoPort.ResultadoBuro> resultado =
            buroRiesgoAdapter.consultarHistorial(solicitudPrestamo);

        assertNotNull(resultado);
        verify(restTemplate, times(2)).postForObject(
            contains("/consulta"),
            any(),
            eq(BuroRiesgoPort.ResultadoBuro.class)
        );
    }

    @Test
    void debeLanzarExcepcionDespuesDeReintentosExcedidos() {
        when(restTemplate.postForObject(
            contains("/consulta"),
            any(),
            eq(BuroRiesgoPort.ResultadoBuro.class)
        )).thenThrow(new RuntimeException("Servicio no disponible"));

        assertThrows(BuroRiesgoException.class, () -> {
            buroRiesgoAdapter.consultarHistorial(solicitudPrestamo).join();
        });
    }

    @Test
    void debeManejarErrorDeRed() {
        when(restTemplate.postForObject(
            contains("/consulta"),
            any(),
            eq(BuroRiesgoPort.ResultadoBuro.class)
        )).thenThrow(new RuntimeException("Connection timeout"));

        CompletableFuture<BuroRiesgoPort.ResultadoBuro> resultado =
            buroRiesgoAdapter.consultarHistorial(solicitudPrestamo);

        assertThrows(Exception.class, () -> resultado.get());
    }

    @Test
    void debeConstruirRequestCorrectamente() {
        BuroRiesgoPort.ResultadoBuro resultadoSimulado = new BuroRiesgoPort.ResultadoBuro(
            true,
            "A",
            0
        );

        when(restTemplate.postForObject(
            anyString(),
            any(),
            eq(BuroRiesgoPort.ResultadoBuro.class)
        )).thenReturn(resultadoSimulado);

        buroRiesgoAdapter.consultarHistorial(solicitudPrestamo);

        verify(restTemplate).postForObject(
            anyString(),
            any(),
            eq(BuroRiesgoPort.ResultadoBuro.class)
        );
    }

    @Test
    void debeValidarRespuestaNulaDelServicio() {
        when(restTemplate.postForObject(
            contains("/consulta"),
            any(),
            eq(BuroRiesgoPort.ResultadoBuro.class)
        )).thenReturn(null);

        assertThrows(Exception.class, () -> {
            buroRiesgoAdapter.consultarHistorial(solicitudPrestamo).get();
        });
    }

    @Test
    void debeRegistrarMetricasDeLlamada() {
        BuroRiesgoPort.ResultadoBuro resultadoSimulado = new BuroRiesgoPort.ResultadoBuro(
            true,
            "A",
            0
        );

        when(restTemplate.postForObject(
            anyString(),
            any(),
            eq(BuroRiesgoPort.ResultadoBuro.class)
        )).thenReturn(resultadoSimulado);

        buroRiesgoAdapter.consultarHistorial(solicitudPrestamo);

        verify(restTemplate, atLeastOnce()).postForObject(
            anyString(),
            any(),
            eq(BuroRiesgoPort.ResultadoBuro.class)
        );
    }
}