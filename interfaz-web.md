# 🌐 Interfaz Web Local (Spring Boot)

Además de funcionar como una librería acoplable en otros proyectos, **qrgenerator** incluye una aplicación web completamente **autocontenida** impulsada por **Spring Boot**. Esto significa que puedes levantar un servidor local en tu máquina para generar, personalizar y descargar tus códigos QR de forma visual e interactiva a través del navegador.

---

## 🚀 Cómo Ejecutar el Proyecto Localmente

Para arrancar el servidor web incorporado, abre tu terminal en la raíz del proyecto y ejecuta el comando correspondiente según el gestor de dependencias que estés utilizando:

### Opción A: Si usas Maven
```bash
./mvnw spring-boot:run

```

### Opción B: Si usas Gradle

```bash
./gradlew bootRun

```

### Opción C: Ejecutando el archivo JAR compilado

Si ya has compilado el proyecto previamente (`clean package` o `clean build`), puedes ejecutar directamente el binario:

```bash
java -jar target/qrgenerator-1.0.0.jar
# o build/libs/qrgenerator-1.0.0.jar según corresponda

```

---

## 💻 Acceso a la Aplicación

Una vez que la consola indique que Spring Boot ha iniciado correctamente (usualmente toma un par de segundos), abre tu navegador web preferido e ingresa a la siguiente dirección:

> 🔗 **[http://localhost:8080](https://www.google.com/search?q=http://localhost:8080)**

---

## 🎛️ Características de la Interfaz Visual

Al ser un reflejo exacto de las capacidades de la librería bajo el enfoque de **Spec-Driven Design**, desde este panel local podrás:

* 📝 **Entrada de Datos:** Escribir cualquier texto, cadena de configuración o URL en tiempo real.
* 🎨 **Selector de Colores:** Cambiar de forma interactiva el color de fondo y el color de los módulos utilizando paletas visuales.
* 📐 **Personalización Geométrica:** Seleccionar mediante menús desplegables las formas de los módulos (cuadrados, círculos, bordes redondeados) y de los ojos de las esquinas.
* 🖼️ **Carga de Logos (Branding):** Subir una imagen desde tu computadora para incrustarla en el centro del QR, aplicando automáticamente el nivel de redundancia seguro (`ErrorCorrectionLevel.HIGH`).
* 📥 **Descarga Instantánea:** Previsualizar el resultado final en pantalla y descargarlo directamente en formato de imagen (`.png`) listo para usar.

---

## 🛠️ ¿Deseas cambiar el puerto por defecto?

Si el puerto `8080` ya está siendo utilizado por otra aplicación en tu sistema, puedes cambiarlo fácilmente al lanzar la aplicación desde la terminal añadiendo el siguiente parámetro:

```bash
# Ejemplo en Maven
./mvnw spring-boot:run -Dspring-boot.run.arguments=--server.port=9090

# Ejemplo con el JAR ejecutable
java -jar qrgenerator.jar --server.port=9090

```

*(Luego de esto, podrás acceder desde `http://localhost:9090`)*

```

```
