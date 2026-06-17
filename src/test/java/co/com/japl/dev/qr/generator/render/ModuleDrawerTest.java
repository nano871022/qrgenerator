package co.com.japl.dev.qr.generator.render;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ModuleDrawerTest {

    @Mock
    private Graphics2D g2d;

    @Test
    void testSquareModuleDrawer() {
        ModuleDrawer drawer = new SquareModuleDrawer();
        drawer.draw(g2d, 10.0, 20.0, 5.0);

        ArgumentCaptor<Shape> shapeCaptor = ArgumentCaptor.forClass(Shape.class);
        verify(g2d).fill(shapeCaptor.capture());

        assertTrue(shapeCaptor.getValue() instanceof Rectangle2D);
        Rectangle2D rect = (Rectangle2D) shapeCaptor.getValue();
        assertEquals(10.0, rect.getX());
        assertEquals(20.0, rect.getY());
        assertEquals(5.0, rect.getWidth());
        assertEquals(5.0, rect.getHeight());
    }

    @Test
    void testCircleModuleDrawer() {
        ModuleDrawer drawer = new CircleModuleDrawer();
        drawer.draw(g2d, 15.0, 25.0, 8.0);

        ArgumentCaptor<Shape> shapeCaptor = ArgumentCaptor.forClass(Shape.class);
        verify(g2d).fill(shapeCaptor.capture());

        assertTrue(shapeCaptor.getValue() instanceof Ellipse2D);
        Ellipse2D ellipse = (Ellipse2D) shapeCaptor.getValue();
        assertEquals(15.0, ellipse.getX());
        assertEquals(25.0, ellipse.getY());
        assertEquals(8.0, ellipse.getWidth());
        assertEquals(8.0, ellipse.getHeight());
    }

    @Test
    void testRoundedModuleDrawer() {
        ModuleDrawer drawer = new RoundedModuleDrawer();
        drawer.draw(g2d, 5.0, 5.0, 10.0);

        ArgumentCaptor<Shape> shapeCaptor = ArgumentCaptor.forClass(Shape.class);
        verify(g2d).fill(shapeCaptor.capture());

        assertTrue(shapeCaptor.getValue() instanceof RoundRectangle2D);
        RoundRectangle2D roundRect = (RoundRectangle2D) shapeCaptor.getValue();
        assertEquals(5.0, roundRect.getX());
        assertEquals(5.0, roundRect.getY());
        assertEquals(10.0, roundRect.getWidth());
        assertEquals(10.0, roundRect.getHeight());
        assertEquals(5.0, roundRect.getArcWidth());
        assertEquals(5.0, roundRect.getArcHeight());
    }
}
