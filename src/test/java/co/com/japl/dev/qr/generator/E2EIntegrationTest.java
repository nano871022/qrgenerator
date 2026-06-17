package co.com.japl.dev.qr.generator;

import co.com.japl.dev.qr.generator.color.GradientColorProvider;
import co.com.japl.dev.qr.generator.color.ImageColorProvider;
import co.com.japl.dev.qr.generator.color.SolidColorProvider;
import co.com.japl.dev.qr.generator.render.CircleModuleDrawer;
import co.com.japl.dev.qr.generator.render.RoundedModuleDrawer;
import co.com.japl.dev.qr.generator.render.SquareModuleDrawer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class E2EIntegrationTest {

    @Test
    void testArtisticQrIsDecodableByZxing() throws Exception {
        String content = "https://japl.com.co/solid-dev";
        BufferedImage artisticQr = CustomQrGenerator.fromConfig(
                CustomQrGenerator.builder()
                    .content(content)
                    .size(400)
                    .moduleDrawer(new CircleModuleDrawer())
                    .colorProvider(new ImageColorProvider(loadTestTemplate()))
                    .logo(loadTestLogo(), 0.20, true)
                    .build()
                ).generate();

        assertDecodable(artisticQr, content);
    }

    @Test
    void testRoundedQrWithGradientIsDecodable() throws Exception {
        String content = "https://japl.com.co/gradient";
        BufferedImage qrImage = CustomQrGenerator.fromConfig(
                CustomQrGenerator.builder()
                        .content(content)
                        .size(400)
                        .moduleDrawer(new RoundedModuleDrawer())
                        .colorProvider(new GradientColorProvider(Color.BLUE, Color.BLACK))
                        .build()
        ).generate();

        assertDecodable(qrImage, content);
    }

    @Test
    void testSquareQrWithSolidColorIsDecodable() throws Exception {
        String content = "Simple Content";
        BufferedImage qrImage = CustomQrGenerator.fromConfig(
                CustomQrGenerator.builder()
                        .content(content)
                        .size(300)
                        .moduleDrawer(new SquareModuleDrawer())
                        .colorProvider(new SolidColorProvider(Color.BLACK))
                        .build()
        ).generate();

        assertDecodable(qrImage, content);
    }

    private void assertDecodable(BufferedImage image, String expectedContent) throws Exception {
        LuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Result result = new MultiFormatReader().decode(bitmap);
        assertEquals(expectedContent, result.getText());
    }

    private BufferedImage loadTestTemplate() {
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, 100, 100);
        g.dispose();
        return image;
    }

    private BufferedImage loadTestLogo() {
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setColor(new Color(255, 0, 0, 128));
        g.fillRect(0, 0, 100, 100);
        g.dispose();
        return image;
    }
}
