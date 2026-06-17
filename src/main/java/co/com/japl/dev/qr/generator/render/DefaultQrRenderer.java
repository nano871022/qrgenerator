package co.com.japl.dev.qr.generator.render;

import co.com.japl.dev.qr.generator.color.ColorProvider;
import co.com.japl.dev.qr.generator.color.SolidColorProvider;
import co.com.japl.dev.qr.generator.model.QrConfig;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * Main rendering engine for the QR code.
 */
public class DefaultQrRenderer {

    public BufferedImage render(boolean[][] matrix, QrConfig config) {
        int matrixSize = matrix.length;
        int imageSize = config.size();
        double cellSize = (double) imageSize / matrixSize;

        BufferedImage image = new BufferedImage(imageSize, imageSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        try {
            setupGraphics(g2d);

            ModuleDrawer moduleDrawer = config.moduleDrawer() != null ? config.moduleDrawer() : new SquareModuleDrawer();
            FinderDrawer finderDrawer = config.finderDrawer() != null ? config.finderDrawer() : new SquareFinderDrawer();
            ColorProvider colorProvider = config.colorProvider() != null ? config.colorProvider() : new SolidColorProvider(Color.BLACK);

            // 1. Draw modules
            drawModules(g2d, matrix, config, cellSize, moduleDrawer, colorProvider);

            // 2. Draw finders
            drawFinders(g2d, matrixSize, cellSize, finderDrawer, colorProvider);

            // 3. Draw logo
            if (config.logo() != null) {
                drawLogo(g2d, imageSize, config);
            }

        } finally {
            g2d.dispose();
        }

        return image;
    }

    private void setupGraphics(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
    }

    private void drawModules(Graphics2D g2d, boolean[][] matrix, QrConfig config, double cellSize,
                             ModuleDrawer moduleDrawer, ColorProvider colorProvider) {
        int matrixSize = matrix.length;
        int logoStart = -1;
        int logoEnd = -1;

        if (config.logo() != null && config.hasQuietZone()) {
            double logoSize = config.size() * config.logoSizeRatio();
            double margin = cellSize; // Extra safety margin
            double quietZoneSize = logoSize + 2 * margin;

            int quietZoneModules = (int) Math.ceil(quietZoneSize / cellSize);
            logoStart = (matrixSize - quietZoneModules) / 2;
            logoEnd = logoStart + quietZoneModules;
        }

        for (int row = 0; row < matrixSize; row++) {
            for (int col = 0; col < matrixSize; col++) {
                if (matrix[row][col]) {
                    // Skip finders
                    if (isFinderArea(row, col, matrixSize)) {
                        continue;
                    }

                    // Skip logo area
                    if (logoStart != -1 && row >= logoStart && row < logoEnd && col >= logoStart && col < logoEnd) {
                        continue;
                    }

                    g2d.setColor(colorProvider.getColor(row, col, matrixSize));
                    moduleDrawer.draw(g2d, col * cellSize, row * cellSize, cellSize);
                }
            }
        }
    }

    private boolean isFinderArea(int row, int col, int matrixSize) {
        // Top-left
        if (row < 7 && col < 7) return true;
        // Top-right
        if (row < 7 && col >= matrixSize - 7) return true;
        // Bottom-left
        if (row >= matrixSize - 7 && col < 7) return true;
        return false;
    }

    private void drawFinders(Graphics2D g2d, int matrixSize, double cellSize, FinderDrawer finderDrawer, ColorProvider colorProvider) {
        double finderSize = 7 * cellSize;

        // Top-left
        g2d.setColor(colorProvider.getColor(0, 0, matrixSize));
        finderDrawer.draw(g2d, 0, 0, finderSize);

        // Top-right
        g2d.setColor(colorProvider.getColor(0, matrixSize - 1, matrixSize));
        finderDrawer.draw(g2d, (matrixSize - 7) * cellSize, 0, finderSize);

        // Bottom-left
        g2d.setColor(colorProvider.getColor(matrixSize - 1, 0, matrixSize));
        finderDrawer.draw(g2d, 0, (matrixSize - 7) * cellSize, finderSize);
    }

    private void drawLogo(Graphics2D g2d, int imageSize, QrConfig config) {
        BufferedImage logo = config.logo();
        double logoSize = imageSize * config.logoSizeRatio();
        int x = (int) ((imageSize - logoSize) / 2);
        int y = (int) ((imageSize - logoSize) / 2);

        if (config.hasQuietZone()) {
            g2d.setColor(Color.WHITE);
            g2d.fill(new Rectangle2D.Double(x, y, logoSize, logoSize));
        }

        g2d.drawImage(logo, x, y, (int) logoSize, (int) logoSize, null);
    }
}
