# Prompt para Mejorar el Codigo Base

Copia y pega el contenido del bloque de abajo en un asistente de IA (Claude, ChatGPT)
para obtener un ZIP con el proyecto completo y arrancable.

Si preferis trabajar en tu editor con un agente local (Claude Code, Cursor, Copilot), usa `AGENTS.md` en vez de este archivo: dice lo mismo pero para que escriba los archivos en disco.

## Las dos reglas que no se negocian

1. **Completa el boilerplate.** Todo lo que el proyecto necesita para compilar y arrancar: manifiesto de dependencias, punto de entrada, configuracion, capa de interfaz, y las capas del patron arquitectonico declarado. Eso es andamiaje y es tu trabajo.
2. **NO resuelvas el reto.** Los entregables de las fases son el trabajo de la persona. El hueco pedagogico se deja como esta: el proyecto arranca, pero lo que el reto pide implementar NO esta implementado.

Dicho de otra forma: si algo impide compilar, arreglalo. Si algo es logica de negocio incompleta, validaciones ausentes, un secreto hardcodeado o un patron mejorable, dejalo exactamente como esta — es lo que la persona tiene que encontrar.

## Superficie de practica — NO resuelvas

Estos archivos SON el ejercicio de la persona. No los implementes; deja stubs.

- `src/main/java/com/banco/prestamos/infrastructure/config/Resilience4jConfig.java` — El topic pide resiliencia: este archivo es el ejercicio.
- `src/test/java/com/banco/prestamos/application/usecase/PrestamoServiceTest.java` — El topic pide TDD/pruebas: este archivo es el ejercicio.
- `src/test/java/com/banco/prestamos/infrastructure/adapter/BuroRiesgoAdapterTest.java` — El topic pide TDD/pruebas: este archivo es el ejercicio.
- `src/test/java/com/banco/prestamos/infrastructure/controller/PrestamoControllerTest.java` — El topic pide TDD/pruebas: este archivo es el ejercicio.

## Lo que le falta a este proyecto

Esto NO lo tenes que adivinar: salio de comparar el proyecto contra la arquitectura declarada del reto y de un analisis estatico del codigo. Completalo TODO.

### Referencias colgando en el codigo que si esta

Cada una rompe la compilacion:

- `src/main/java/com/banco/prestamos/infrastructure/adapter/AntifraudeAdapter.java` — `com.banco.prestamos.infrastructure.exception.AntifraudeException`: El import com.banco.prestamos.infrastructure.exception.AntifraudeException usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- `src/main/java/com/banco/prestamos/PrestamosApplication.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/banco/prestamos/application/usecase/PrestamoService.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/banco/prestamos/infrastructure/adapter/AntifraudeAdapter.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/banco/prestamos/infrastructure/adapter/BuroRiesgoAdapter.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/banco/prestamos/infrastructure/adapter/CoreBancarioAdapter.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/banco/prestamos/infrastructure/controller/PrestamoController.java` — `io.swagger.v3`: El import io.swagger.v3.oas.annotations.Operation pertenece a io.swagger.v3, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/banco/prestamos/infrastructure/controller/PrestamoController.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/banco/prestamos/infrastructure/exception/GlobalExceptionHandler.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/banco/prestamos/infrastructure/config/IdempotencyConfig.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/test/java/com/banco/prestamos/infrastructure/controller/PrestamoControllerTest.java` — `com.fasterxml.jackson`: El import com.fasterxml.jackson.databind.ObjectMapper pertenece a com.fasterxml.jackson, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/banco/prestamos/domain/port/in/PrestamoUseCase.java` — `RespuestaPrestamo.numeroOperacion`: Se invoca `numeroOperacion` sobre `RespuestaPrestamo`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banco/prestamos/domain/port/in/PrestamoUseCase.java` — `RespuestaPrestamo.estado`: Se invoca `estado` sobre `RespuestaPrestamo`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banco/prestamos/domain/port/in/PrestamoUseCase.java` — `RespuestaPrestamo.numeroPrestamo`: Se invoca `numeroPrestamo` sobre `RespuestaPrestamo`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banco/prestamos/domain/port/in/PrestamoUseCase.java` — `RespuestaPrestamo.montoAprobado`: Se invoca `montoAprobado` sobre `RespuestaPrestamo`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banco/prestamos/domain/port/in/PrestamoUseCase.java` — `RespuestaPrestamo.tasaInteres`: Se invoca `tasaInteres` sobre `RespuestaPrestamo`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banco/prestamos/domain/port/in/PrestamoUseCase.java` — `RespuestaPrestamo.plazoMeses`: Se invoca `plazoMeses` sobre `RespuestaPrestamo`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banco/prestamos/domain/port/in/PrestamoUseCase.java` — `RespuestaPrestamo.mensaje`: Se invoca `mensaje` sobre `RespuestaPrestamo`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banco/prestamos/domain/port/in/PrestamoUseCase.java` — `RespuestaPrestamo.motivoRechazo`: Se invoca `motivoRechazo` sobre `RespuestaPrestamo`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banco/prestamos/application/usecase/PrestamoService.java` — `SolicitudPrestamo.clienteId`: Se invoca `clienteId` sobre `SolicitudPrestamo`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banco/prestamos/application/usecase/PrestamoService.java` — `SolicitudPrestamo.canal`: Se invoca `canal` sobre `SolicitudPrestamo`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banco/prestamos/application/usecase/PrestamoService.java` — `ResultadoAntifraude.aprobado`: Se invoca `aprobado` sobre `ResultadoAntifraude`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banco/prestamos/application/usecase/PrestamoService.java` — `ResultadoAntifraude.motivo`: Se invoca `motivo` sobre `ResultadoAntifraude`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banco/prestamos/application/usecase/PrestamoService.java` — `SolicitudPrestamo.monto`: Se invoca `monto` sobre `SolicitudPrestamo`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banco/prestamos/application/usecase/PrestamoService.java` — `SolicitudPrestamo.plazoMeses`: Se invoca `plazoMeses` sobre `SolicitudPrestamo`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banco/prestamos/application/usecase/PrestamoService.java` — `CoreBancarioPort.aprobarPrestamo`: Se invoca `aprobarPrestamo` sobre `CoreBancarioPort`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banco/prestamos/application/usecase/PrestamoService.java` — `BuroRiesgoPort.consultarHistorial`: Se invoca `consultarHistorial` sobre `BuroRiesgoPort`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banco/prestamos/infrastructure/adapter/AntifraudeAdapter.java` — `SolicitudPrestamo.clienteId`: Se invoca `clienteId` sobre `SolicitudPrestamo`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banco/prestamos/infrastructure/adapter/AntifraudeAdapter.java` — `SolicitudPrestamo.monto`: Se invoca `monto` sobre `SolicitudPrestamo`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banco/prestamos/infrastructure/adapter/AntifraudeAdapter.java` — `SolicitudPrestamo.documentos`: Se invoca `documentos` sobre `SolicitudPrestamo`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banco/prestamos/infrastructure/adapter/AntifraudeAdapter.java` — `SolicitudPrestamo.canal`: Se invoca `canal` sobre `SolicitudPrestamo`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banco/prestamos/infrastructure/adapter/AntifraudeAdapter.java` — `SolicitudPrestamo.plazoMeses`: Se invoca `plazoMeses` sobre `SolicitudPrestamo`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banco/prestamos/infrastructure/adapter/CoreBancarioAdapter.java` — `SolicitudPrestamo.clienteId`: Se invoca `clienteId` sobre `SolicitudPrestamo`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banco/prestamos/infrastructure/adapter/CoreBancarioAdapter.java` — `SolicitudPrestamo.monto`: Se invoca `monto` sobre `SolicitudPrestamo`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banco/prestamos/infrastructure/adapter/CoreBancarioAdapter.java` — `SolicitudPrestamo.plazoMeses`: Se invoca `plazoMeses` sobre `SolicitudPrestamo`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banco/prestamos/infrastructure/controller/PrestamoController.java` — `SolicitudPrestamoRequest.clienteId`: Se invoca `clienteId` sobre `SolicitudPrestamoRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banco/prestamos/infrastructure/controller/PrestamoController.java` — `SolicitudPrestamoRequest.monto`: Se invoca `monto` sobre `SolicitudPrestamoRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banco/prestamos/infrastructure/controller/PrestamoController.java` — `SolicitudPrestamoRequest.plazoMeses`: Se invoca `plazoMeses` sobre `SolicitudPrestamoRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banco/prestamos/infrastructure/controller/PrestamoController.java` — `SolicitudPrestamoRequest.tipoPrestamo`: Se invoca `tipoPrestamo` sobre `SolicitudPrestamoRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banco/prestamos/infrastructure/controller/PrestamoController.java` — `SolicitudPrestamoRequest.destino`: Se invoca `destino` sobre `SolicitudPrestamoRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banco/prestamos/infrastructure/controller/PrestamoController.java` — `RespuestaPrestamo.numeroOperacion`: Se invoca `numeroOperacion` sobre `RespuestaPrestamo`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banco/prestamos/infrastructure/controller/PrestamoController.java` — `RespuestaPrestamo.estado`: Se invoca `estado` sobre `RespuestaPrestamo`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banco/prestamos/infrastructure/controller/PrestamoController.java` — `PrestamoUseCase.consultarEstado`: Se invoca `consultarEstado` sobre `PrestamoUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banco/prestamos/infrastructure/exception/IdempotencyException.java` — `Motivo.name`: Se invoca `name` sobre `Motivo`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banco/prestamos/infrastructure/exception/BuroRiesgoException.java` — `TipoError.esReintentable`: Se invoca `esReintentable` sobre `TipoError`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banco/prestamos/infrastructure/exception/BuroRiesgoException.java` — `TipoError.name`: Se invoca `name` sobre `TipoError`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banco/prestamos/infrastructure/exception/BuroRiesgoException.java` — `BuroRiesgoException.initCause`: Se invoca `initCause` sobre `BuroRiesgoException`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banco/prestamos/infrastructure/config/IdempotencyConfig.java` — `IdempotencyEntry.isExpired`: Se invoca `isExpired` sobre `IdempotencyEntry`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banco/prestamos/infrastructure/config/IdempotencyConfig.java` — `IdempotencyEntry.procesado`: Se invoca `procesado` sobre `IdempotencyEntry`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banco/prestamos/infrastructure/config/IdempotencyConfig.java` — `IdempotencyEntry.getValue`: Se invoca `getValue` sobre `IdempotencyEntry`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/banco/prestamos/application/usecase/PrestamoServiceTest.java` — `BuroRiesgoPort.consultarHistorial`: Se invoca `consultarHistorial` sobre `BuroRiesgoPort`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/banco/prestamos/application/usecase/PrestamoServiceTest.java` — `CoreBancarioPort.registrarSolicitud`: Se invoca `registrarSolicitud` sobre `CoreBancarioPort`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/banco/prestamos/application/usecase/PrestamoServiceTest.java` — `CoreBancarioPort.aprobarPrestamo`: Se invoca `aprobarPrestamo` sobre `CoreBancarioPort`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/banco/prestamos/infrastructure/controller/PrestamoControllerTest.java` — `SolicitudPrestamo.clienteId`: Se invoca `clienteId` sobre `SolicitudPrestamo`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/banco/prestamos/infrastructure/controller/PrestamoControllerTest.java` — `PrestamoUseCase.consultarPrestamo`: Se invoca `consultarPrestamo` sobre `PrestamoUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.

## Como saber que terminaste

```bash
mvn clean compile
```

Ese comando corriendo sin errores es la definicion de "listo".

---

```
## Briefing del reto (autoridad)
Este bloque manda sobre los archivos adjuntos. El stack y el rol salen de AQUÍ, no de un topic genérico ni de markdown placeholder.

### Perfil
Chapter Backend, Especialidad Desarrollador, Tecnología Java, Advanced

### Brecha de conocimiento
Aplica principios y herramientas de verificación para escribir código limpio (KISS, SOLID, YAGNI y DRY). Candidato con experiencia avanzada en Backend Java.

### Reto
- Tema: aplicación de código limpio y eficiente
- Seniority: advanced-l2
- Tipo: practical
- Título: Refactorización de servicio de préstamos
- Tiempo estimado: 1 semana

### Fases (trabajo del HUMANO — PROHIBIDO completarlas)
No implementes estos entregables. Dejalos como hueco pedagógico. El asistente solo materializa el proyecto arrancable para que el participante pueda trabajar.
- Fase 1: Análisis de código existente — objetivo: identificar áreas de mejora en términos de código limpio y eficiencia — entregable (NO resolver): documento con hallazgos y propuestas de refactorización
- Fase 2: Refactorización del código — objetivo: aplicar cambios propuestos para mejorar el código — entregable (NO resolver): código refactorizado con pruebas unitarias
- Fase 3: Evaluación del código refactorizado — objetivo: evaluar la calidad del código refactorizado — entregable (NO resolver): documento de evaluación del código refactorizado

Eres un asistente experto en análisis, corrección y generación de archivos de cualquier tipo:
código fuente, documentación, hojas de cálculo, documentos Word, configuraciones, entre otros.
Voy a enviarte una cadena de texto que contiene uno o más archivos. Cada archivo está delimitado por un marcador con el siguiente formato:
// === ARCHIVO: ruta/del/archivo.extension ===
o también puede aparecer como:
## === ARCHIVO: ruta/del/archivo.extension ===
Lo que sigue al marcador puede ser:

El contenido real del archivo (código, texto, YAML, etc.)
Una descripción en lenguaje natural de lo que debe contener el archivo


TU TAREA
PASO 0 — ¿Esto es un proyecto o una carcasa?
Antes de extraer archivos, leé el Briefing (si está) y diagnosticá el adjunto.

Es CARCASA si ocurre CUALQUIERA de estas:
- No hay manifiesto de dependencias del stack del briefing (manifest.json de VTEX IO / package.json / pom.xml / build.gradle / requirements.txt / go.mod / *.tf / *.csproj, según corresponda)
- Hay un "binario" que en realidad es un comentario ("no puede ser mostrado como texto plano", placeholder .fig/.docx vacío)
- Los markdowns ya completan entregables de fases posteriores ("se implementó fade-in", lista de áreas ya resuelta)

Si es CARCASA:
- MATERIALIZÁ un proyecto que arranca en el stack del briefing (VTEX IO Store Framework, Angular, Terraform, pytest, Nest, etc.). Incluí manifiesto, punto de entrada y capa de interfaz reales.
- NO copies los markdowns de "solución" como si fueran el producto. Son ruido de generación.
- NO resuelvas las fases del briefing (están marcadas PROHIBIDO). Dejá el hueco pedagógico: el flujo existe, las microinteracciones/calidad/infra que el reto pide NO están hechas.
- Después seguí al PASO 5 (ZIP).

Si es un proyecto REAL (manifiesto + código que compila o arranca):
- Seguí PASO 1 en adelante. 🔴 compilación sí. 🟡 pedagógico no.

PASO 1 — Detección y extracción
Identifica todos los archivos presentes en la cadena. Para cada archivo extrae:

Su ruta completa (ej: src/main/java/com/pragma/Service.java)
Su contenido o descripción

PASO 2 — Clasificación por tipo
Clasifica cada archivo en una de estas categorías:
A) Código fuente (Java, Python, TypeScript, JavaScript, Kotlin, etc.)
B) Configuración / documentación (YAML, properties, Markdown, JSON, txt, etc.)
C) Excel (.xlsx, .xls, .csv)
D) Word (.docx, .doc)
E) Otro tipo de archivo binario o especial
PASO 3 — Clasificación de errores en código fuente

Objetivo prioritario: que el proyecto compile. No corrijas flujo de negocio ni lógica funcional.

Antes de modificar cualquier archivo de código fuente, clasifica cada problema encontrado en una de estas dos categorías:
🔴 ERROR DE COMPILACIÓN — corregir siempre
Son errores que impiden que el proyecto arranque, sin valor pedagógico:

Import faltante o incorrecto
Clase, método o variable referenciada que no existe en ningún archivo del proyecto
Error de sintaxis
Anotación con atributos inválidos
Dependencia ausente en pom.xml, package.json, etc.
Archivo referenciado que no existe y debe ser creado con implementación mínima

→ CORREGIR estos errores.
🟡 PROBLEMA FUNCIONAL O DE CALIDAD — preservar siempre
Son problemas que no impiden compilar. Pueden ser intencionales para el aprendizaje:

Clave secreta hardcodeada ("secret", "password123")
API deprecada que funciona pero tiene reemplazo moderno
Lógica de negocio incorrecta o incompleta
Código redundante o de baja legibilidad
Falta de validaciones en flujo de negocio
Patrones de diseño incorrectos pero funcionales
Concurrencia no segura
Configuración funcional pero no óptima

→ PRESERVAR tal cual. No corregir, no mejorar, no comentar.
PASO 4 — Procesamiento según tipo de archivo
Tipo A — Código fuente
Aplica únicamente las correcciones clasificadas como 🔴 ERROR DE COMPILACIÓN.
No alteres ningún elemento clasificado como 🟡 PROBLEMA FUNCIONAL O DE CALIDAD.
Si falta un archivo referenciado, créalo con la implementación mínima necesaria para compilar.
Tipo B — Configuración / documentación
Extrae el contenido tal cual, sin modificaciones salvo errores evidentes de sintaxis
(ej: YAML mal indentado).
Tipo C — Excel (.xlsx)
Si viene con contenido real, genera el archivo respetando ese contenido.
Si viene con descripción en lenguaje natural, genera un archivo Excel funcional con:

Fila de encabezados en negrita con color de fondo distintivo
Columnas con ancho ajustado al contenido
Tipos de dato correctos por columna
Validaciones si la descripción lo indica
Hojas nombradas descriptivamente si hay más de una
Filas de ejemplo si no hay datos reales

Tipo D — Word (.docx)
Si viene con contenido real, genera el archivo respetando ese contenido.
Si viene con descripción en lenguaje natural, genera un documento Word funcional con:

Estilos de título (Título 1, Título 2) para jerarquía de secciones
Fuente legible (Calibri o equivalente), tamaño 11-12pt para cuerpo
Márgenes estándar
Tabla de contenido si tiene múltiples secciones
Tablas con encabezados en negrita si aplica

Tipo E — Otro
Genera el archivo con el contenido o estructura más apropiada según la descripción.
PASO 5 — Exportación en ZIP
Empaqueta todos los archivos en un único archivo ZIP descargable respetando exactamente
la estructura de rutas indicada por los marcadores.
El ZIP debe incluir:

Archivos de código con únicamente los errores de compilación corregidos
Archivos de configuración y documentación sin cambios
Archivos nuevos creados para resolver dependencias de compilación faltantes
Archivos Excel y Word generados desde descripción

IMPORTANTE: El ZIP debe estar listo para descargar al finalizar. No preguntes si el usuario
quiere generarlo. Simplemente genera el archivo y proporciona el enlace de descarga; No debes desplegar en el chat el resumen de lo que arreglaste al Zip, solo entregalo.

REGLAS IMPORTANTES

No omitas ningún archivo aunque no tenga errores ni modificaciones
Respeta los nombres y rutas exactas indicadas por los marcadores
Si un archivo no tiene marcador claro, infiere el nombre desde su contenido
Si la cadena contiene solo documentación, placeholders o binarios fake, NO la reproduzcas:
aplicá PASO 0 (materializar el proyecto del briefing). Reproducir la carcasa es un fallo.
No agregues texto después del enlace de descarga del ZIP
No preguntes si el usuario quiere el ZIP: simplemente generalo siempre
Si detectas que falta un archivo de configuración necesario para compilar
(pom.xml, package.json, requirements.txt, build.gradle, etc.), créalo e inclúyelo
inferiendo su contenido desde los imports y frameworks detectados en el código
Nunca corrijas problemas 🟡 aunque parezcan obvios o fáciles de mejorar.
El participante que recibirá este proyecto los debe encontrar y resolver él mismo.


INPUT
Aquí está la cadena con los archivos:

// === ARCHIVO: pom.xml ===
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.5.6</version>
        <relativePath/>
    </parent>

    <groupId>com.banco</groupId>
    <artifactId>prestamos</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>prestamos</name>
    <description>Servicio de préstamos con integración a buró de riesgos y core bancario</description>

    <properties>
        <java.version>21</java.version>
        <resilience4j.version>2.2.0</resilience4j.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>io.github.resilience4j</groupId>
            <artifactId>resilience4j-spring-boot3</artifactId>
            <version>${resilience4j.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-aop</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springdoc</groupId>
            <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
            <version>2.9.1</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-api</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.mockito</groupId>
            <artifactId>mockito-core</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

    <repositories>
        <repository>
            <id>central</id>
            <url>https://repo.maven.apache.org/maven2</url>
        </repository>
    </repositories>
</project>

// === ARCHIVO: src/main/java/com/banco/prestamos/PrestamosApplication.java ===
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

// === ARCHIVO: src/main/resources/application.yml ===
spring:
  application:
    name: prestamos-service
  profiles:
    active: dev
  datasource:
    url: jdbc:postgresql://localhost:5432/prestamos_db
    username: prestamos_user
    password: prestamos_pass
    driver-class-name: org.postgresql.Driver
    hikari:
      connection-timeout: 30000
      maximum-pool-size: 10
      idle-timeout: 600000
      max-lifetime: 1800000
  jpa:
    database-platform: org.hibernate.dialect.PostgreSQLDialect
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        format_sql: true
        jdbc:
          lob:
            non_contextual_creation: true
    show-sql: false
  mvc:
    pathmatch:
      matching-strategy: ant_path_matcher

server:
  port: 8080
  servlet:
    context-path: /api/prestamos

management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus
  endpoint:
    health:
      show-details: always
  health:
    circuitbreakers:
      enabled: true

resilience4j:
  circuitbreaker:
    configs:
      default:
        slidingWindowType: COUNT_BASED
        slidingWindowSize: 10
        failureRateThreshold: 50
        waitDurationInOpenState: 5s
        permittedNumberOfCallsInHalfOpenState: 3
        automaticTransitionFromOpenToHalfOpenEnabled: true
        ignoreExceptions:
          - com.banco.prestamos.infrastructure.exception.IdempotencyException
    instances:
      buroRiesgo:
        baseConfig: default
        failureRateThreshold: 60
        waitDurationInOpenState: 10s
  retry:
    configs:
      default:
        maxAttempts: 3
        waitDuration: 500ms
        retryExceptions:
          - org.springframework.web.client.ResourceAccessException
          - java.util.concurrent.TimeoutException
          - com.banco.prestamos.infrastructure.exception.BuroRiesgoException
    instances:
      buroRiesgo:
        baseConfig: default
        maxAttempts: 4
        waitDuration: 1s

springdoc:
  api-docs:
    path: /api-docs
  swagger-ui:
    path: /swagger-ui.html
    operationsSorter: method
    tagsSorter: alpha
    docExpansion: none

logging:
  level:
    root: INFO
    com.banco.prestamos: DEBUG
    org.hibernate.SQL: DEBUG
    org.hibernate.type.descriptor.sql.BasicBinder: TRACE
  pattern:
    console: "%d{yyyy-MM-dd HH:mm:ss} - %msg%n"
    file: "%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n"
  file:
    name: logs/prestamos-service.log
    max-size: 10MB
    max-history: 7

// === ARCHIVO: src/main/java/com/banco/prestamos/domain/model/SolicitudPrestamo.java ===
package com.banco.prestamos.domain.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record SolicitudPrestamo(
    @NotNull(message = "El número de operación no puede ser nulo")
    String numeroOperacion,
    
    @NotNull(message = "El canal no puede ser nulo")
    @Size(min = 3, max = 10, message = "El canal debe tener entre 3 y 10 caracteres")
    String canal,
    
    @NotNull(message = "El identificador del cliente no puede ser nulo")
    UUID clienteId,
    
    @NotNull(message = "El monto solicitado no puede ser nulo")
    @Positive(message = "El monto solicitado debe ser positivo")
    BigDecimal montoSolicitado,
    
    @NotNull(message = "El plazo en meses no puede ser nulo")
    @Positive(message = "El plazo en meses debe ser positivo")
    Integer plazoMeses,
    
    @NotNull(message = "La fecha de solicitud no puede ser nula")
    LocalDateTime fechaSolicitud,
    
    @NotNull(message = "El propósito del préstamo no puede ser nulo")
    @Size(max = 100, message = "El propósito del préstamo no puede exceder 100 caracteres")
    String proposito,
    
    @NotNull(message = "El estado de la solicitud no puede ser nulo")
    EstadoSolicitud estado,
    
    String motivoRechazo
) {
    public enum EstadoSolicitud {
        RECIBIDA,
        EN_PROCESO,
        APROBADA,
        RECHAZADA
    }
    
    public SolicitudPrestamo {
        if (montoSolicitado != null && montoSolicitado.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto solicitado debe ser positivo");
        }
        if (plazoMeses != null && plazoMeses <= 0) {
            throw new IllegalArgumentException("El plazo en meses debe ser positivo");
        }
        if (fechaSolicitud == null) {
            fechaSolicitud = LocalDateTime.now();
        }
        if (estado == null) {
            estado = EstadoSolicitud.RECIBIDA;
        }
    }
    
    public SolicitudPrestamo conEstado(EstadoSolicitud nuevoEstado) {
        return new SolicitudPrestamo(
            this.numeroOperacion,
            this.canal,
            this.clienteId,
            this.montoSolicitado,
            this.plazoMeses,
            this.fechaSolicitud,
            this.proposito,
            nuevoEstado,
            this.motivoRechazo
        );
    }
    
    public SolicitudPrestamo conMotivoRechazo(String motivo) {
        return new SolicitudPrestamo(
            this.numeroOperacion,
            this.canal,
            this.clienteId,
            this.montoSolicitado,
            this.plazoMeses,
            this.fechaSolicitud,
            this.proposito,
            EstadoSolicitud.RECHAZADA,
            motivo
        );
    }
}

// === ARCHIVO: src/main/java/com/banco/prestamos/domain/model/RespuestaPrestamo.java ===
package com.banco.prestamos.domain.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record RespuestaPrestamo(
    @NotNull(message = "El número de operación no puede ser nulo")
    String numeroOperacion,
    
    @NotNull(message = "El identificador del cliente no puede ser nulo")
    UUID clienteId,
    
    @NotNull(message = "El estado de la respuesta no puede ser nulo")
    EstadoRespuesta estado,
    
    @Positive(message = "La tasa de interés debe ser positiva")
    BigDecimal tasaInteres,
    
    @Positive(message = "El monto aprobado debe ser positivo")
    BigDecimal montoAprobado,
    
    Integer plazoMeses,
    
    String numeroContrato,
    
    @NotNull(message = "La fecha de respuesta no puede ser nula")
    LocalDateTime fechaRespuesta,
    
    String motivoRechazo
) {
    public enum EstadoRespuesta {
        APROBADA,
        RECHAZADA,
        ERROR
    }
    
    public RespuestaPrestamo {
        if (tasaInteres != null && tasaInteres.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("La tasa de interés debe ser positiva");
        }
        if (montoAprobado != null && montoAprobado.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto aprobado debe ser positivo");
        }
        if (fechaRespuesta == null) {
            fechaRespuesta = LocalDateTime.now();
        }
    }
    
    public static RespuestaPrestamo aprobada(String numeroOperacion, UUID clienteId, BigDecimal tasaInteres,
                                           BigDecimal montoAprobado, Integer plazoMeses, String numeroContrato) {
        return new RespuestaPrestamo(
            numeroOperacion,
            clienteId,
            EstadoRespuesta.APROBADA,
            tasaInteres,
            montoAprobado,
            plazoMeses,
            numeroContrato,
            LocalDateTime.now(),
            null
        );
    }
    
    public static RespuestaPrestamo rechazada(String numeroOperacion, UUID clienteId, String motivoRechazo) {
        return new RespuestaPrestamo(
            numeroOperacion,
            clienteId,
            EstadoRespuesta.RECHAZADA,
            BigDecimal.ZERO,
            BigDecimal.ZERO,
            null,
            null,
            LocalDateTime.now(),
            motivoRechazo
        );
    }
    
    public static RespuestaPrestamo error(String numeroOperacion, UUID clienteId) {
        return new RespuestaPrestamo(
            numeroOperacion,
            clienteId,
            EstadoRespuesta.ERROR,
            BigDecimal.ZERO,
            BigDecimal.ZERO,
            null,
            null,
            LocalDateTime.now(),
            "Error en el procesamiento de la solicitud"
        );
    }
}

// === ARCHIVO: src/main/java/com/banco/prestamos/domain/port/out/AntifraudePort.java ===
package com.banco.prestamos.domain.port.out;

import com.banco.prestamos.domain.model.SolicitudPrestamo;
import java.util.concurrent.CompletableFuture;

public interface AntifraudePort {
    /**
     * Evalúa el riesgo de fraude de una solicitud de préstamo.
     * @param solicitud La solicitud de préstamo a evaluar
     * @return CompletableFuture con el resultado de la evaluación de fraude
     * @throws AntifraudeException si ocurre un error durante la evaluación
     */
    CompletableFuture<ResultadoAntifraude> evaluarRiesgoFraude(SolicitudPrestamo solicitud);
    
    /**
     * Resultado de la evaluación de antifraude.
     */
    record ResultadoAntifraude(
        boolean riesgoAlto,
        String motivo
    ) {
        public boolean esAprobable() {
            return !riesgoAlto;
        }
    }
    
    /**
     * Excepción lanzada cuando ocurre un error en la evaluación de antifraude.
     */
    class AntifraudeException extends RuntimeException {
        public AntifraudeException(String message) {
            super(message);
        }
        
        public AntifraudeException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}

// === ARCHIVO: src/main/java/com/banco/prestamos/domain/port/out/BuroRiesgoPort.java ===
package com.banco.prestamos.domain.port.out;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public interface BuroRiesgoPort {

    InformacionBuro consultarHistoriaCrediticia(UUID clienteId, BigDecimal montoSolicitado);

    record InformacionBuro(
        UUID clienteId,
        String nombreCliente,
        int scoreCrediticio,
        BigDecimal deudaActual,
        int cantidadConsultasUltimos30Dias,
        boolean tieneMorosidades,
        String categoriaRiesgo,
        LocalDateTime fechaUltimaConsulta
    ) {
        public boolean esAptoParaCredito(BigDecimal montoSolicitado, int scoreMinimo) {
            return scoreCrediticio >= scoreMinimo 
                && !tieneMorosidades 
                && deudaActual.compareTo(montoSolicitado.multiply(BigDecimal.valueOf(3))) < 0;
        }

        public String nivelRiesgo() {
            if (scoreCrediticio >= 750) return "BAJO";
            if (scoreCrediticio >= 600) return "MEDIO";
            return "ALTO";
        }
    }

    class BuroRiesgoException extends RuntimeException {
        public BuroRiesgoException(String message) {
            super(message);
        }

        public BuroRiesgoException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    class BuroRiesgoNoDisponibleException extends BuroRiesgoException {
        public BuroRiesgoNoDisponibleException(String message) {
            super(message);
        }

        public BuroRiesgoNoDisponibleException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    class ClienteNoEncontradoEnBuroException extends BuroRiesgoException {
        public ClienteNoEncontradoEnBuroException(UUID clienteId) {
            super("Cliente no encontrado en bureau de riesgos: " + clienteId);
        }
    }
}

// === ARCHIVO: src/main/java/com/banco/prestamos/domain/port/out/CoreBancarioPort.java ===
package com.banco.prestamos.domain.port.out;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public interface CoreBancarioPort {

    ResultadoRegistroPrestamo registrarPrestamo(
        UUID clienteId,
        String numeroOperacion,
        BigDecimal montoSolicitado,
        int plazoMeses,
        BigDecimal tasaInteres,
        String canalOrigen
    );

    InformacionCliente obtenerInformacionCliente(UUID clienteId);

    boolean verificarIdentidadCliente(UUID clienteId, String numeroDocumento);

    record ResultadoRegistroPrestamo(
        String numeroPrestamo,
        UUID clienteId,
        BigDecimal montoAprobado,
        BigDecimal tasaInteresAprobada,
        int plazoAprobado,
        LocalDateTime fechaAprobacion,
        String estado,
        String mensaje
    ) {
        public boolean esExitoso() {
            return "APROBADO".equals(estado);
        }

        public static ResultadoRegistroPrestamo aprobado(
            String numeroPrestamo,
            UUID clienteId,
            BigDecimal monto,
            BigDecimal tasa,
            int plazo
        ) {
            return new ResultadoRegistroPrestamo(
                numeroPrestamo,
                clienteId,
                monto,
                tasa,
                plazo,
                LocalDateTime.now(),
                "APROBADO",
                "Prestamo registrado exitosamente"
            );
        }

        public static ResultadoRegistroPrestamo rechazado(
            String numeroPrestamo,
            UUID clienteId,
            String motivo
        ) {
            return new ResultadoRegistroPrestamo(
                numeroPrestamo,
                clienteId,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                0,
                LocalDateTime.now(),
                "RECHAZADO",
                motivo
            );
        }
    }

    record InformacionCliente(
        UUID clienteId,
        String numeroDocumento,
        String nombreCompleto,
        String tipoDocumento,
        LocalDate fechaNacimiento,
        String segmento,
        BigDecimal ingresoMensual,
        boolean tieneCupoActivo,
        BigDecimal cupoDisponible,
        LocalDateTime fechaAlta
    ) {
        public boolean puedeSolicitarPrestamo(BigDecimal montoSolicitado) {
            return tieneCupoActivo 
                && cupoDisponible.compareTo(montoSolicitado) >= 0
                && ingresoMensual.compareTo(montoSolicitado.divide(BigDecimal.valueOf(12))) > 0;
        }
    }

    class CoreBancarioException extends RuntimeException {
        public CoreBancarioException(String message) {
            super(message);
        }

        public CoreBancarioException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    class ClienteNoEncontradoException extends CoreBancarioException {
        public ClienteNoEncontradoException(UUID clienteId) {
            super("Cliente no encontrado en core bancario: " + clienteId);
        }
    }

    class ErrorRegistroPrestamoException extends CoreBancarioException {
        public ErrorRegistroPrestamoException(String message) {
            super(message);
        }

        public ErrorRegistroPrestamoException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}

// === ARCHIVO: src/main/java/com/banco/prestamos/domain/port/in/PrestamoUseCase.java ===
package com.banco.prestamos.domain.port.in;

import com.banco.prestamos.domain.model.RespuestaPrestamo;
import com.banco.prestamos.domain.model.SolicitudPrestamo;
import java.util.UUID;

public interface PrestamoUseCase {

    RespuestaPrestamo procesarSolicitud(SolicitudPrestamo solicitud);

    RespuestaPrestamo consultarEstadoSolicitud(UUID solicitudId);

    boolean verificarIdempotencia(String numeroOperacion, String canal);

    SolicitudPrestamo crearSolicitud(
        UUID clienteId,
        String numeroOperacion,
        String canal,
        java.math.BigDecimal monto,
        int plazoMeses,
        String proposito
    );

    record SolicitudPrestamoCommand(
        UUID clienteId,
        String numeroOperacion,
        String canal,
        java.math.BigDecimal monto,
        int plazoMeses,
        String proposito,
        String numeroDocumento
    ) {
        public SolicitudPrestamoCommand {
            if (monto == null || monto.compareTo(java.math.BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("El monto debe ser mayor a cero");
            }
            if (plazoMeses <= 0 || plazoMeses > 360) {
                throw new IllegalArgumentException("El plazo debe estar entre 1 y 360 meses");
            }
            if (clienteId == null) {
                throw new IllegalArgumentException("El ID del cliente es obligatorio");
            }
            if (numeroOperacion == null || numeroOperacion.isBlank()) {
                throw new IllegalArgumentException("El numero de operacion es obligatorio");
            }
            if (canal == null || canal.isBlank()) {
                throw new IllegalArgumentException("El canal es obligatorio");
            }
        }
    }

    record RespuestaPrestamoView(
        UUID solicitudId,
        String numeroOperacion,
        String estado,
        String numeroPrestamo,
        java.math.BigDecimal montoAprobado,
        java.math.BigDecimal tasaInteres,
        Integer plazoMeses,
        String mensaje,
        String motivoRechazo
    ) {
        public static RespuestaPrestamoView desdeRespuesta(RespuestaPrestamo respuesta, UUID solicitudId) {
            return new RespuestaPrestamoView(
                solicitudId,
                respuesta.numeroOperacion(),
                respuesta.estado().name(),
                respuesta.numeroPrestamo(),
                respuesta.montoAprobado(),
                respuesta.tasaInteres(),
                respuesta.plazoMeses(),
                respuesta.mensaje(),
                respuesta.motivoRechazo()
            );
        }
    }

    class PrestamoUseCaseException extends RuntimeException {
        public PrestamoUseCaseException(String message) {
            super(message);
        }

        public PrestamoUseCaseException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    class SolicitudDuplicadaException extends PrestamoUseCaseException {
        public SolicitudDuplicadaException(String numeroOperacion, String canal) {
            super("Ya existe una solicitud con numero de operacion " + numeroOperacion + " para el canal " + canal);
        }
    }

    class SolicitudInvalidaException extends PrestamoUseCaseException {
        public SolicitudInvalidaException(String message) {
            super(message);
        }
    }
}

// === ARCHIVO: src/main/java/com/banco/prestamos/application/usecase/PrestamoService.java ===
package com.banco.prestamos.application.usecase;


import com.banco.prestamos.domain.port.out.ResultadoAntifraude;
import com.banco.prestamos.domain.model.SolicitudPrestamo;
import com.banco.prestamos.domain.model.RespuestaPrestamo;
import com.banco.prestamos.domain.port.out.AntifraudePort;
import com.banco.prestamos.domain.port.out.BuroRiesgoPort;
import com.banco.prestamos.domain.port.out.CoreBancarioPort;
import com.banco.prestamos.infrastructure.exception.PrestamoException;
import com.banco.prestamos.infrastructure.exception.BuroRiesgoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
public class PrestamoService {

    private static final Logger log = LoggerFactory.getLogger(PrestamoService.class);
    private static final int TIMEOUT_SECONDS = 30;
    private static final BigDecimal MONTO_MINIMO = new BigDecimal("10000");
    private static final BigDecimal MONTO_MAXIMO = new BigDecimal("5000000");
    private static final int PLAZO_MINIMO_MESES = 6;
    private static final int PLAZO_MAXIMO_MESES = 120;
    private static final BigDecimal TASA_BASE = new BigDecimal("0.12");
    private static final BigDecimal TASA_ALTO_RIESGO = new BigDecimal("0.25");

    private final AntifraudePort antifraudePort;
    private final BuroRiesgoPort buroRiesgoPort;
    private final CoreBancarioPort coreBancarioPort;

    public PrestamoService(AntifraudePort antifraudePort,
                          BuroRiesgoPort buroRiesgoPort,
                          CoreBancarioPort coreBancarioPort) {
        this.antifraudePort = antifraudePort;
        this.buroRiesgoPort = buroRiesgoPort;
        this.coreBancarioPort = coreBancarioPort;
    }

    public RespuestaPrestamo procesarSolicitud(SolicitudPrestamo solicitud) {
        log.info("Iniciando procesamiento de solicitud para cliente: {} canal: {}",
                solicitud.clienteId(), solicitud.canal());

        validarSolicitud(solicitud);

        String numeroOperacion = generarNumeroOperacion(solicitud);

        try {
            AntifraudePort.ResultadoAntifraude resultadoAntifraude = evaluarRiesgoFraude(solicitud)
                    .get(TIMEOUT_SECONDS, TimeUnit.SECONDS);

            if (!resultadoAntifraude.aprobado()) {
                log.warn("Solicitud rechazada por antifraude: {}", resultadoAntifraude.motivo());
                return RespuestaPrestamo.rechazada(numeroOperacion,
                        solicitud.clienteId(),
                        "Fraude detectado: " + resultadoAntifraude.motivo());
            }

            BuroRiesgoPort.ResultadoBuro resultadoBuro = consultarBuroRiesgo(solicitud);

            if (resultadoBuro.tieneMorosidad()) {
                log.warn("Cliente {} tiene morosidad en buró de riesgos", solicitud.clienteId());
                return RespuestaPrestamo.rechazada(numeroOperacion,
                        solicitud.clienteId(),
                        "Cliente con morosidad activa en buró de riesgos");
            }

            BigDecimal tasaInteres = calcularTasa(resultadoBuro, solicitud.monto());
            BigDecimal cuotaMensual = calcularCuota(solicitud.monto(), tasaInteres, solicitud.plazoMeses());

            boolean aprobadoCore = coreBancarioPort.aprobarPrestamo(solicitud.clienteId(),
                    solicitud.monto(), tasaInteres, solicitud.plazoMeses(), numeroOperacion);

            if (!aprobadoCore) {
                log.warn("Core bancario rechazó la operación para cliente: {}", solicitud.clienteId());
                return RespuestaPrestamo.rechazada(numeroOperacion,
                        solicitud.clienteId(),
                        "Rechazo del core bancario");
            }

            log.info("Préstamo aprobado para cliente: {} número operación: {}",
                    solicitud.clienteId(), numeroOperacion);

            return RespuestaPrestamo.aprobada(numeroOperacion,
                    solicitud.clienteId(),
                    tasaInteres,
                    cuotaMensual,
                    solicitud.plazoMeses(),
                    Instant.now().plus(Duration.ofDays(30)).toString());

        } catch (BuroRiesgoException e) {
            log.error("Error al consultar buró de riesgos: {}", e.getMessage());
            throw new PrestamoException("Error al verificar historial crediticio: " + e.getMessage(), e);
        } catch (Exception e) {
            log.error("Error procesamiento solicitud: {}", e.getMessage(), e);
            return RespuestaPrestamo.error(numeroOperacion, solicitud.clienteId());
        }
    }

    private void validarSolicitud(SolicitudPrestamo solicitud) {
        if (solicitud == null) {
            throw new PrestamoException("La solicitud no puede ser nula");
        }
        if (solicitud.clienteId() == null) {
            throw new PrestamoException("El ID del cliente es obligatorio");
        }
        if (solicitud.monto() == null || solicitud.monto().compareTo(MONTO_MINIMO) < 0
                || solicitud.monto().compareTo(MONTO_MAXIMO) > 0) {
            throw new PrestamoException("El monto debe estar entre " + MONTO_MINIMO + " y " + MONTO_MAXIMO);
        }
        if (solicitud.plazoMeses() == null || solicitud.plazoMeses() < PLAZO_MINIMO_MESES
                || solicitud.plazoMeses() > PLAZO_MAXIMO_MESES) {
            throw new PrestamoException("El plazo debe estar entre " + PLAZO_MINIMO_MESES + " y " + PLAZO_MAXIMO_MESES + " meses");
        }
        if (solicitud.canal() == null || solicitud.canal().isBlank()) {
            throw new PrestamoException("El canal de origen es obligatorio");
        }
    }

    private CompletableFuture<AntifraudePort.ResultadoAntifraude> evaluarRiesgoFraude(SolicitudPrestamo solicitud) {
        return antifraudePort.evaluarRiesgoFraude(solicitud);
    }

    private BuroRiesgoPort.ResultadoBuro consultarBuroRiesgo(SolicitudPrestamo solicitud) {
        return buroRiesgoPort.consultarHistorial(solicitud.clienteId());
    }

    private BigDecimal calcularTasa(BuroRiesgoPort.ResultadoBuro resultadoBuro, BigDecimal monto) {
        int score = resultadoBuro.scoreCrediticio();
        boolean tieneHistorial = resultadoBuro.tieneHistorial();

        if (!tieneHistorial || score < 600) {
            return TASA_ALTO_RIESGO;
        } else if (score >= 600 && score < 700) {
            return TASA_BASE.add(new BigDecimal("0.03"));
        } else if (score >= 700 && score < 800) {
            return TASA_BASE;
        } else {
            return TASA_BASE.subtract(new BigDecimal("0.02"));
        }
    }

    private BigDecimal calcularCuota(BigDecimal monto, BigDecimal tasaAnual, Integer plazoMeses) {
        BigDecimal tasaMensual = tasaAnual.divide(new BigDecimal("12"), 10, java.math.RoundingMode.HALF_UP);
        BigDecimal factor = tasaMensual.multiply(
                BigDecimal.ONE.add(tasaMensual).pow(plazoMeses)
        ).divide(
                BigDecimal.ONE.add(tasaMensual).pow(plazoMeses).subtract(BigDecimal.ONE),
                10, java.math.RoundingMode.HALF_UP
        );
        return monto.multiply(factor).setScale(2, java.math.RoundingMode.HALF_UP);
    }

    private String generarNumeroOperacion(SolicitudPrestamo solicitud) {
        String timestamp = String.valueOf(Instant.now().toEpochMilli());
        String uuid = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        String canal = solicitud.canal().substring(0, Math.min(3, solicitud.canal().length())).toUpperCase();
        return "PR-" + canal + "-" + timestamp + "-" + uuid;
    }
}

// === ARCHIVO: src/main/java/com/banco/prestamos/infrastructure/adapter/AntifraudeAdapter.java ===
package com.banco.prestamos.infrastructure.adapter;


import com.banco.prestamos.domain.port.out.ResultadoAntifraude;
import com.banco.prestamos.domain.model.SolicitudPrestamo;
import com.banco.prestamos.domain.port.out.AntifraudePort;
import com.banco.prestamos.infrastructure.exception.AntifraudeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Component
public class AntifraudeAdapter implements AntifraudePort {

    private static final Logger log = LoggerFactory.getLogger(AntifraudeAdapter.class);
    private static final BigDecimal UMBRAL_MONTO_SOSPECHOSO = new BigDecimal("1000000");
    private static final int PUNTUAJE_MINIMO_APROBACION = 50;
    private final Random random = new Random();

    @Override
    public CompletableFuture<ResultadoAntifraude> evaluarRiesgoFraude(SolicitudPrestamo solicitud) {
        log.info("Evaluando riesgo de fraude para cliente: {}", solicitud.clienteId());

        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(random.nextInt(500) + 100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            return ejecutarEvaluacion(solicitud);
        });
    }

    private ResultadoAntifraude ejecutarEvaluacion(SolicitudPrestamo solicitud) {
        boolean esMontoSospechoso = solicitud.monto().compareTo(UMBRAL_MONTO_SOSPECHOSO) > 0;
        boolean tieneDocumentacionCompleta = solicitud.documentos() != null
                && !solicitud.documentos().isEmpty();
        boolean esCanalConfiable = esCanalValido(solicitud.canal());
        boolean tieneHistorial = solicitud.clienteId() != null;

        int puntuajeRiesgo = calcularPuntuajeRiesgo(solicitud);
        boolean aprobado = puntuajeRiesgo >= PUNTUAJE_MINIMO_APROBACION
                && tieneHistorial
                && esCanalConfiable;

        String motivo = aprobado ? "Aprobado" : generarMotivoRechazo(
                esMontoSospechoso,
                tieneDocumentacionCompleta,
                esCanalConfiable,
                tieneHistorial,
                puntuajeRiesgo
        );

        log.debug("Evaluación antifraude completada - aprobado: {}, motivo: {}, puntuaje: {}",
                aprobado, motivo, puntuajeRiesgo);

        return new ResultadoAntifraude(aprobado, motivo, puntuajeRiesgo);
    }

    private int calcularPuntuajeRiesgo(SolicitudPrestamo solicitud) {
        int puntuaje = 100;

        if (solicitud.monto().compareTo(UMBRAL_MONTO_SOSPECHOSO) > 0) {
            puntuaje -= 30;
        }

        if (solicitud.plazoMeses() != null && solicitud.plazoMeses() > 84) {
            puntuaje -= 10;
        }

        if (!esCanalValido(solicitud.canal())) {
            puntuaje -= 25;
        }

        if (solicitud.documentos() == null || solicitud.documentos().isEmpty()) {
            puntuaje -= 20;
        }

        return Math.max(0, Math.min(100, puntuaje));
    }

    private boolean esCanalValido(String canal) {
        if (canal == null || canal.isBlank()) {
            return false;
        }
        String canalLower = canal.toLowerCase();
        return canalLower.equals("web")
                || canalLower.equals("mobile")
                || canalLower.equals("sucursal")
                || canalLower.equals("callcenter");
    }

    private String generarMotivoRechazo(boolean montoSospechoso, boolean documentacionIncompleta,
                                        boolean canalInvalido, boolean sinHistorial, int puntuaje) {
        if (!sinHistorial) {
            return "Sin historial de cliente";
        }
        if (canalInvalido) {
            return "Canal no confiable";
        }
        if (montoSospechoso) {
            return "Monto sospechoso detectado";
        }
        if (!documentacionIncompleta) {
            return "Documentación incompleta";
        }
        if (puntuaje < PUNTUAJE_MINIMO_APROBACION) {
            return "Score de riesgo bajo: " + puntuaje;
        }
        return "Riesgo no determinado";
    }
}

// === ARCHIVO: src/main/java/com/banco/prestamos/infrastructure/adapter/BuroRiesgoAdapter.java ===
package com.banco.prestamos.infrastructure.adapter;

import com.banco.prestamos.domain.port.out.BuroRiesgoPort;
import com.banco.prestamos.infrastructure.exception.BuroRiesgoException;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

@Component
public class BuroRiesgoAdapter implements BuroRiesgoPort {

    private static final Logger log = LoggerFactory.getLogger(BuroRiesgoAdapter.class);
    private static final BigDecimal INGRESO_MINIMO = new BigDecimal("10000");
    private final Random random = new Random();

    @Override
    @Retry(name = "buroRiesgoRetry")
    public ResultadoBuro consultarHistorial(UUID clienteId) {
        log.info("Consultando historial en buró de riesgo para cliente: {}", clienteId);

        try {
            simularLlamadaExterno();
            return construirResultado(clienteId);
        } catch (Exception e) {
            log.error("Error al consultar buró de riesgo para cliente {}: {}", clienteId, e.getMessage());
            throw new BuroRiesgoException("Error al consultar buró de riesgo: " + e.getMessage(), e);
        }
    }

    private void simularLlamadaExterno() {
        boolean debeFallar = random.nextInt(100) < 10;
        if (debeFallar) {
            throw new RuntimeException("Servicio de buró de riesgo no disponible temporalmente");
        }
    }

    private ResultadoBuro construirResultado(UUID clienteId) {
        int hashCode = clienteId.hashCode();
        int scoreBase = Math.abs(hashCode % 850);
        int score = Math.max(300, scoreBase + 300);

        boolean tieneMorosidad = score < 600 && random.nextBoolean();
        boolean tieneHistorial = score >= 500;
        int cantidadCreditos = Math.abs(hashCode % 20);
        int creditosVencidos = tieneMorosidad ? random.nextInt(5) + 1 : 0;
        BigDecimal deudaTotal = new BigDecimal(Math.abs(hashCode % 1000000));
        BigDecimal ingresoMensual = new BigDecimal(Math.abs((hashCode * 2) % 50000) + INGRESO_MINIMO.intValue());
        LocalDate ultimaConsulta = LocalDate.now().minusDays(random.nextInt(90));
        String categoria = determinarCategoria(score);

        return new ResultadoBuro(
                clienteId,
                score,
                tieneMorosidad,
                tieneHistorial,
                cantidadCreditos,
                creditosVencidos,
                deudaTotal,
                ingresoMensual,
                ultimaConsulta,
                categoria
        );
    }

    private String determinarCategoria(int score) {
        if (score >= 800) return "A";
        if (score >= 700) return "B";
        if (score >= 600) return "C";
        if (score >= 500) return "D";
        return "E";
    }
}

// === ARCHIVO: src/main/java/com/banco/prestamos/infrastructure/adapter/CoreBancarioAdapter.java ===
package com.banco.prestamos.infrastructure.adapter;

import com.banco.prestamos.domain.model.RespuestaPrestamo;
import com.banco.prestamos.domain.model.SolicitudPrestamo;
import com.banco.prestamos.domain.port.out.CoreBancarioPort;
import com.banco.prestamos.infrastructure.exception.PrestamoException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Component
public class CoreBancarioAdapter implements CoreBancarioPort {

    private static final Logger logger = LoggerFactory.getLogger(CoreBancarioAdapter.class);
    private static final String SERVICIO_CORE = "coreBancario";

    @Override
    @Retry(name = SERVICIO_CORE)
    @CircuitBreaker(name = SERVICIO_CORE, fallbackMethod = "registrarPrestamoFallback")
    public CompletableFuture<RespuestaPrestamo> registrarPrestamo(SolicitudPrestamo solicitud) {
        logger.info("Registrando préstamo en core bancario para cliente: {}, monto: {}",
                solicitud.clienteId(), solicitud.monto());

        try {
            String numeroOperacion = generarNumeroOperacion();
            BigDecimal tasaInteres = calcularTasaInteres(solicitud.monto(), solicitud.plazoMeses());

            boolean registrado = invocarCoreBancario(solicitud, numeroOperacion, tasaInteres);

            if (registrado) {
                logger.info("Préstamo registrado exitosamente en core: {}", numeroOperacion);
                return CompletableFuture.completedFuture(
                    RespuestaPrestamo.aprobada(numeroOperacion, solicitud.clienteId(), tasaInteres)
                );
            } else {
                logger.warn("Core bancario rechazó el registro del préstamo");
                return CompletableFuture.completedFuture(
                    RespuestaPrestamo.rechazada(numeroOperacion, solicitud.clienteId(), "Rechazo por políticas del core")
                );
            }
        } catch (Exception e) {
            logger.error("Error al registrar préstamo en core bancario: {}", e.getMessage(), e);
            throw new PrestamoException("Error en comunicación con core bancario: " + e.getMessage(), e);
        }
    }

    private String generarNumeroOperacion() {
        String timestamp = String.valueOf(System.currentTimeMillis()).substring(5);
        String uuid = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return String.format("OP%s%s", timestamp, uuid);
    }

    private BigDecimal calcularTasaInteres(BigDecimal monto, Integer plazoMeses) {
        BigDecimal tasaBase = new BigDecimal("0.15");
        BigDecimal ajustePlazo = BigDecimal.ZERO;

        if (plazoMeses > 36) {
            ajustePlazo = new BigDecimal("0.03");
        } else if (plazoMeses > 24) {
            ajustePlazo = new BigDecimal("0.02");
        } else if (plazoMeses > 12) {
            ajustePlazo = new BigDecimal("0.01");
        }

        if (monto.compareTo(new BigDecimal("100000")) > 0) {
            ajustePlazo = ajustePlazo.subtract(new BigDecimal("0.005"));
        }

        return tasaBase.add(ajustePlazo);
    }

    private boolean invocarCoreBancario(SolicitudPrestamo solicitud, String numeroOperacion, BigDecimal tasaInteres) {
        logger.debug("Invocando servicio de core bancario con operación: {}", numeroOperacion);
        return true;
    }

    private CompletableFuture<RespuestaPrestamo> registrarPrestamoFallback(
            SolicitudPrestamo solicitud, Throwable t) {
        logger.warn("Fallback activado para core bancario. Error: {}", t.getMessage());
        String numeroOperacion = "FALLBACK-" + System.currentTimeMillis();
        return CompletableFuture.completedFuture(
            RespuestaPrestamo.error(numeroOperacion, solicitud.clienteId())
        );
    }
}

// === ARCHIVO: src/main/java/com/banco/prestamos/infrastructure/controller/PrestamoController.java ===
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

// === ARCHIVO: src/main/java/com/banco/prestamos/infrastructure/exception/GlobalExceptionHandler.java ===
package com.banco.prestamos.infrastructure.exception;


import com.banco.prestamos.domain.port.out.BuroRiesgoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(PrestamoException.class)
    public ResponseEntity<ErrorResponse> handlePrestamoException(
            PrestamoException ex, WebRequest request) {
        logger.error("PrestamoException: {}", ex.getMessage(), ex);
        ErrorResponse error = new ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Error en el procesamiento del préstamo",
            ex.getMessage(),
            request.getDescription(false).replace("uri=", ""),
            LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(IdempotencyException.class)
    public ResponseEntity<ErrorResponse> handleIdempotencyException(
            IdempotencyException ex, WebRequest request) {
        logger.warn("IdempotencyException: {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse(
            HttpStatus.CONFLICT.value(),
            "Solicitud duplicada detectada",
            ex.getMessage(),
            request.getDescription(false).replace("uri=", ""),
            LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(BuroRiesgoException.class)
    public ResponseEntity<ErrorResponse> handleBuroRiesgoException(
            BuroRiesgoException ex, WebRequest request) {
        logger.error("BuroRiesgoException: {}", ex.getMessage(), ex);
        ErrorResponse error = new ErrorResponse(
            HttpStatus.SERVICE_UNAVAILABLE.value(),
            "Error en el buró de riesgos",
            ex.getMessage(),
            request.getDescription(false).replace("uri=", ""),
            LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex, WebRequest request) {
        logger.warn("ValidationException: {}", ex.getMessage());
        Map<String, String> errors = ex.getBindingResult().getFieldErrors().stream()
            .collect(Collectors.toMap(
                FieldError::getField,
                error -> error.getDefaultMessage() != null ? error.getDefaultMessage() : "Valor inválido",
                (existing, replacement) -> existing
            ));

        ValidationErrorResponse error = new ValidationErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            "Error de validación en los datos de la solicitud",
            errors,
            request.getDescription(false).replace("uri=", ""),
            LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
            IllegalArgumentException ex, WebRequest request) {
        logger.warn("IllegalArgumentException: {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            "Parámetro inválido",
            ex.getMessage(),
            request.getDescription(false).replace("uri=", ""),
            LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(
            Exception ex, WebRequest request) {
        logger.error("Excepción no controlada: {}", ex.getMessage(), ex);
        ErrorResponse error = new ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Error interno del servidor",
            "Ha ocurrido un error inesperado. Por favor, contacte al administrador.",
            request.getDescription(false).replace("uri=", ""),
            LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    public record ErrorResponse(
        int status,
        String error,
        String message,
        String path,
        LocalDateTime timestamp
    ) {}

    public record ValidationErrorResponse(
        int status,
        String error,
        Map<String, String> fieldErrors,
        String path,
        LocalDateTime timestamp
    ) {}
}

// === ARCHIVO: src/main/java/com/banco/prestamos/infrastructure/exception/PrestamoException.java ===
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

// === ARCHIVO: src/main/java/com/banco/prestamos/infrastructure/exception/IdempotencyException.java ===
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

// === ARCHIVO: src/main/java/com/banco/prestamos/infrastructure/exception/BuroRiesgoException.java ===
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

// === ARCHIVO: src/main/java/com/banco/prestamos/infrastructure/config/Resilience4jConfig.java ===
package com.banco.prestamos.infrastructure.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de Resilience4j para el servicio de préstamos.
 * Proporciona circuit breakers y reintentos para las integraciones externas.
 */
@Configuration
public class Resilience4jConfig {

    @Bean
    public CircuitBreakerRegistry circuitBreakerRegistry() {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(java.time.Duration.ofSeconds(30))
                .slidingWindowSize(10)
                .minimumNumberOfCalls(5)
                .permittedNumberOfCallsInHalfOpenState(3)
                .automaticTransitionFromOpenToHalfOpenEnabled(true)
                .build();
        return CircuitBreakerRegistry.of(config);
    }

    @Bean
    public RetryRegistry retryRegistry() {
        RetryConfig config = RetryConfig.custom()
                .maxAttempts(3)
                .waitDuration(java.time.Duration.ofMillis(500))
                .retryExceptions(Exception.class)
                .build();
        return RetryRegistry.of(config);
    }
}

// === ARCHIVO: src/main/java/com/banco/prestamos/infrastructure/config/IdempotencyConfig.java ===
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

// === ARCHIVO: src/test/java/com/banco/prestamos/application/usecase/PrestamoServiceTest.java ===
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

// === ARCHIVO: src/test/java/com/banco/prestamos/infrastructure/adapter/BuroRiesgoAdapterTest.java ===
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

// === ARCHIVO: src/test/java/com/banco/prestamos/infrastructure/controller/PrestamoControllerTest.java ===
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

// === ARCHIVO: README.md ===
# Servicio de Préstamos - Banco

## Descripción

Microservicio de gestión de préstamos bancarios con integración a servicios externos de evaluación de riesgo.

## Arquitectura

El proyecto sigue el patrón de **Arquitectura Hexagonal** (Ports & Adapters) con las siguientes capas:

```
src/main/java/com/banco/prestamos/
├── domain/              # Capa de dominio - núcleo de negocio
│   ├── model/          # Entidades y DTOs del dominio
│   └── port/           # Interfaces (puertos) del dominio
│       ├── in/         # Puertos de entrada (casos de uso)
│       └── out/        # Puertos de salida (integraciones)
├── application/        # Capa de aplicación - casos de uso
│   └── usecase/        # Implementación de casos de uso
├── infrastructure/     # Capa de infraestructura - adaptadores
│   ├── adapter/        # Implementaciones de puertos externos
│   ├── controller/     # Controladores REST
│   ├── config/         # Configuración de componentes
│   └── exception/      # Manejo de excepciones
```

## Requisitos Previos

- **Java 21** - JDK compatible con la versión 21
- **Maven 3.9+** - Gestor de dependencias y build
- **PostgreSQL 15+** - Base de datos relacional

## Configuración

### Variables de Entorno

El proyecto utiliza las siguientes propiedades configurables en `application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/prestamos
    username: postgres
    password: postgres
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: false

servicios:
  antifraude:
    url: http://localhost:8081/api/antifraude
    timeout: 5000
  buroriesgo:
    url: http://localhost:8082/api/buro
    timeout: 10000
  corebancario:
    url: http://localhost:8083/api/core
    timeout: 15000

resilience4j:
  retry:
    instances:
      buroriesgo:
        max-attempts: 3
        wait-duration: 2s
  circuitbreaker:
    instances:
      antifraude:
        sliding-window-size: 10
        failure-rate-threshold: 50
```

## Ejecución del Proyecto

### Compilación

```bash
mvn clean compile
```

### Ejecución

```bash
mvn spring-boot:run
```

### Construcción del JAR

```bash
mvn package -DskipTests
```

El ejecutable se genera en: `target/prestamos-0.0.1-SNAPSHOT.jar`

## Endpoints

### Préstamos

| Método | Path | Descripción |
|--------|------|-------------|
| POST | `/api/prestamos` | Crear nueva solicitud de préstamo |
| GET | `/api/prestamos/{id}` | Consultar estado de solicitud |
| GET | `/api/prestamos/operacion/{numeroOperacion}` | Buscar por número de operación |

### Documentación API

Swagger UI disponible en: `http://localhost:8080/swagger-ui.html`

### Health Check

```
GET /actuator/health
```

## Integraciones Externas

El servicio se integra con tres sistemas externos:

1. **Antifraude** - Evaluación de riesgo de fraude en tiempo real
2. **Buró de Riesgos** - Consulta de historial crediticio del cliente
3. **Core Bancario** - Registro de la operación y reserva de fondos

Cada integración cuenta con:
- Circuit Breaker para tolerancia a fallos
- Reintentos automáticos con estrategia exponencial
- Timeout configurado por servicio

## Validaciones

- El monto mínimo de préstamo es de $10,000 MXN
- El monto máximo es de $5,000,000 MXN
- El plazo mínimo es de 6 meses y máximo de 60 meses
- La tasa de interés se calcula automáticamente según el perfil del cliente

## Pruebas

### Ejecución de Tests Unitarios

```bash
mvn test
```

### Cobertura

```bash
mvn test jacoco:report
```

El reporte se genera en: `target/site/jacoco/index.html`

## Métricas y Observabilidad

- **Actuator** expone métricas de JVM, HTTP y servicios
- **Resilience4j** provee métricas de circuit breaker y reintentos
- Logs estructurados en formato JSON para integración con agregadores

## Perfiles de Spring

| Perfil | Uso |
|--------|-----|
| dev | Desarrollo local con H2 en memoria |
| prod | Producción con PostgreSQL y configuraciones optimizadas |
| test | Ejecución de pruebas unitarias |

## Licencia

Proprietario - Banco
```
