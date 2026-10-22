# Refactorización de servicio de préstamos

El servicio de préstamos de un banco necesita ser refactorizado para aplicar principios de código limpio y eficiente. El servicio interactúa con el motor antifraude, el buró de riesgos y el core bancario. Debe manejar volúmenes de hasta 1 500 solicitudes por segundo en hora pico y garantizar idempotencia de las solicitudes por número de operación y canal. El sistema debe mantener consistencia entre el registro de la solicitud y la respuesta del core, y tolerar fallos temporales del buró con una estrategia de reintento.

## Informacion General

| Campo | Valor |
|-------|-------|
| **Tema** | aplicación de código limpio y eficiente |
| **Nivel** | advanced-l2 |
| **Tipo** | practical |
| **Tiempo estimado** | 1 semana |

## Fases del Reto

### Fase 0: Configuración del Proyecto

**Objetivo:** Obtener el proyecto base funcional enviando el Código Base a un asistente de IA, que lo analizará, corregirá errores y generará un ZIP listo para usar.

**Tiempo estimado:** 15-30 minutos

**Instrucciones:**

- Asegúrate de tener instalado para ejecutar el proyecto: JDK 17+, Maven 3.9+, IDE con soporte Java.
- Copia todo el contenido del campo **Código Base** de este reto — incluyendo el texto de instrucciones que aparece al inicio.
- Abre un asistente de IA (Claude en claude.ai, ChatGPT o Gemini — se recomienda Claude), pega el contenido copiado en el chat y envíalo.
- El asistente analizará los archivos, corregirá errores y generará un archivo ZIP descargable. Descárgalo y extráelo en la carpeta donde quieras trabajar.
- Ejecuta `mvn compile` en la raíz. Si no hay errores, estás listo.

**Entregable:** El proyecto compila/arranca sin errores.

<details>
<summary>Pistas de conocimiento</summary>

- Copia el Código Base completo incluyendo el texto de instrucciones al inicio — esas instrucciones le indican al asistente exactamente qué hacer con los archivos.
- Si el asistente no genera el ZIP automáticamente al terminar el análisis, escríbele: "genera el ZIP ahora".
- Si el proyecto tiene errores al arrancar, comparte el mensaje de error con el mismo asistente para que lo corrija.

</details>

### Fase 1: Análisis de código existente

**Objetivo:** identificar áreas de mejora en términos de código limpio y eficiencia

**Tiempo estimado:** 2 días

**Instrucciones:**

- revisar el código fuente del servicio de préstamos
- identificar violaciones de los principios KISS, SOLID, YAGNI y DRY
- documentar hallazgos y proponer cambios

**Entregable:** documento con hallazgos y propuestas de refactorización

<details>
<summary>Pistas de conocimiento</summary>

- principios de código limpio
- patrones de diseño comunes en Java

</details>

### Fase 2: Refactorización del código

**Objetivo:** aplicar cambios propuestos para mejorar el código

**Tiempo estimado:** 3 días

**Instrucciones:**

- implementar los cambios propuestos en la fase anterior
- asegurar que el código refactorizado sigue los principios KISS, SOLID, YAGNI y DRY
- realizar pruebas unitarias para validar el código refactorizado

**Entregable:** código refactorizado con pruebas unitarias

<details>
<summary>Pistas de conocimiento</summary>

- herramientas de refactorización en IDE
- patrones de diseño en Java

</details>

### Fase 3: Evaluación del código refactorizado

**Objetivo:** evaluar la calidad del código refactorizado

**Tiempo estimado:** 2 días

**Instrucciones:**

- revisar el código refactorizado para asegurar que sigue los principios de código limpio y eficiente
- evaluar la cobertura de pruebas y la calidad del código
- documentar hallazgos y proponer mejoras adicionales si es necesario

**Entregable:** documento de evaluación del código refactorizado

<details>
<summary>Pistas de conocimiento</summary>

- métricas de calidad de código
- herramientas de análisis estático de código

</details>

## Dimensiones Evaluadas

- **queEs**: ¿qué son los principios KISS, SOLID, YAGNI y DRY y por qué son importantes en el desarrollo de software?
- **paraQueSirve**: ¿para qué sirve aplicar estos principios en el código del servicio de préstamos?
- **comoSeUsa**: ¿cómo se aplican estos principios en la refactorización del código?
- **erroresComunes**: ¿cuáles son los errores comunes al aplicar estos principios y cómo se pueden evitar?
- **queDecisionesImplica**: ¿qué decisiones implica la aplicación de estos principios en el código del servicio de préstamos?

## Criterios de Evaluacion

- identificación correcta de áreas de mejora en el código existente
- aplicación efectiva de los principios de código limpio y eficiente en la refactorización
- evaluación adecuada de la calidad del código refactorizado

## Como trabajar con un asistente de IA

Hay dos caminos, elegi uno:

- **AGENTS.md** (recomendado) — instrucciones nativas del repo. Abri esta carpeta con tu agente local (Claude Code, Cursor, Codex, Copilot, Gemini) y las carga solo. Sabe que archivos faltan y con que comando se verifica, y completa el scaffold escribiendo en disco.
- **PROMPT_MEJORA.md** — para copiar y pegar en un chat (claude.ai, ChatGPT). Devuelve un ZIP con el proyecto. Sirve si no tenes un agente en el IDE.

Ninguno de los dos resuelve las fases del reto: eso es tu trabajo.

## Verificacion

El proyecto esta listo para trabajar cuando este comando corre sin errores:

```bash
mvn clean compile
```

---

*Reto generado automaticamente por Challenge Generator - Pragma*
