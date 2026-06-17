package co.com.japl.dev.qr.generator.render;

import java.awt.Graphics2D;

/**
 * Strategy for corner alignment patterns (Finders).
 */
public interface FinderDrawer {
    /**
     * Draws a finder pattern.
     *
     * @param g2d      The graphics context.
     * @param x        Top-left X coordinate.
     * @param y        Top-left Y coordinate.
     * @param size     Size of the finder pattern.
     */
    void draw(Graphics2D g2d, double x, double y, double size);
}
