package co.com.japl.dev.qr.generator.color;

import java.awt.Color;

/**
 * Strategy for gradient color module coloring.
 */
public class GradientColorProvider implements ColorProvider {
    private final Color startColor;
    private final Color endColor;

    public GradientColorProvider(Color startColor, Color endColor) {
        this.startColor = startColor;
        this.endColor = endColor;
    }

    @Override
    public Color getColor(int row, int col, int totalSize) {
        if (totalSize <= 1) {
            return startColor;
        }
        float ratio = (float) (row + col) / (2 * (totalSize - 1));
        return interpolate(startColor, endColor, ratio);
    }

    private Color interpolate(Color c1, Color c2, float ratio) {
        int r = (int) (c1.getRed() + ratio * (c2.getRed() - c1.getRed()));
        int g = (int) (c1.getGreen() + ratio * (c2.getGreen() - c1.getGreen()));
        int b = (int) (c1.getBlue() + ratio * (c2.getBlue() - c1.getBlue()));
        int a = (int) (c1.getAlpha() + ratio * (c2.getAlpha() - c1.getAlpha()));
        return new Color(Math.min(255, Math.max(0, r)),
                         Math.min(255, Math.max(0, g)),
                         Math.min(255, Math.max(0, b)),
                         Math.min(255, Math.max(0, a)));
    }
}
