# Work Breakdown Structure (WBS) & Testing Strategy
## Java Custom QR Code Generator System - co.com.japl.dev.qr.generator
This execution plan breaks down the development lifecycle into concurrent and sequential milestones, setting clear entry/exit criteria, testing assertions, and assignments for multiple development agents.
## 1. Task Dependency Graph & Agent Parallelization
To maximize development velocity, tasks are divided into **Sequential (S)** tasks (which act as critical blockers) and **Parallel (P)** tasks (which can be assigned to different developers/agents concurrently once interfaces are stable).
```text
[ T1: Core API & Interfaces ] (S)
        │
        ├───► [ T2: Matrix Generator Integration ] (P) ────┐
        │                                                  │
        ├───► [ T3: Color Strategies Development ] (P) ────┼─► [ T5: Main Engine Renderer ] (S) ─► [ T6: Integration & E2E ] (I)
        │                                                  │
        └───► [ T4: Geometry Drawers Creation ] (P) ───────┘
```
## 2. Milestone Backlog & Testing Criteria
### T1: Define Core Models, API, and Builder (Fluent Interface)
 * **Execution Style:** Sequential (S) - *Initial Blocker.*
 * **Objective:** Establish the packages, interfaces (MatrixGenerator, ColorProvider, ModuleDrawer, FinderDrawer), the configuration parameter state (QrConfig), and the builder interface.
 * **Success Criteria:** The interface structure compiles cleanly. The fluent interface syntax executes without type errors.
 * **Verification Test:**
   ```java
   @Test
   void testFluentBuilderThrowsExceptionOnEmptyContent() {
       assertThrows(IllegalStateException.class, () -> {
           CustomQrGenerator.builder().size(350).build();
       });
   }
   
   ```
### T2: Integrations for Logical Matrix Generation (MatrixGenerator)
 * **Execution Style:** Parallel (P) - *Prerequisite: T1.*
 * **Objective:** Create the ZXing integration adaptor that turns text data into a boolean 2D array representation (boolean[][]) respecting requested error correction metrics.
 * **Success Criteria:** A valid coordinate matrix is created matching structural QR standard size (e.g., Version 1 - 21 \times 21 grid).
 * **Verification Test:**
   * **Matrix Pattern Check:** Assert that the corner areas of (0,0), (0, M-7), and (M-7, 0) are marked correctly as active bits so finders can be overlaid.
### T3: Implement Color Strategy Providers (ColorProvider)
 * **Execution Style:** Parallel (P) - *Prerequisite: T1.*
 * **Objective:** Develop the core color strategies: SolidColorProvider, GradientColorProvider, and ImageColorProvider.
 * **Success Criteria:** The ImageColorProvider successfully scales low-res or high-res images to fit the QR dimension context and samples colors without raising out-of-bounds exceptions.
 * **Verification Test:**
   * **Scale Mock Test:** Feed a 2 \times 2 pixel red template image. Ask the provider for coordinates on a 400 \times 400 virtual canvas. Assert that all sampled outputs return Color.RED.
### T4: Geometry Drawers (ModuleDrawer & FinderDrawer)
 * **Execution Style:** Parallel (P) - *Prerequisite: T1.*
 * **Objective:** Implement rendering calculations using Graphics2D vectors for pixels (Square, Circle, Rounded) and finders (Square, Rounded).
 * **Success Criteria:** Module drawings fit perfectly inside the designated cellSize box without visual bleed or clipping.
 * **Verification Test:**
   * **Geometry Mocking:** Mock Graphics2D and verify that the drawing calls (fill, fillRect, fillRoundRect) are invoked with coordinate values that remain within the expected bounding box.
### T5: Engine Integration (DefaultQrRenderer)
 * **Execution Style:** Sequential (S) - *Prerequisite: T2, T3, T4.*
 * **Objective:** Combine the matrix logic, the color provider strategy, and the drawing patterns into the unified DefaultQrRenderer. Add Quiet Zone boundary exclusions and logo overlay processing.
 * **Success Criteria:** A unified BufferedImage is produced combining all styles, with a clean logo area in the center and correct coordinates.
 * **Verification Test:**
   * **Overlay Quiet Zone Check:** Verify that matrix pixels located within the logo's central safety boundaries remain clear of template image colors.
### T6: End-to-End Scannability and Quality Assurance (E2E)
 * **Execution Style:** Integration (I) - *Final Phase.*
 * **Objective:** Programmatically generate high-complexity artistic QR codes (using a template background image and a central logo) and pipe them through a ZXing decoder to ensure 100% scannability.
 * **Success Criteria:** The decoded string matches the input content 100% of the time across various test configurations.
 * **Verification Test:**
   ```java
   @Test
   void testArtisticQrIsDecodableByZxing() throws Exception {
       BufferedImage artisticQr = CustomQrGenerator.builder()
           .content("[https://japl.com.co/solid-dev](https://japl.com.co/solid-dev)")
           .size(400)
           .moduleDrawer(new CircleModuleDrawer())
           .colorProvider(new ImageColorProvider(loadTestTemplate()))
           .logo(loadTestLogo(), 0.20, true)
           .build()
           .generate();
   
       LuminanceSource source = new BufferedImageLuminanceSource(artisticQr);
       BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
       Result result = new MultiFormatReader().decode(bitmap);
   
       assertEquals("[https://japl.com.co/solid-dev](https://japl.com.co/solid-dev)", result.getText());
   }
   
   ```
## 3. Resource and Agent Matrix Assignment

| Agent Profile | Targeted Milestone | Prerequisite Blocks | Target Deliverables |
| :--- | :--- | :--- | :--- |
| **Lead Architect** | **T1:** Core API, Interface & Models | None | API contract classes under co.com.japl.dev.qr.generator. |
| **Agent A (Data)** | **T2:** Matrix Generation Adaptor | **T1** complete | ZxingMatrixGenerator class. |
| **Agent B (Graphics)** | **T3:** Color Strategies | **T1** complete | ImageColorProvider class. |
| **Agent C (Math/UI)** | **T4:** Geometric Drawers | **T1** complete | ModuleDrawer and FinderDrawer implementations. |
| **Lead Integrator** | **T5:** Main Rendering Engine | **T2, T3, T4** complete | Combined DefaultQrRenderer class. |
| **QA Specialist** | **T6:** E2E Scannability Testing | **T5** complete | Full JUnit test suite. |
