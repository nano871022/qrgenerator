package co.com.japl.dev.qr.generator.color;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * Strategy for image-based color sampling module coloring.
 */
public class ImageColorProvider implements ColorProvider {
    private final BufferedImage templateImage;

    public ImageColorProvider(BufferedImage templateImage) {
        if (templateImage == null) {
            throw new IllegalArgumentException("Template image cannot be null");
        }
        this.templateImage = templateImage;
    }

    @Override
    public Color getColor(int row, int col, int totalSize) {
        int width = templateImage.getWidth();
        int height = templateImage.getHeight();

        int x = (int) (((long) col * width) / totalSize);
        int y = (int) (((long) row * height) / totalSize);

        // Ensure coordinates are within bounds
        x = Math.min(x, width - 1);
        y = Math.min(y, height - 1);
        x = Math.max(0, x);
        y = Math.max(0, y);

        return new Color(templateImage.getRGB(x, y), true);
    }
}
