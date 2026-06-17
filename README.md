# Custom QR Code Generator (`co.com.japl.dev.qr.generator`)

A robust, modular, and highly customizable QR code generator developed in Java. This project has been designed under **SOLID** and **Clean Code** principles, maintaining a **KISS** (Keep It Simple, Stupid) approach to ensure a clean, extensible, and easy-to-maintain architecture.

The repository integrates a modern **Spec-Driven Design** strategy, centralizing architectural design and AI agent automation guidelines within the `ai/` folder.

## 🚀 Key Features

- **Base QR Code Generation:** Full support for standard text and URL encoding with strict control over error correction levels.
- **Logo Overlay:** Ability to integrate custom logos into the center of the QR code without compromising readability or data redundancy.
- **Artistic Image Mapping:** Advanced chromatic processing pipeline that transforms reference images into stylized pixel patterns within the QR code (generative art).
- **Flexible Rendering (`ModuleDrawer`):** Complete abstraction of the module drawing workflow to allow custom styles (rounded edges, gradients, custom shapes).
- **Extensible Architecture:** Extensive use of the *Strategy* pattern to decouple aesthetic design rules from the core QR generation engine.

## 📂 Project Structure

```text
qrgenerator/
├── ai/                              # Specifications and guidelines for AI Agents
│   ├── design_specification.md      # Design Specification (Technical details and contracts)
│   ├── architecture_document.md     # Architecture Document (Modular design and Clean Code)
│   └── work_plan_test_matrix.md     # Work Plan with Test Matrix (Phases and test matrix)
├── src/
│   └── main/
│       └── java/
│           └── co/com/japl/dev/qr/generator/
│               ├── core/             # Encoding engine and base QR logic
│               ├── drawer/           # Rendering components and contracts (ModuleDrawer)
│               └── strategy/         # Chromatic and visual customization strategies
├── pom.xml
└── README.md
