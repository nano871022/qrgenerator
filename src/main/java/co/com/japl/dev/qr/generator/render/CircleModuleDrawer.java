package co.com.japl.dev.qr.generator.render;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

/**
 * Circle module drawer implementation.
 */
public class CircleModuleDrawer implements ModuleDrawer {
    @Override
    public void draw(Graphics2D g2d, double x, double y, double cellSize) {
        g2d.fill(new Ellipse2D.Double(x, y, cellSize, cellSize));
    }
}
