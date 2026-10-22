package com.banco.prestamos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import com.banco.prestamos.domain.port.in.PrestamoUseCase;
import com.banco.prestamos.infrastructure.config.Resilience4jConfig;
import com.banco.prestamos.infrastructure.config.IdempotencyConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

@SpringBootApplication
@EnableAsync
@EnableTransactionManagement
public class PrestamosApplication {
    private static final Logger logger = LoggerFactory.getLogger(PrestamosApplication.class);
    private final Environment environment;
    private final PrestamoUseCase prestamoUseCase;

    public PrestamosApplication(Environment environment, PrestamoUseCase prestamoUseCase) {
        this.environment = environment;
        this.prestamoUseCase = prestamoUseCase;
        validateProfiles();
    }

    public static void main(String[] args) {
        SpringApplication.run(PrestamosApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        logger.info("Aplicación iniciada con perfiles activos: {}",
            Arrays.toString(environment.getActiveProfiles()));
        logger.info("Resilience4j configurado con políticas de reintento y circuit breaker");
        logger.info("Idempotencia garantizada por número de operación y canal");
    }

    private void validateProfiles() {
        String[] activeProfiles = environment.getActiveProfiles();
        if (activeProfiles.length == 0) {
            logger.warn("No hay perfiles activos definidos. Se recomienda usar 'dev' o 'prod'");
        }
        if (Arrays.asList(activeProfiles).contains("test") &&
            Arrays.asList(activeProfiles).contains("prod")) {
            throw new IllegalStateException("No se puede ejecutar con perfiles 'test' y 'prod' simultáneamente");
        }
    }

    /**
     * Inicializa los componentes de configuración requeridos para el funcionamiento del servicio.
     * Este método asegura que las políticas de Resilience4j y la configuración de idempotencia
     * estén disponibles desde el inicio de la aplicación.
     */
    public void initializeConfigurations() {
        Resilience4jConfig.initialize();
        IdempotencyConfig.initialize();
        logger.info("Configuraciones inicializadas correctamente");
    }
}