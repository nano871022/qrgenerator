package co.com.japl.dev.qr.generator.render;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FinderDrawerTest {

    @Mock
    private Graphics2D g2d;

    @Test
    void testSquareFinderDrawer() {
        FinderDrawer drawer = new SquareFinderDrawer();
        drawer.draw(g2d, 0.0, 0.0, 70.0);

        ArgumentCaptor<Shape> shapeCaptor = ArgumentCaptor.forClass(Shape.class);
        verify(g2d).fill(shapeCaptor.capture());

        assertTrue(shapeCaptor.getValue() instanceof Path2D);
        Path2D path = (Path2D) shapeCaptor.getValue();
        assertEquals(Path2D.WIND_EVEN_ODD, path.getWindingRule());

        Rectangle2D bounds = path.getBounds2D();
        assertEquals(0.0, bounds.getX());
        assertEquals(0.0, bounds.getY());
        assertEquals(70.0, bounds.getWidth());
        assertEquals(70.0, bounds.getHeight());
    }

    @Test
    void testRoundedFinderDrawer() {
        FinderDrawer drawer = new RoundedFinderDrawer();
        drawer.draw(g2d, 10.0, 10.0, 70.0);

        ArgumentCaptor<Shape> shapeCaptor = ArgumentCaptor.forClass(Shape.class);
        verify(g2d).fill(shapeCaptor.capture());

        assertTrue(shapeCaptor.getValue() instanceof Path2D);
        Path2D path = (Path2D) shapeCaptor.getValue();
        assertEquals(Path2D.WIND_EVEN_ODD, path.getWindingRule());

        Rectangle2D bounds = path.getBounds2D();
        assertEquals(10.0, bounds.getX());
        assertEquals(10.0, bounds.getY());
        assertEquals(70.0, bounds.getWidth());
        assertEquals(70.0, bounds.getHeight());
    }
}
