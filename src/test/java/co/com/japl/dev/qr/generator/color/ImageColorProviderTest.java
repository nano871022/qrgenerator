package co.com.japl.dev.qr.generator.color;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;
import java.awt.image.BufferedImage;
import org.junit.jupiter.api.Test;

class ImageColorProviderTest {

    @Test
    void testGetColorSamplesFromImage() {
        // Create a 2x2 image: [Red, Green]
        //                      [Blue, White]
        BufferedImage img = new BufferedImage(2, 2, BufferedImage.TYPE_INT_RGB);
        img.setRGB(0, 0, Color.RED.getRGB());
        img.setRGB(1, 0, Color.GREEN.getRGB());
        img.setRGB(0, 1, Color.BLUE.getRGB());
        img.setRGB(1, 1, Color.WHITE.getRGB());

        ImageColorProvider provider = new ImageColorProvider(img);

        // Map to 4x4 QR matrix
        // (0,0) -> img(0,0) = RED
        assertEquals(Color.RED.getRGB(), provider.getColor(0, 0, 4).getRGB());
        // (0,2) -> img(1,0) = GREEN
        assertEquals(Color.GREEN.getRGB(), provider.getColor(0, 2, 4).getRGB());
        // (2,0) -> img(0,1) = BLUE
        assertEquals(Color.BLUE.getRGB(), provider.getColor(2, 0, 4).getRGB());
        // (2,2) -> img(1,1) = WHITE
        assertEquals(Color.WHITE.getRGB(), provider.getColor(2, 2, 4).getRGB());
    }

    @Test
    void testGetColorHandlesLargeCoordinates() {
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        img.setRGB(0, 0, Color.RED.getRGB());
        ImageColorProvider provider = new ImageColorProvider(img);

        // Any coordinate should map to (0,0) for a 1x1 image
        assertEquals(Color.RED.getRGB(), provider.getColor(100, 100, 21).getRGB());
    }
}
