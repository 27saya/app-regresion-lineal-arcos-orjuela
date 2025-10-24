# Guía de Usuario - Aplicación Web para Regresión Lineal en Kotlin

## Descripción General

Esta aplicación web desarrollada en **Kotlin con Spring Boot** permite calcular y visualizar regresiones lineales de manera intuitiva. El usuario puede ingresar puntos de datos (x,y) y obtener inmediatamente la ecuación de la línea de regresión, métricas estadísticas y una representación gráfica interactiva.

## Funcionalidad de la Aplicación

La aplicación ofrece las siguientes características principales:

- **Entrada de Datos Flexible**: Acepta múltiples formatos de entrada (separados por comas, espacios o saltos de línea)
- **Cálculo Automático**: Procesa los datos y calcula la regresión lineal usando el método de mínimos cuadrados
- **Visualización Gráfica**: Genera automáticamente un gráfico de dispersión con la línea de regresión superpuesta
- **Métricas Estadísticas**: Proporciona la ecuación, pendiente, intersección y coeficiente de determinación (R²)
- **Interpretación Automática**: Genera una explicación textual del análisis estadístico
- **Validación de Datos**: Verifica la integridad de los datos ingresados y maneja errores apropiadamente

## Justificación de la Arquitectura Elegida

### Patrón MVC (Model-View-Controller)
Se eligió el patrón **MVC** por las siguientes razones fundamentales:

1. **Separación de Responsabilidades**: Cada capa tiene una función específica y bien definida
2. **Mantenibilidad**: Cambios en la lógica de negocio no afectan la presentación y viceversa
3. **Escalabilidad**: Permite agregar nuevas funcionalidades sin comprometer el código existente
4. **Testabilidad**: Facilita las pruebas unitarias al aislar componentes específicos

### Spring Boot Framework
La elección de **Spring Boot** sobre otras alternativas se justifica por:

- **Configuración Automática**: Reduce significativamente el código boilerplate
- **Inyección de Dependencias**: Gestión automática del ciclo de vida de los componentes
- **Ecosistema Robusto**: Amplia gama de librerías y herramientas integradas
- **Facilidad de Despliegue**: Servidor embebido Tomcat incluido

### Arquitectura de Capas vs. Microservicios
Para este caso específico, se prefirió una **arquitectura monolítica en capas** sobre microservicios porque:

- **Simplicidad**: Una sola aplicación es más fácil de desarrollar, probar y desplegar
- **Latencia Mínima**: No hay overhead de comunicación entre servicios
- **Consistencia de Datos**: Las transacciones son más sencillas de manejar
- **Recursos Limitados**: No requiere la complejidad de orquestación de múltiples servicios

## Explicación de Archivos del Código

### Backend (Kotlin)

#### `GeneradorRegresionAplicacion.kt` (Main Application)
```kotlin
@SpringBootApplication
class GeneradorRegresionApplication
```
**Función**: Punto de entrada principal de la aplicación Spring Boot. Configura y arranca el contexto de la aplicación.

#### `RegressionController.kt` (Controller Layer)
**Función**: Maneja las solicitudes HTTP y coordina la comunicación entre el frontend y el servicio.
- `@GetMapping("/")`: Sirve la página principal
- `@PostMapping("/calculate")`: Procesa los datos de regresión y retorna JSON

#### `LinearRegressionService.kt` (Service Layer)
**Función**: Contiene toda la lógica de negocio para el cálculo de regresión lineal.
- `calculateLinearRegression()`: Implementa el algoritmo de mínimos cuadrados
- `parseDataPoints()`: Procesa y valida los datos de entrada en múltiples formatos

#### `RegressionRequest.kt` (Model)
**Función**: DTO (Data Transfer Object) que encapsula los datos enviados desde el frontend.
```kotlin
data class RegressionRequest(
    @field:NotBlank(message = "Los datos no pueden estar vacíos")
    val dataPoints: String = ""
)
```

#### `RegressionResult.kt` (Model)
**Función**: Encapsula todos los resultados del cálculo de regresión para su serialización a JSON.
- Incluye: pendiente, intersección, ecuación, puntos originales, línea de regresión y R²

#### `DataPoint.kt` (Model)
**Función**: Representa un punto de datos (x, y) en el sistema.
```kotlin
data class DataPoint(val x: Double, val y: Double)
```

### Frontend

#### `index.html` (View)
**Función**: Template Thymeleaf que define la estructura HTML de la interfaz de usuario.
- Formulario para entrada de datos
- Secciones para mostrar resultados y gráficos
- Integración con Chart.js para visualización

#### `chart-handler.js` (Client Logic)
**Función**: Maneja toda la lógica del frontend y la comunicación con el backend.
- Envío asíncrono de datos via AJAX
- Renderización de gráficos con Chart.js
- Gestión de eventos de usuario (botones, validaciones)
- Generación automática de interpretaciones

#### `style.css` (Presentation)
**Función**: Estilos CSS que definen la apariencia visual de la aplicación.
- Tema oscuro moderno y responsive
- Animaciones y transiciones suaves
- Diseño adaptable para dispositivos móviles

### Configuración

#### `build.gradle.kts` (Build Configuration)
**Función**: Define las dependencias del proyecto y configuración de compilación.
- Spring Boot Web, Thymeleaf, Validation
- Kotlin específico y Jackson para JSON

#### `settings.gradle.kts` (Project Settings)
**Función**: Configuración básica del proyecto Gradle.

## Guía de Ejecución en IntelliJ IDEA

### Prerrequisitos
- **JDK 17** o superior instalado
- **IntelliJ IDEA** (Community o Ultimate Edition)
- **Kotlin Plugin** habilitado

### Pasos de Ejecución

1. **Importar el Proyecto**
   ```
   File → Open → Seleccionar la carpeta del proyecto
   ```

2. **Sincronizar Dependencias**
   ```
   Gradle tool window → Refresh Gradle project
   ```

3. **Configurar el JDK**
   ```
   File → Project Structure → Project → SDK: Java 17
   ```

4. **Ejecutar la Aplicación**
   - **Opción 1**: Ejecutar desde la clase principal
     ```
     Abrir GeneradorRegresionAplicacion.kt → Click en el ícono de play verde
     ```
   
   - **Opción 2**: Usar Gradle
     ```
     Terminal: ./gradlew bootRun
     ```

5. **Acceder a la Aplicación**
   ```
   Navegador: http://localhost:8080
   ```

### Configuración de Hot Reload (Opcional)
Para desarrollo más eficiente, Spring Boot DevTools está incluido:
```kotlin
developmentOnly("org.springframework.boot:spring-boot-devtools")
```

## Algoritmo y Fórmulas Matemáticas

### Método de Mínimos Cuadrados

La aplicación utiliza el **método de mínimos cuadrados ordinarios** para encontrar la línea que mejor se ajusta a los datos.

#### Fórmulas Implementadas

**1. Pendiente (m)**
```
m = (n∑xy - ∑x∑y) / (n∑x² - (∑x)²)
```

**2. Intersección (b)**
```
b = (∑y - m∑x) / n
```

**3. Ecuación de la Línea**
```
y = mx + b
```

**4. Coeficiente de Determinación (R²)**
```
R² = 1 - (SSres / SStot)

Donde:
SSres = ∑(yi - ŷi)²  (Suma de cuadrados residuales)
SStot = ∑(yi - ȳ)²   (Suma total de cuadrados)
```

#### Variables del Algoritmo
- `n`: Número de puntos de datos
- `∑x`: Suma de todos los valores x
- `∑y`: Suma de todos los valores y
- `∑xy`: Suma de los productos x·y
- `∑x²`: Suma de los cuadrados de x
- `ȳ`: Media de los valores y

### Implementación en Código

```kotlin
val n = dataPoints.size.toDouble()
val sumX = dataPoints.sumOf { it.x }
val sumY = dataPoints.sumOf { it.y }
val sumXY = dataPoints.sumOf { it.x * it.y }
val sumX2 = dataPoints.sumOf { it.x.pow(2) }

val slope = (n * sumXY - sumX * sumY) / (n * sumX2 - sumX.pow(2))
val intercept = (sumY - slope * sumX) / n
```

## Cómo Usar la Aplicación

### Ingreso de Datos

1. **Formatos Soportados**:
   - Separados por comas: `1,2`
   - Separados por espacios: `1 2`
   - Una línea por punto: 
     ```
     1,2
     2,4
     3,6
     ```

2. **Ejemplo de Datos Válidos**:
   ```
   1,2
   2,4
   3,6
   4,8
   5,10
   ```

### Interpretación de Resultados

#### Métricas Principales
- **Ecuación**: Fórmula matemática de la línea de regresión
- **Pendiente (m)**: Indica la inclinación de la línea
  - Positiva: relación creciente
  - Negativa: relación decreciente
- **Intersección (b)**: Punto donde la línea cruza el eje Y
- **R²**: Calidad del ajuste (0 a 1)
  - 0.9-1.0: Ajuste muy fuerte
  - 0.7-0.9: Ajuste moderado
  - 0.0-0.7: Ajuste débil

#### Gráfico Interactivo
- **Puntos blancos**: Datos originales ingresados
- **Línea gris**: Línea de regresión calculada
- **Ejes etiquetados**: X e Y con valores automáticos

## Características Técnicas

### Tecnologías Utilizadas
- **Backend**: Kotlin 1.9.21, Spring Boot 3.2.1
- **Frontend**: HTML5, CSS3, JavaScript ES6+, Chart.js 4.5.0
- **Plantillas**: Thymeleaf
- **Validación**: Jakarta Validation
- **Serialización**: Jackson Kotlin Module
- **Build Tool**: Gradle 8.x con Kotlin DSL

### Características de Rendimiento
- **Complejidad Temporal**: O(n) para n puntos de datos
- **Memoria**: Almacenamiento temporal de datos en memoria
- **Concurrencia**: Manejo de múltiples usuarios mediante Spring Boot
- **Validación**: Validación tanto del lado cliente como servidor

### Seguridad y Robustez
- **Validación de Entrada**: Verificación de formato y tipos de datos
- **Manejo de Errores**: Respuestas JSON estructuradas para errores
- **Sanitización**: Prevención de inyección de código malicioso
- **Límites**: Requiere mínimo 2 puntos de datos para cálculos válidos

### Responsividad
- **Diseño Adaptable**: CSS responsive para dispositivos móviles
- **Interfaz Táctil**: Botones y controles optimizados para touch
- **Carga Rápida**: Recursos minificados y optimizados

---

**Versión de la Aplicación**: 1.0.0  
**Compatibilidad**: Java 17+, Navegadores modernos (Chrome, Firefox, Safari, Edge)