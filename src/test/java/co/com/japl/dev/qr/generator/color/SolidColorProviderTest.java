package co.com.japl.dev.qr.generator.color;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;
import org.junit.jupiter.api.Test;

class SolidColorProviderTest {

    @Test
    void testGetColorReturnsConstantColor() {
        Color expected = Color.RED;
        SolidColorProvider provider = new SolidColorProvider(expected);

        assertEquals(expected, provider.getColor(0, 0, 21));
        assertEquals(expected, provider.getColor(10, 10, 21));
        assertEquals(expected, provider.getColor(20, 20, 21));
    }
}
