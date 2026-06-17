# Spec-Driven Design (SDD) Document
## Java Custom QR Code Generator Library (SOLID & Art Edition)
This document specifies the technical architecture, public API, functional requirements, and execution strategies for the high-performance, customizable QR code generator library developed under the root package namespace co.com.japl.dev.qr.generator.
## 1. Project Objectives & Scope
### 1.1. Core Objective
To provide Java developers with a highly modular, lightweight, extensible, and fluent API to generate visually outstanding QR codes. This library supports deep branding personalization through advanced geometries, colors, gradient styling, central logo integration, and image-based pixel color mapping (halftone/mosaic effects).
### 1.2. Out of Scope
 * QR Code scanning, decoding, or reading.
 * Cloud-based dynamic QR management (the library acts purely as an on-premise/embedded generation utility).
 * Direct generation of high-complexity vector formats beyond standard PNG and basic SVG wrappers.
## 2. Technical Prerequisites & Dependencies
 * **Java Version:** Java 17 or higher (leveraging modern rendering capabilities, record types, and pattern matching).
 * **Core QR Engine:** ZXing (Zebra Crossing) wrapped through a modular abstraction to isolate the binary matrix generation.
 * **Output Mediums:** java.awt.image.BufferedImage, byte[] streams, and local java.io.File bindings.
## 3. High-Value Artistic Features
### 3.1. Overlay Logo Centerpiece
Enables embedding a crisp brand logo at the exact geometric center of the QR code. The system dynamically computes an inner "Quiet Zone" (excluding data modules beneath the logo) to prevent visual overlap and scan degradation, strictly limiting the logo size to a safe threshold (maximum 30% of the QR's total area) aligned with QR Error Correction Level H.
### 3.2. Image Pixel Color Mapping (Halftone / Mosaic QR)
Allows developers to supply a template background image. The rendering engine samples the color at the physical coordinate of each active QR module and applies that specific RGB color to the drawn module. This generates a pixelated/mosaic representation of the template image within the QR pattern itself, maintaining high code scannability while delivering an artistic visual.
### 3.3. Hybrid Processing Mode
The system supports the simultaneous execution of both features: rendering the data grid using sampled pixel colors from a template image, while superimposing a high-contrast corporate logo onto a protected, solid-colored central plate.
## 4. Package and Class Topography
The codebase is strictly organized to prevent monolithic coupling, splitting data logic from graphic execution:
```text
co.com.japl.dev.qr.generator
│
├── CustomQrGenerator.java          <-- Primary Facade & Fluent Builder
│
├── api
│   └── MatrixGenerator.java        <-- Matrix generation abstraction interface
│
├── model
│   └── QrConfig.java               <-- Immutable Value Object for aesthetic options
│
├── color
│   ├── ColorProvider.java          <-- Strategy for dynamic/static module coloring
│   ├── SolidColorProvider.java     <-- Plain color strategy
│   ├── GradientColorProvider.java  <-- Coordinate-based gradient strategy
│   └── ImageColorProvider.java     <-- Template image color sampling strategy
│
└── render
    ├── DefaultQrRenderer.java      <-- Graphics2D coordinate plotting engine
    ├── ModuleDrawer.java           <-- Strategy for individual pixel geometries
    │   ├── SquareModuleDrawer.java
    │   ├── CircleModuleDrawer.java
    │   └── RoundedModuleDrawer.java
    └── FinderDrawer.java           <-- Strategy for corner alignment patterns (Finders)
        ├── SquareFinderDrawer.java
        └── RoundedFinderDrawer.java

```
## 5. Architectural Contracts (SOLID in Practice)
### 5.1. Single Responsibility Principle (SRP)
 * MatrixGenerator is solely tasked with converting content into a boolean matrix.
 * DefaultQrRenderer is only responsible for rendering shapes onto a BufferedImage canvas.
 * ColorProvider is uniquely tasked with evaluating color values without understanding module shapes.
### 5.2. Open/Closed Principle (OCP)
The graphics engine uses strategies for drawing modules and alignment patterns. To add a custom module geometry (e.g., diamond or star shapes), developers implement ModuleDrawer without altering the DefaultQrRenderer class:
```java
public interface ModuleDrawer {
    void draw(Graphics2D g2d, double x, double y, double size);
}

```
### 5.3. Dependency Inversion Principle (DIP)
CustomQrGenerator depends on the MatrixGenerator interface rather than concrete implementations, allowing developers to switch from ZXing to any other QR engine or mock data generator seamlessly.
## 6. Image Pixel Mapping Math
Let a QR code have physical dimensions S \times S pixels, with a logical matrix grid of M \times M modules. The width and height of each logical cell are calculated as:

For any module located at coordinates (row, col), its central coordinate on the physical canvas is computed as:

The ImageColorProvider maps these coordinates proportionally to the template image bounds (W_{\text{img}} \times H_{\text{img}}):

The provider samples the exact pixel color at (X_{\text{mapped}}, Y_{\text{mapped}}) and supplies it to the renderer for graphic drawing.
