package co.com.japl.dev.qr.generator.render;

import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;

/**
 * Rounded module drawer implementation.
 */
public class RoundedModuleDrawer implements ModuleDrawer {
    @Override
    public void draw(Graphics2D g2d, double x, double y, double cellSize) {
        double arc = cellSize * 0.5;
        g2d.fill(new RoundRectangle2D.Double(x, y, cellSize, cellSize, arc, arc));
    }
}
