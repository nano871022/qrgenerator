
# Custom QR Code Generator (`co.com.japl.dev.qr.generator`)

Un generador de códigos QR robusto, modular y altamente personalizable desarrollado en Java. Este proyecto ha sido diseñado bajo los principios **SOLID**, **Clean Code** y manteniendo un enfoque **KISS** (Keep It Simple, Stupid) para garantizar una arquitectura limpia, extensible y fácil de mantener.

El repositorio integra una estrategia moderna de **Desarrollo Guiado por Especificaciones (Spec-Driven Design)**, centralizando las directrices de diseño arquitectónico y automatización en la carpeta `ai/`.

## 🚀 Características Principales

- **Generación Base de Códigos QR:** Soporte completo para codificación estándar de texto y URLs con control estricto de los niveles de corrección de errores.
- **Superposición de Logotipos:** Capacidad de integrar logotipos personalizados en el centro del código QR sin comprometer la legibilidad ni la redundancia de datos.
- **Mapeo Artístico de Imágenes:** Pipeline de procesamiento cromático avanzado que permite transformar imágenes de referencia en patrones de píxeles estilizados dentro del código QR (arte generativo).
- **Renderizado Flexible (`ModuleDrawer`):** Abstracción completa del flujo de dibujo de módulos para permitir estilos personalizados (bordes redondeados, degradados, formas personalizadas).
- **Arquitectura Extensible:** Uso intensivo del patrón *Strategy* para desacoplar las reglas de diseño estético del motor de generación del código QR.

## 📂 Estructura del Proyecto

```text
qrgenerator/
├── ai/                              # Especificaciones y directrices para Agentes de IA
│   ├── design_specification.md      # Design Specification (Detalles técnicos y contratos)
│   ├── architecture_document.md     # Architecture Document (Diseño modular y Clean Code)
│   └── work_plan_test_matrix.md     # Work Plan with Test Matrix (Fases y matriz de pruebas)
├── src/
│   └── main/
│       └── java/
│           └── co/com/japl/dev/qr/generator/
│               ├── core/             # Motor de codificación y lógica base del QR
│               ├── drawer/           # Componentes y contratos de renderizado (ModuleDrawer)
│               └── strategy/         # Estrategias de personalización cromática y visual
├── pom.xml
└── README.md

```
## 🤖 Gestión del Desarrollo Basado en IA (ai/)
La carpeta ai/ actúa como el núcleo de gobernanza técnica para el desarrollo asistido por Inteligencia Artificial. Los archivos están completamente redactados en inglés técnico y optimizados en formato Markdown para ser consumidos directamente por frameworks de orquestación de agentes autónomos o asistentes de código en tareas paralelas:
 * **design_specification.md:** Detalla los contratos de las interfaces, definición de modelos de datos, excepciones y flujos de datos síncronos del pipeline.
 * **architecture_document.md:** Estructura las capas del sistema y el desacoplamiento mediante patrones de diseño para asegurar la mantenibilidad.
 * **work_plan_test_matrix.md:** Modula el plan de trabajo estructurado por fases junto con la matriz de pruebas unitarias y de integración para desarrollo concurrente por agentes.
## 🛠️ Requisitos Técnicos
 * **Java SE Development Kit (JDK):** Versión 17 o superior.
 * **Herramienta de Construcción:** Maven (o Gradle).
## ⚙️ Instalación y Compilación
Para clonar el repositorio y compilar el componente localmente, ejecuta:
```bash
git clone [https://github.com/nano871022/qrgenerator.git](https://github.com/nano871022/qrgenerator.git)
cd qrgenerator
mvn clean install

```
## 📝 Ejemplo de Uso (KISS Approach)
El diseño de la API permite un uso directo mediante un patrón Constructor (*Builder*), delegando el estilo visual a la estrategia seleccionada:
```java
import co.com.japl.dev.qr.generator.QrGenerator;
import co.com.japl.dev.qr.generator.strategy.impl.ArtisticMappingStrategy;

public class Main {
    public static void main(String[] args) {
        QrGenerator generator = new QrGenerator();
        
        // Generación ágil con mapeo cromático personalizado
        generator.createBuilder()
                 .withContent("[https://github.com/nano871022/qrgenerator](https://github.com/nano871022/qrgenerator)")
                 .withStrategy(new ArtisticMappingStrategy("/path/to/source_image.png"))
                 .generate("/path/to/output_artistic_qr.png");
    }
}

```
## 📄 Licencia
Este proyecto es propiedad de **JAPL**. Todos los derechos reservados.
```

