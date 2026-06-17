package co.com.japl.dev.qr.generator.color;

import java.awt.Color;

/**
 * Strategy for solid color module coloring.
 */
public class SolidColorProvider implements ColorProvider {
    private final Color color;

    public SolidColorProvider(Color color) {
        this.color = color;
    }

    @Override
    public Color getColor(int row, int col, int totalSize) {
        return color;
    }
}
