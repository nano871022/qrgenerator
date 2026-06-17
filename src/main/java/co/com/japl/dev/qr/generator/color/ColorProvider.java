package co.com.japl.dev.qr.generator.color;

import java.awt.Color;

/**
 * Strategy for dynamic/static module coloring.
 */
public interface ColorProvider {
    /**
     * Evaluates color values based on coordinates.
     *
     * @param row      Current row in the QR matrix.
     * @param col      Current column in the QR matrix.
     * @param totalSize Total logical size of the QR matrix.
     * @return The color for the specific module.
     */
    Color getColor(int row, int col, int totalSize);
}
