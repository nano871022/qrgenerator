package co.com.japl.dev.qr.generator.render;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/**
 * Square module drawer implementation.
 */
public class SquareModuleDrawer implements ModuleDrawer {
    @Override
    public void draw(Graphics2D g2d, double x, double y, double cellSize) {
        g2d.fill(new Rectangle2D.Double(x, y, cellSize, cellSize));
    }
}
