package co.com.japl.dev.qr.generator.render;

import java.awt.Graphics2D;

/**
 * Strategy for individual pixel geometries.
 */
public interface ModuleDrawer {
    /**
     * Draws a single QR module.
     *
     * @param g2d      The graphics context.
     * @param x        Top-left X coordinate.
     * @param y        Top-left Y coordinate.
     * @param cellSize Size of the cell.
     */
    void draw(Graphics2D g2d, double x, double y, double cellSize);
}
