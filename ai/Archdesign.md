# Software Architecture Document (SAD)
## Java Custom QR Code Generator System - co.com.japl.dev.qr.generator
This document describes the logical architecture, execution flow, component relationships, and communication protocols of the custom QR generation library.
## 1. Logical Component Layout
The system decoupling ensures that data manipulation, layout styling, and pixel coloring are executed by specialized components:
```text
  ┌────────────────────────────────────────────────────────┐
  │                   CLIENT (Consumer)                    │
  └───────────────────────────┬────────────────────────────┘
                              │ Accesses Fluent Builder
                              ▼
  ┌────────────────────────────────────────────────────────┐
  │                 CustomQrGenerator (Facade)             │
  └───────┬────────────────────────────────────────┬───────┘
          │                                        │
          │ 1. Fetches Grid                        │ 2. Initiates Plotting
          ▼                                        ▼
  ┌───────────────────────┐              ┌───────────────────────┐
  │    MatrixGenerator    │              │   DefaultQrRenderer   │
  │     (Abstraction)     │              └─────────┬─────────────┘
  └──────────┬────────────┘                        │
             │                                     ├─► Uses ModuleDrawer (Shape Strategy)
             ▼                                     │
  ┌───────────────────────┐                        ├─► Uses FinderDrawer (Finder Strategy)
  │  ZXing/Mock Matrix    │                        │
  │     (Implementation)  │                        └─► Queries for RGB mapping
  └───────────────────────┘                                │
                                                           ▼
                                                 ┌───────────────────────┐
                                                 │     ColorProvider     │
                                                 │     (Abstraction)     │
                                                 └─────────┬─────────────┘
                                                           ├─► SolidColorProvider
                                                           ├─► GradientColorProvider
                                                           └─► ImageColorProvider

```
## 2. Sequence Diagram (Hybrid Artistic Flow)
The sequence diagram below displays the execution path when a client requests a QR code styled with an underlying template image and a centered brand logo:
```text
Client            CustomQrGenerator       DefaultQrRenderer      ImageColorProvider      ModuleDrawer        Graphics2D
   │                       │                       │                       │                   │                  │
   │ 1. generate()         │                       │                       │                   │                  │
   ├──────────────────────►│                       │                       │                   │                  │
   │                       │ 2. render(matrix)     │                       │                   │                  │
   │                       ├──────────────────────►│                       │                   │                  │
   │                       │                       │                       │                   │                  │
   │                       │                       │ 3. Loop cell (row, col)                   │                  │
   │                       │                       ├─────────────────────────────────────────────────────────────┐│
   │                       │                       │                                                             ││
   │                       │                       │ 4. getColor(row, col, size)                                 ││
   │                       │                       ├──────────────────────►│                                     ││
   │                       │                       │                       │ 5. Proportional scaling             ││
   │                       │                       │                       │    & pixel sampling                 ││
   │                       │                       │                       │───┐                                 ││
   │                       │                       │                       │   │                                 ││
   │                       │                       │                       │◄──┘                                 ││
   │                       │                       │ 6. Return sampled RGB │                                     ││
   │                       │                       │◄──────────────────────┤                                     ││
   │                       │                       │                                                             ││
   │                       │                       │ 7. setColor(color)                                          ││
   │                       │                       ├────────────────────────────────────────────────────────────►││
   │                       │                       │                                                             ││
   │                       │                       │ 8. draw(g2d, x, y, cellSize)                                ││
   │                       │                       ├──────────────────────────────────────►│                     ││
   │                       │                       │                                       │ 9. g2d.fill(...)    ││
   │                       │                       │                                       ├────────────────────►││
   │                       │                       │◄──────────────────────────────────────┤                     ││
   │                       │◄────────────────────────────────────────────────────────────────────────────────────┘│
   │                       │                       │                                                             │
   │                       │                       │ 10. Draw Finders & overlay logo with Quiet Zone             │
   │                       │                       ├────────────────────────────────────────────────────────────┐│
   │                       │                       │                                                            ││
   │                       │                       │ 11. Overwrite central matrix cells with solid background   ││
   │                       │                       │◄───────────────────────────────────────────────────────────┘│
   │                       │                       │                                                             │
   │                       │ 12. Return Image      │                                                             │
   │                       │◄──────────────────────┤                                                             │
   │◄──────────────────────┤                       │                                                             │

```
## 3. Data Pipeline and Protection Strategies
The DefaultQrRenderer is engineered to prevent visual corruption through a multi-pass drawing sequence:
```
[Target Canvas] ──► [Background Fill] ──► [Module Grid Loop] ──► [Finder Styles] ──► [Logo Overlay]

```
### 3.1. Quality Pre-flight
A BufferedImage is instantiated with alpha channel support (BufferedImage.TYPE_INT_ARGB). Rendering hints are configured for high-quality antialiasing:
 * RenderingHints.KEY_ANTIALIASING \rightarrow VALUE_ANTIALIAS_ON
 * RenderingHints.KEY_INTERPOLATION \rightarrow VALUE_INTERPOLATION_BICUBIC (necessary for crisp logo resizing).
### 3.2. Central quiet Zone Calculation (Logo Protection)
To preserve visual clarity around the logo, the renderer enforces a protective boundary offset (M_s) around the scaled logo dimension (L_s):

Before stamping the actual logo, the renderer sweeps the central canvas region and paints a clean, solid background, ensuring that no mapped pixels leak behind the brand asset.
## 4. Extensibility Guide (OCP in Action)
### Adding a Diamond-shaped Module
To extend the library with new styles without modifying the core renderer, implement ModuleDrawer:
```java
package co.com.japl.dev.qr.generator.render;

import java.awt.Graphics2D;
import java.awt.geom.Path2D;

public class DiamondModuleDrawer implements ModuleDrawer {
    @Override
    public void draw(Graphics2D g2d, double x, double y, double size) {
        Path2D path = new Path2D.Double();
        path.moveTo(x + size / 2, y);
        path.lineTo(x + size, y + size / 2);
        path.lineTo(x + size / 2, y + size);
        path.lineTo(x, y + size / 2);
        path.closePath();
        g2d.fill(path);
    }
}

```
Simply inject the instance into the builder setup:
```java
CustomQrGenerator.builder()
    .content("[https://japl.com.co](https://japl.com.co)")
    .moduleDrawer(new DiamondModuleDrawer())
    .build();

```
