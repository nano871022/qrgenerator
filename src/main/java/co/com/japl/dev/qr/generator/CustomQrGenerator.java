package co.com.japl.dev.qr.generator;

import co.com.japl.dev.qr.generator.model.QrConfig;
import java.awt.image.BufferedImage;

/**
 * Primary Facade for QR code generation.
 */
public class CustomQrGenerator {
    private final QrConfig config;

    private CustomQrGenerator(QrConfig config) {
        this.config = config;
    }

    public static QrConfig.Builder builder() {
        return QrConfig.builder();
    }

    /**
     * Entry point to create the generator from config.
     */
    public static CustomQrGenerator fromConfig(QrConfig config) {
        return new CustomQrGenerator(config);
    }

    /**
     * Generates the QR code image.
     *
     * @return A BufferedImage containing the QR code.
     */
    public BufferedImage generate() {
        // Implementation will follow in T5
        return null;
    }
}
