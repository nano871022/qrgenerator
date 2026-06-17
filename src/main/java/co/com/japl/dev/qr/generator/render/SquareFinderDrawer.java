package co.com.japl.dev.qr.generator.render;

import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

/**
 * Square finder drawer implementation.
 */
public class SquareFinderDrawer implements FinderDrawer {
    @Override
    public void draw(Graphics2D g2d, double x, double y, double size) {
        double unit = size / 7.0;
        Path2D path = new Path2D.Double(Path2D.WIND_EVEN_ODD);

        // Outer square (7x7)
        path.append(new Rectangle2D.Double(x, y, size, size), false);

        // Middle square (5x5) - hollow
        path.append(new Rectangle2D.Double(x + unit, y + unit, 5 * unit, 5 * unit), false);

        // Inner square (3x3) - solid
        path.append(new Rectangle2D.Double(x + 2 * unit, y + 2 * unit, 3 * unit, 3 * unit), false);

        g2d.fill(path);
    }
}
