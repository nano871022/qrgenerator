package co.com.japl.dev.qr.generator.model;

import co.com.japl.dev.qr.generator.color.ColorProvider;
import co.com.japl.dev.qr.generator.render.FinderDrawer;
import co.com.japl.dev.qr.generator.render.ModuleDrawer;
import java.awt.image.BufferedImage;

/**
 * Immutable class to store configuration parameters for QR generation.
 */
public record QrConfig(
    String content,
    int size,
    ModuleDrawer moduleDrawer,
    FinderDrawer finderDrawer,
    ColorProvider colorProvider,
    BufferedImage logo,
    double logoSizeRatio,
    boolean hasQuietZone
) {
    public static class Builder {
        private String content;
        private int size = 400;
        private ModuleDrawer moduleDrawer;
        private FinderDrawer finderDrawer;
        private ColorProvider colorProvider;
        private BufferedImage logo;
        private double logoSizeRatio = 0.2;
        private boolean hasQuietZone = true;

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder size(int size) {
            this.size = size;
            return this;
        }

        public Builder moduleDrawer(ModuleDrawer moduleDrawer) {
            this.moduleDrawer = moduleDrawer;
            return this;
        }

        public Builder finderDrawer(FinderDrawer finderDrawer) {
            this.finderDrawer = finderDrawer;
            return this;
        }

        public Builder colorProvider(ColorProvider colorProvider) {
            this.colorProvider = colorProvider;
            return this;
        }

        public Builder logo(BufferedImage logo, double ratio, boolean quietZone) {
            this.logo = logo;
            this.logoSizeRatio = ratio;
            this.hasQuietZone = quietZone;
            return this;
        }

        public QrConfig build() {
            if (content == null || content.isBlank()) {
                throw new IllegalStateException("Content cannot be null or empty");
            }
            return new QrConfig(content, size, moduleDrawer, finderDrawer, colorProvider, logo, logoSizeRatio, hasQuietZone);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
