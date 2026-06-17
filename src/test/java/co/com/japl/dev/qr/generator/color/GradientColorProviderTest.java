package co.com.japl.dev.qr.generator.color;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;
import org.junit.jupiter.api.Test;

class GradientColorProviderTest {

    @Test
    void testGetColorReturnsInterpolatedColor() {
        Color start = new Color(255, 0, 0);
        Color end = new Color(0, 0, 255);
        GradientColorProvider provider = new GradientColorProvider(start, end);

        // Start (0,0) -> ratio = 0
        assertEquals(start, provider.getColor(0, 0, 21));

        // End (20,20) -> ratio = (20+20)/(2*20) = 1
        assertEquals(end, provider.getColor(20, 20, 21));

        // Middle (10,10) -> ratio = (10+10)/(2*20) = 0.5
        Color middle = provider.getColor(10, 10, 21);
        assertEquals(127, middle.getRed());
        assertEquals(0, middle.getGreen());
        assertEquals(127, middle.getBlue());
    }

    @Test
    void testGetColorWithTotalSizeOne() {
        Color start = Color.RED;
        Color end = Color.BLUE;
        GradientColorProvider provider = new GradientColorProvider(start, end);

        assertEquals(start, provider.getColor(0, 0, 1));
    }
}
