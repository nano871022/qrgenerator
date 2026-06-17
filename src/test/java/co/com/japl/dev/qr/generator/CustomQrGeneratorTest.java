package co.com.japl.dev.qr.generator;

import static org.junit.jupiter.api.Assertions.*;

import co.com.japl.dev.qr.generator.render.CircleModuleDrawer;
import co.com.japl.dev.qr.generator.color.ImageColorProvider;
import java.awt.Color;
import java.awt.image.BufferedImage;
import org.junit.jupiter.api.Test;

class CustomQrGeneratorTest {

    @Test
    void testFluentBuilderThrowsExceptionOnEmptyContent() {
        assertThrows(IllegalStateException.class, () -> {
            CustomQrGenerator.builder().size(350).build();
        });
    }

    @Test
    void testFluentBuilderThrowsExceptionOnBlankContent() {
        assertThrows(IllegalStateException.class, () -> {
            CustomQrGenerator.builder().content("   ").build();
        });
    }

    @Test
    void testGenerateProducesImage() {
        BufferedImage image = CustomQrGenerator.fromConfig(
                CustomQrGenerator.builder()
                    .content("Test Content")
                    .size(200)
                    .build()
                ).generate();

        assertNotNull(image);
        assertEquals(200, image.getWidth());
        assertEquals(200, image.getHeight());
    }

    @Test
    void testGenerateWithLogo() {
        BufferedImage logo = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
        BufferedImage image = CustomQrGenerator.fromConfig(
                CustomQrGenerator.builder()
                    .content("Test Content")
                    .size(400)
                    .logo(logo, 0.2, true)
                    .build()
                ).generate();

        assertNotNull(image);
        assertEquals(400, image.getWidth());
        // Basic check: center pixel should be non-transparent (logo area)
        // Note: TYPE_INT_ARGB, logo was empty so it might be 0.
        // But with quiet zone it should be WHITE
        int centerColor = image.getRGB(200, 200);
        assertEquals(Color.WHITE.getRGB(), centerColor);
    }
}
