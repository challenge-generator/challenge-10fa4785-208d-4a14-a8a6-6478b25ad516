package com.banco.prestamos.infrastructure.config;

import com.banco.prestamos.domain.model.SolicitudPrestamo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * Configuración para manejar la idempotencia de las solicitudes de préstamo.
 * Garantiza que operaciones con el mismo número de operación y canal no se procesen duplicadamente.
 */
@Configuration
public class IdempotencyConfig {

    private static final Logger logger = LoggerFactory.getLogger(IdempotencyConfig.class);
    private static final long TTL_MILLIS = 3600000;

    private final Map<IdempotencyKey, IdempotencyEntry> idempotencyStore = new ConcurrentHashMap<>();

    @Bean
    public IdempotencyService idempotencyService() {
        return new IdempotencyServiceImpl();
    }

    public record IdempotencyKey(String numeroOperacion, String canal) {
        public static IdempotencyKey from(String numeroOperacion, String canal) {
            return new IdempotencyKey(numeroOperacion, canal);
        }
    }

    public record IdempotencyEntry(
            String numeroOperacion,
            String canal,
            SolicitudPrestamo solicitud,
            Instant timestamp,
            boolean procesado
    ) {
        public boolean isExpired() {
            return Instant.now().toEpochMilli() - timestamp.toEpochMilli() > TTL_MILLIS;
        }
    }

    public interface IdempotencyService {
        boolean isDuplicate(String numeroOperacion, String canal);
        void markAsProcessed(String numeroOperacion, String canal, SolicitudPrestamo solicitud);
        IdempotencyEntry getEntry(String numeroOperacion, String canal);
        void cleanupExpired();
    }

    public class IdempotencyServiceImpl implements IdempotencyService {

        @Override
        public boolean isDuplicate(String numeroOperacion, String canal) {
            IdempotencyKey key = IdempotencyKey.from(numeroOperacion, canal);
            IdempotencyEntry entry = idempotencyStore.get(key);
            
            if (entry == null) {
                logger.debug("Nueva solicitud con número de operación: {} y canal: {}", numeroOperacion, canal);
                return false;
            }
            
            if (entry.isExpired()) {
                logger.info("Entrada expirada para número de operación: {} y canal: {}. Eliminando.", numeroOperacion, canal);
                idempotencyStore.remove(key);
                return false;
            }
            
            logger.info("Solicitud duplicada detectada para número de operación: {} y canal: {}", numeroOperacion, canal);
            return entry.procesado();
        }

        @Override
        public void markAsProcessed(String numeroOperacion, String canal, SolicitudPrestamo solicitud) {
            IdempotencyKey key = IdempotencyKey.from(numeroOperacion, canal);
            IdempotencyEntry entry = new IdempotencyEntry(
                    numeroOperacion,
                    canal,
                    solicitud,
                    Instant.now(),
                    true
            );
            idempotencyStore.put(key, entry);
            logger.info("Solicitud marcada como procesada: {} en canal: {}", numeroOperacion, canal);
        }

        @Override
        public IdempotencyEntry getEntry(String numeroOperacion, String canal) {
            IdempotencyKey key = IdempotencyKey.from(numeroOperacion, canal);
            IdempotencyEntry entry = idempotencyStore.get(key);
            
            if (entry != null && entry.isExpired()) {
                idempotencyStore.remove(key);
                return null;
            }
            
            return entry;
        }

        @Override
        public void cleanupExpired() {
            long removedCount = idempotencyStore.entrySet().removeIf(entry -> entry.getValue().isExpired());
            if (removedCount > 0) {
                logger.info("Limpiadas {} entradas de idempotencia expiradas", removedCount);
            }
        }
    }
}