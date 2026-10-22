# AGENTS.md

Instrucciones para el agente de IA que abra este repositorio (Claude Code, Cursor, Codex, Copilot, Gemini). Se cargan solas: no hay que pegar nada en ningun chat.

## Que es este repositorio

Es el codigo base de un reto de aprendizaje de Pragma: **Refactorización de servicio de préstamos**.

| | |
|---|---|
| Tema | aplicación de código limpio y eficiente |
| Nivel | advanced-l2 |
| Chapter | Backend |
| Especialidad | Java |
| Stack | Java / Spring Boot 3.5 |
| Patron arquitectonico | hexagonal/clean |
| Tiempo estimado | 1 semana |

## Receta del stack

Esqueleto obligatorio:

- `pom.xml en la raiz`
- `clase con @SpringBootApplication`
- `application.yml en src/main/resources`
- `capa de dominio con entidades y puertos`
- `capa de aplicacion con casos de uso`
- `capa de infraestructura con adaptadores y @RestController`

Trampas conocidas:

- TODA `<version>` del pom va con tres segmentos: la del parent (ej. `3.5.6`) y la de cada dependencia que la lleve (ej. Resilience4j `2.2.0`). `3.4` y `2.0` no existen como artefacto y el build muere resolviendo dependencias.
- Las dependencias que el parent POM gestiona van SIN `<version>`: `spring-boot-starter-web`, `-data-jpa`, `-validation`, `-test`, etc.
- Resilience4j publica un artefacto por linea de Spring Boot. Con Spring Boot 3 va `resilience4j-spring-boot3` con version de tres segmentos (ej. `2.2.0`, no `2.0`). `resilience4j-spring-boot2` es de Spring Boot 2 y rompe el arranque.
- Si usas anotaciones de validacion (`@NotNull`, `@Size`, `@Positive`) declara `spring-boot-starter-validation`: el starter web no las trae.
- El `spring-boot-maven-plugin` tiene que estar en `<build><plugins>` o no se empaqueta ejecutable.
- Spring Boot 3 usa `jakarta.*`, nunca `javax.*`.
- Cada archivo empieza con su `package` y con un `import` por cada clase del proyecto que viva en otro paquete. Usar `PaymentService` desde `infrastructure` sin `import com.x.application.PaymentService` no compila.

Dependencias:

- org.springframework.boot:spring-boot-starter-web n/a
- org.springframework.boot:spring-boot-starter-data-jpa n/a
- org.springframework.boot:spring-boot-starter-validation n/a
- org.springframework.boot:spring-boot-starter-actuator n/a
- org.postgresql:postgresql n/a
- io.github.resilience4j:resilience4j-spring-boot3 2.2.0
- org.springframework.boot:spring-boot-starter-aop n/a
- org.springframework.boot:spring-boot-starter-test n/a
- org.junit.jupiter:junit-jupiter-api n/a
- org.mockito:mockito-core n/a
- org.springdoc:springdoc-openapi-starter-webmvc-ui 2.9.1

## Tu tarea

Dejar este proyecto en estado **verificable**: que el comando de verificacion corra sin errores. Escribi los archivos en disco, en este repositorio. No generes ZIPs ni archivos adjuntos.

En orden:

1. Corre `mvn clean compile` y mira que falla.
2. Completa lo que falte de la lista de abajo: manifiesto de dependencias, punto de entrada, capa de interfaz y las capas del patron declarado.
3. Arregla SOLO los errores que impiden compilar o arrancar.
4. Volve a correr `mvn clean compile` hasta que pase.
5. Pará ahí.

## Regla dura: las fases son trabajo del humano

**PROHIBIDO implementar los entregables de las fases.** El valor del reto esta en que la persona los resuelva. Tu trabajo es que tenga un proyecto que arranca; el hueco pedagogico se queda como esta.

No resuelvas nada de esto:

- **Fase 1 — Análisis de código existente**: documento con hallazgos y propuestas de refactorización
- **Fase 2 — Refactorización del código**: código refactorizado con pruebas unitarias
- **Fase 3 — Evaluación del código refactorizado**: documento de evaluación del código refactorizado

Distincion operativa:

- **Arreglar** (si): import faltante, tipo que no existe, dependencia sin declarar, error de sintaxis, archivo referenciado que no existe.
- **No tocar** (no): logica de negocio incompleta, validaciones ausentes, secretos hardcodeados, APIs deprecadas que funcionan, concurrencia insegura, patrones mejorables. Eso es lo que la persona tiene que encontrar.

## Superficie de practica (NO completes)

Estos archivos SON el ejercicio de la persona. No los implementes; deja stubs. No toques la logica que el reto pide completar.

- [ ] `src/main/java/com/banco/prestamos/infrastructure/config/Resilience4jConfig.java` — El topic pide resiliencia: este archivo es el ejercicio.
- [ ] `src/test/java/com/banco/prestamos/application/usecase/PrestamoServiceTest.java` — El topic pide TDD/pruebas: este archivo es el ejercicio.
- [ ] `src/test/java/com/banco/prestamos/infrastructure/adapter/BuroRiesgoAdapterTest.java` — El topic pide TDD/pruebas: este archivo es el ejercicio.
- [ ] `src/test/java/com/banco/prestamos/infrastructure/controller/PrestamoControllerTest.java` — El topic pide TDD/pruebas: este archivo es el ejercicio.

## Lo que falta y tenes que completar

### 1. Referencias colgando (55)

Salieron de un analisis estatico del codigo que SI esta en el repo. Cada una rompe la compilacion:

- [ ] `src/main/java/com/banco/prestamos/infrastructure/adapter/AntifraudeAdapter.java` — `com.banco.prestamos.infrastructure.exception.AntifraudeException`
      El import com.banco.prestamos.infrastructure.exception.AntifraudeException usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- [ ] `src/main/java/com/banco/prestamos/PrestamosApplication.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/banco/prestamos/application/usecase/PrestamoService.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/banco/prestamos/infrastructure/adapter/AntifraudeAdapter.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/banco/prestamos/infrastructure/adapter/BuroRiesgoAdapter.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/banco/prestamos/infrastructure/adapter/CoreBancarioAdapter.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/banco/prestamos/infrastructure/controller/PrestamoController.java` — `io.swagger.v3`
      El import io.swagger.v3.oas.annotations.Operation pertenece a io.swagger.v3, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/banco/prestamos/infrastructure/controller/PrestamoController.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/banco/prestamos/infrastructure/exception/GlobalExceptionHandler.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/banco/prestamos/infrastructure/config/IdempotencyConfig.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/test/java/com/banco/prestamos/infrastructure/controller/PrestamoControllerTest.java` — `com.fasterxml.jackson`
      El import com.fasterxml.jackson.databind.ObjectMapper pertenece a com.fasterxml.jackson, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/banco/prestamos/domain/port/in/PrestamoUseCase.java` — `RespuestaPrestamo.numeroOperacion`
      Se invoca `numeroOperacion` sobre `RespuestaPrestamo`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banco/prestamos/domain/port/in/PrestamoUseCase.java` — `RespuestaPrestamo.estado`
      Se invoca `estado` sobre `RespuestaPrestamo`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banco/prestamos/domain/port/in/PrestamoUseCase.java` — `RespuestaPrestamo.numeroPrestamo`
      Se invoca `numeroPrestamo` sobre `RespuestaPrestamo`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banco/prestamos/domain/port/in/PrestamoUseCase.java` — `RespuestaPrestamo.montoAprobado`
      Se invoca `montoAprobado` sobre `RespuestaPrestamo`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banco/prestamos/domain/port/in/PrestamoUseCase.java` — `RespuestaPrestamo.tasaInteres`
      Se invoca `tasaInteres` sobre `RespuestaPrestamo`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banco/prestamos/domain/port/in/PrestamoUseCase.java` — `RespuestaPrestamo.plazoMeses`
      Se invoca `plazoMeses` sobre `RespuestaPrestamo`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banco/prestamos/domain/port/in/PrestamoUseCase.java` — `RespuestaPrestamo.mensaje`
      Se invoca `mensaje` sobre `RespuestaPrestamo`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banco/prestamos/domain/port/in/PrestamoUseCase.java` — `RespuestaPrestamo.motivoRechazo`
      Se invoca `motivoRechazo` sobre `RespuestaPrestamo`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banco/prestamos/application/usecase/PrestamoService.java` — `SolicitudPrestamo.clienteId`
      Se invoca `clienteId` sobre `SolicitudPrestamo`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banco/prestamos/application/usecase/PrestamoService.java` — `SolicitudPrestamo.canal`
      Se invoca `canal` sobre `SolicitudPrestamo`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banco/prestamos/application/usecase/PrestamoService.java` — `ResultadoAntifraude.aprobado`
      Se invoca `aprobado` sobre `ResultadoAntifraude`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banco/prestamos/application/usecase/PrestamoService.java` — `ResultadoAntifraude.motivo`
      Se invoca `motivo` sobre `ResultadoAntifraude`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banco/prestamos/application/usecase/PrestamoService.java` — `SolicitudPrestamo.monto`
      Se invoca `monto` sobre `SolicitudPrestamo`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banco/prestamos/application/usecase/PrestamoService.java` — `SolicitudPrestamo.plazoMeses`
      Se invoca `plazoMeses` sobre `SolicitudPrestamo`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banco/prestamos/application/usecase/PrestamoService.java` — `CoreBancarioPort.aprobarPrestamo`
      Se invoca `aprobarPrestamo` sobre `CoreBancarioPort`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banco/prestamos/application/usecase/PrestamoService.java` — `BuroRiesgoPort.consultarHistorial`
      Se invoca `consultarHistorial` sobre `BuroRiesgoPort`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banco/prestamos/infrastructure/adapter/AntifraudeAdapter.java` — `SolicitudPrestamo.clienteId`
      Se invoca `clienteId` sobre `SolicitudPrestamo`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banco/prestamos/infrastructure/adapter/AntifraudeAdapter.java` — `SolicitudPrestamo.monto`
      Se invoca `monto` sobre `SolicitudPrestamo`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banco/prestamos/infrastructure/adapter/AntifraudeAdapter.java` — `SolicitudPrestamo.documentos`
      Se invoca `documentos` sobre `SolicitudPrestamo`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banco/prestamos/infrastructure/adapter/AntifraudeAdapter.java` — `SolicitudPrestamo.canal`
      Se invoca `canal` sobre `SolicitudPrestamo`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banco/prestamos/infrastructure/adapter/AntifraudeAdapter.java` — `SolicitudPrestamo.plazoMeses`
      Se invoca `plazoMeses` sobre `SolicitudPrestamo`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banco/prestamos/infrastructure/adapter/CoreBancarioAdapter.java` — `SolicitudPrestamo.clienteId`
      Se invoca `clienteId` sobre `SolicitudPrestamo`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banco/prestamos/infrastructure/adapter/CoreBancarioAdapter.java` — `SolicitudPrestamo.monto`
      Se invoca `monto` sobre `SolicitudPrestamo`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banco/prestamos/infrastructure/adapter/CoreBancarioAdapter.java` — `SolicitudPrestamo.plazoMeses`
      Se invoca `plazoMeses` sobre `SolicitudPrestamo`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banco/prestamos/infrastructure/controller/PrestamoController.java` — `SolicitudPrestamoRequest.clienteId`
      Se invoca `clienteId` sobre `SolicitudPrestamoRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banco/prestamos/infrastructure/controller/PrestamoController.java` — `SolicitudPrestamoRequest.monto`
      Se invoca `monto` sobre `SolicitudPrestamoRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banco/prestamos/infrastructure/controller/PrestamoController.java` — `SolicitudPrestamoRequest.plazoMeses`
      Se invoca `plazoMeses` sobre `SolicitudPrestamoRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banco/prestamos/infrastructure/controller/PrestamoController.java` — `SolicitudPrestamoRequest.tipoPrestamo`
      Se invoca `tipoPrestamo` sobre `SolicitudPrestamoRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banco/prestamos/infrastructure/controller/PrestamoController.java` — `SolicitudPrestamoRequest.destino`
      Se invoca `destino` sobre `SolicitudPrestamoRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banco/prestamos/infrastructure/controller/PrestamoController.java` — `RespuestaPrestamo.numeroOperacion`
      Se invoca `numeroOperacion` sobre `RespuestaPrestamo`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banco/prestamos/infrastructure/controller/PrestamoController.java` — `RespuestaPrestamo.estado`
      Se invoca `estado` sobre `RespuestaPrestamo`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banco/prestamos/infrastructure/controller/PrestamoController.java` — `PrestamoUseCase.consultarEstado`
      Se invoca `consultarEstado` sobre `PrestamoUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banco/prestamos/infrastructure/exception/IdempotencyException.java` — `Motivo.name`
      Se invoca `name` sobre `Motivo`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banco/prestamos/infrastructure/exception/BuroRiesgoException.java` — `TipoError.esReintentable`
      Se invoca `esReintentable` sobre `TipoError`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banco/prestamos/infrastructure/exception/BuroRiesgoException.java` — `TipoError.name`
      Se invoca `name` sobre `TipoError`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banco/prestamos/infrastructure/exception/BuroRiesgoException.java` — `BuroRiesgoException.initCause`
      Se invoca `initCause` sobre `BuroRiesgoException`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banco/prestamos/infrastructure/config/IdempotencyConfig.java` — `IdempotencyEntry.isExpired`
      Se invoca `isExpired` sobre `IdempotencyEntry`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banco/prestamos/infrastructure/config/IdempotencyConfig.java` — `IdempotencyEntry.procesado`
      Se invoca `procesado` sobre `IdempotencyEntry`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banco/prestamos/infrastructure/config/IdempotencyConfig.java` — `IdempotencyEntry.getValue`
      Se invoca `getValue` sobre `IdempotencyEntry`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/banco/prestamos/application/usecase/PrestamoServiceTest.java` — `BuroRiesgoPort.consultarHistorial`
      Se invoca `consultarHistorial` sobre `BuroRiesgoPort`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/banco/prestamos/application/usecase/PrestamoServiceTest.java` — `CoreBancarioPort.registrarSolicitud`
      Se invoca `registrarSolicitud` sobre `CoreBancarioPort`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/banco/prestamos/application/usecase/PrestamoServiceTest.java` — `CoreBancarioPort.aprobarPrestamo`
      Se invoca `aprobarPrestamo` sobre `CoreBancarioPort`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/banco/prestamos/infrastructure/controller/PrestamoControllerTest.java` — `SolicitudPrestamo.clienteId`
      Se invoca `clienteId` sobre `SolicitudPrestamo`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/banco/prestamos/infrastructure/controller/PrestamoControllerTest.java` — `PrestamoUseCase.consultarPrestamo`
      Se invoca `consultarPrestamo` sobre `PrestamoUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.

### Presentes (24)

- `pom.xml`
- `src/main/java/com/banco/prestamos/PrestamosApplication.java`
- `src/main/resources/application.yml`
- `src/main/java/com/banco/prestamos/domain/model/SolicitudPrestamo.java`
- `src/main/java/com/banco/prestamos/domain/model/RespuestaPrestamo.java`
- `src/main/java/com/banco/prestamos/domain/port/out/AntifraudePort.java`
- `src/main/java/com/banco/prestamos/domain/port/out/BuroRiesgoPort.java`
- `src/main/java/com/banco/prestamos/domain/port/out/CoreBancarioPort.java`
- `src/main/java/com/banco/prestamos/domain/port/in/PrestamoUseCase.java`
- `src/main/java/com/banco/prestamos/application/usecase/PrestamoService.java`
- `src/main/java/com/banco/prestamos/infrastructure/adapter/AntifraudeAdapter.java`
- `src/main/java/com/banco/prestamos/infrastructure/adapter/BuroRiesgoAdapter.java`
- `src/main/java/com/banco/prestamos/infrastructure/adapter/CoreBancarioAdapter.java`
- `src/main/java/com/banco/prestamos/infrastructure/controller/PrestamoController.java`
- `src/main/java/com/banco/prestamos/infrastructure/exception/GlobalExceptionHandler.java`
- `src/main/java/com/banco/prestamos/infrastructure/exception/PrestamoException.java`
- `src/main/java/com/banco/prestamos/infrastructure/exception/IdempotencyException.java`
- `src/main/java/com/banco/prestamos/infrastructure/exception/BuroRiesgoException.java`
- `src/main/java/com/banco/prestamos/infrastructure/config/Resilience4jConfig.java`
- `src/main/java/com/banco/prestamos/infrastructure/config/IdempotencyConfig.java`
- `src/test/java/com/banco/prestamos/application/usecase/PrestamoServiceTest.java`
- `src/test/java/com/banco/prestamos/infrastructure/adapter/BuroRiesgoAdapterTest.java`
- `src/test/java/com/banco/prestamos/infrastructure/controller/PrestamoControllerTest.java`
- `README.md`

### Capas del patron declarado

Cada una tiene que existir como directorio real con al menos un archivo. Codigo plano en la raiz no satisface el patron.

- `src/main/java/com/banco/prestamos/domain`
- `src/main/java/com/banco/prestamos/domain/model`
- `src/main/java/com/banco/prestamos/domain/port`
- `src/main/java/com/banco/prestamos/application`
- `src/main/java/com/banco/prestamos/application/usecase`
- `src/main/java/com/banco/prestamos/infrastructure`
- `src/main/java/com/banco/prestamos/infrastructure/adapter`
- `src/main/java/com/banco/prestamos/infrastructure/config`
- `src/main/java/com/banco/prestamos/infrastructure/controller`
- `src/main/java/com/banco/prestamos/infrastructure/exception`
- `src/main/resources`
- `src/test/java/com/banco/prestamos`

## Verificacion

```bash
mvn clean compile
```

El comando tiene que pasar SIN implementar los archivos de la superficie de practica: solo andamiaje.

Ese comando pasando es la definicion de "terminado" para vos.

## Convenciones que tenes que respetar

- Un solo ecosistema: no declares librerias de otro lenguaje ni mezcles gestores de paquetes.
- Toda libreria que uses tiene que estar declarada en el manifiesto de dependencias.
- Todo import declarado tiene que usarse; todo tipo usado tiene que existir o venir de una dependencia declarada.
- El patron es **hexagonal/clean**: los contratos (interfaces, puertos) los define la capa interna y los implementa la externa, nunca al revés.
- Los archivos que crees llevan implementacion real, no stubs: sin `TODO`, sin cuerpos vacios, sin `// getters y setters`.

## Contexto del candidato

Sirve para calibrar el nivel del codigo, no para resolver las fases.

- Perfil: Chapter Backend, Especialidad Desarrollador, Tecnología Java, Advanced
- Brecha que el reto ataca: Aplica principios y herramientas de verificación para escribir código limpio (KISS, SOLID, YAGNI y DRY). Candidato con experiencia avanzada en Backend Java.

---

*Generado por Challenge Generator — Pragma. `README.md` tiene el enunciado completo del reto para la persona. `PROMPT_MEJORA.md` es la variante para pegar en un chat, si se prefiere ese flujo.*
