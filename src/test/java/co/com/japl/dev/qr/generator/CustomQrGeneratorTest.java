package co.com.japl.dev.qr.generator;

import static org.junit.jupiter.api.Assertions.assertThrows;
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
}
