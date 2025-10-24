# Guía de usuario - Aplicación web para regresión lineal en Kotlin

## Descripción general

Esta aplicación web desarrollada en kotlin con spring boot permite calcular y visualizar regresiones lineales de manera intuitiva. El usuario puede ingresar puntos de datos (x,y) y obtener la ecuación de la línea de regresión, métricas estadísticas y una representación gráfica interactiva.

## Funcionalidad de la aplicación

La aplicación ofrece las siguientes características principales:

- **Entrada de datos flexible**: Acepta múltiples formatos de entrada (separados por comas, espacios o saltos de línea)
- **Cálculo automático**: Procesa los datos y calcula la regresión lineal usando el método de mínimos cuadrados
- **Visualización gráfica**: Genera automáticamente un gráfico de dispersión con la línea de regresión superpuesta
- **Métricas estadísticas**: Proporciona la ecuación, pendiente, intersección y coeficiente de determinación (R²)
- **Interpretación automática**: Genera una explicación textual del análisis estadístico
- **Validación de datos**: Verifica la integridad de los datos ingresados y maneja errores apropiadamente

## Justificación de la arquitectura elegida

### Patrón MVC (Modelo vista-controlador)
Se eligió el patrón MVC por las siguientes razones:

1. **Separación de responsabilidades**: Cada capa tiene una función específica y bien definida
2. **Mantenibilidad**: Cambios en la lógica no afectan la presentación y viceversa
3. **Escalabilidad**: Permite agregar nuevas funcionalidades sin ocasionar problemas al código ya existente
4. **Testabilidad**: Facilita las pruebas unitarias al aislar componentes específicos

### Sobre spring boot
Se eligio sobre otras alternativas por su:

- **Configuración automática**: Reduce significativamente el código que se repite frecuentemente con pocos o ningún cambio, y que se usa como punto de partida para el proyecto (boilerplate)
- **Inyección de dependencias**: Gestión automática del ciclo de vida de los componentes
- **Ecosistema robusto**: Amplia gama de librerías y herramientas integradas
- **Facilidad de despliegue**: Servidor embebido Tomcat incluido (el motor web integrado que escucha y responde a las solicitudes HTTP para la aplicación con spring boot sin tener que manejar un servidor web separado)

### Arquitectura de capas vs. microservicios
Para este caso específico, se prefirió una arquitectura de capas ya que:

- **Simplicidad**: Se tiene un único archivo principal (main) que ejecuta toda la aplicación como una sola unidad. Todo el código, incluyendo la interfaz, lógica de negocio y acceso a datos, se compila y despliega junto, funcionando como un único programa grande.
- **Latencia mínima**: A diferencia de la arquitectura de microservicios, en la de capas no hay una carga o costo adicional que implique manejar ciertos aspectos técnicos en la arquitectura del software que no contribuya directamente a la funcionalidad principal (overhead). 
- **Consistencia de datos**: Las operaciones o cambios de datos son más sencillos de manejar
- **Recursos limitados**: No requiere la complejidad de manejar múltiples servicios a la vez

## Explicación de archivos del código

### Backend (Kotlin)

#### `GeneradorRegresionAplicacion.kt` (Aplicacion principal)
```kotlin
@SpringBootApplication
class GeneradorRegresionApplication
```
**Función**: Punto de entrada principal de la aplicación. Configura y arranca el contexto de la aplicación.

#### `RegressionController.kt` (Capa controlador)
**Función**: Maneja las solicitudes HTTP y coordina la comunicación entre el frontend y el servicio.
- `@GetMapping("/")`: Sirve la página principal
- `@PostMapping("/calculate")`: Procesa los datos de regresión y retorna JSON

#### `LinearRegressionService.kt` (Capa de servicio)
**Función**: Contiene todas las reglas, procesos y decisiones que definen cómo funciona realmente la aplicación para el cálculo de la regresión lineal.
- `calculateLinearRegression()`: Implementa el algoritmo de mínimos cuadrados
- `parseDataPoints()`: Procesa y valida los datos de entrada en múltiples formatos

#### `RegressionRequest.kt` (Model)
**Función**: DTO (Data Transfer Object, mueve los datos entre las capas o entre un cliente y un servidor de forma segura sin incluir la logica de negocio) que encapsula los datos enviados desde el frontend.
```kotlin
data class RegressionRequest(
    @field:NotBlank(message = "Los datos no pueden estar vacíos")
    val dataPoints: String = ""
)
```

#### `RegressionResult.kt` (Model)
**Función**: Encapsula todos los resultados del cálculo de regresión para su serialización a JSON. (formato estándar para la transferencia de datos entre el frontend (interfaz de usuario) y el backend (servidor)
- Incluye: pendiente, intersección, ecuación, puntos originales, línea de regresión y R²

#### `DataPoint.kt` (Model)
**Función**: Representa un punto de datos (x, y) en el sistema.
```kotlin
data class DataPoint(val x: Double, val y: Double)
```

### Frontend

#### `index.html` (Vista)
**Función**: Plantilla Thymeleaf que define la estructura HTML de la interfaz de usuario.
- Formulario para entrada de datos
- Secciones para mostrar resultados y gráficos
- Integración con Chart.js para visualización

#### `chart-handler.js` (Logica de cliente)
**Función**: Maneja toda la lógica del frontend y la comunicación con el backend.
- Envío asíncrono de datos via AJAX (técnica de desarrollo que permite que una página web se comunique con el servidor y actualice partes de su contenido sin tener que recargar toda la página completa. En este caso se usa para enviar los puntos de datos ingresados en el formulario al servidor de forma sincronizada, recibir la respuesta con los resultados de la regresión (en formato JSON), y actualizar los resultados y el gráfico en la página web sin interrupciones ni vuelta a cargar.
- Renderización de gráficos con Chart.js
- Gestión de eventos de usuario (botones, validaciones)
- Generación automática de interpretaciones

#### `style.css` (Presentación)
**Función**: Estilos CSS que definen la apariencia visual de la aplicación.
- Tema oscuro moderno y responsive
- Animaciones y transiciones suaves
- Diseño adaptable para dispositivos móviles

### Configuración

#### `build.gradle.kts` (Configuración)
**Función**: Define las dependencias del proyecto y configuración de compilación.
- Spring boot web, Thymeleaf, Validation
- Kotlin específico y Jackson para JSON

#### `settings.gradle.kts` (Settings del proyecto)
**Función**: Configuración básica del proyecto Gradle.

## Guía de Ejecución en IntelliJ IDEA

### Prerrequisitos
- JDK 17 o superior instalado
- IntelliJ IDEA (Community o Ultimate Edition)
- Kotlin Plugin habilitado

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

## Algoritmo y fórmulas matemáticas

### Método de mínimos cuadrados

La aplicación utiliza el método de mínimos cuadrados ordinarios, el cual consiste en encontrar estadisticamente la línea recta que mejor se ajusta a un conjunto de puntos en un plano, es decir, para realizar una regresión lineal. En la aplicación, se tiene una sola relación entre dos variables y los datos, los cuales vienen en forma de (x, y), este metodo asegura que la recta ajustada explica de la mejor forma la tendencia del conjunto entero de datos.

#### Fórmulas implementadas

**1. Pendiente (m)**
```
m = (n∑xy - ∑x∑y) / (n∑x² - (∑x)²)
```

**2. Intersección (b)**
```
b = (∑y - m∑x) / n
```

**3. Ecuación de la línea**
```
y = mx + b
```

**4. Coeficiente de determinación (R²)**
```
R² = 1 - (SSres / SStot)

Donde:
SSres = ∑(yi - ŷi)²  (Suma de cuadrados residuales)
SStot = ∑(yi - ȳ)²   (Suma total de cuadrados)
```

#### Variables del algoritmo
- `n`: Número de puntos de datos
- `∑x`: Suma de todos los valores x
- `∑y`: Suma de todos los valores y
- `∑xy`: Suma de los productos x·y
- `∑x²`: Suma de los cuadrados de x
- `ȳ`: Media de los valores y

### Implementación en el código

```kotlin
val n = dataPoints.size.toDouble()
val sumX = dataPoints.sumOf { it.x }
val sumY = dataPoints.sumOf { it.y }
val sumXY = dataPoints.sumOf { it.x * it.y }
val sumX2 = dataPoints.sumOf { it.x.pow(2) }

val slope = (n * sumXY - sumX * sumY) / (n * sumX2 - sumX.pow(2))
val intercept = (sumY - slope * sumX) / n
```

## Cómo Usar la aplicación

### Ingreso de Datos

1. **Formatos soportados**:
   - Separados por comas: `1,2`
   - Separados por espacios: `1 2`
   - Una línea por punto: 
     ```
     1,2
     2,4
     3,6
     ```

2. **Ejemplo de datos válidos, importable en la misma aplicación**:
   ```
   1,2
   2,4
   3,6
   4,8
   5,10
   ```

### Interpretación de resultados

#### Métricas principales
- **Ecuación**: Fórmula matemática de la línea de regresión
- **Pendiente (m)**: Indica la inclinación de la línea
  - Positiva: relación creciente
  - Negativa: relación decreciente
- **Intersección (b)**: Punto donde la línea cruza el eje Y
- **R²**: Calidad del ajuste (0 a 1)
  - 0.9-1.0: Ajuste muy fuerte
  - 0.7-0.9: Ajuste moderado
  - 0.0-0.7: Ajuste débil

#### Gráfico interactivo
- **Puntos blancos**: Datos originales ingresados
- **Línea gris**: Línea de regresión calculada
- **Ejes etiquetados**: X e Y con valores automáticos

## Características técnicas

### Tecnologías utilizadas
- **Backend**: Kotlin 1.9.21, Spring Boot 3.2.1
- **Frontend**: HTML5, CSS3, JavaScript ES6+, Chart.js 4.5.0
- **Plantillas**: Thymeleaf
- **Validación**: Jakarta Validation
- **Serialización**: Jackson Kotlin Module
- **Build Tool**: Gradle 8.x con Kotlin DSL

### Seguridad
- **Validación de entrada**: Verificación de formato y tipos de datos
- **Manejo de errores**: Respuestas JSON estructuradas para errores
- **Límites**: Requiere mínimo 2 puntos de datos para cálculos válidos

### Responsividad
- **Diseño adaptable**: CSS responsive para dispositivos móviles
- **Interfaz táctil**: Botones y controles optimizados para touch
- **Carga rápida**: Recursos minificados y optimizados
