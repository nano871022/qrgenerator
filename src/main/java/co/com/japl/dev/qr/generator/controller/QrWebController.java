package co.com.japl.dev.qr.generator.controller;

import co.com.japl.dev.qr.generator.CustomQrGenerator;
import co.com.japl.dev.qr.generator.color.ColorProvider;
import co.com.japl.dev.qr.generator.color.GradientColorProvider;
import co.com.japl.dev.qr.generator.color.ImageColorProvider;
import co.com.japl.dev.qr.generator.color.SolidColorProvider;
import co.com.japl.dev.qr.generator.model.QrConfig;
import co.com.japl.dev.qr.generator.render.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Controller
public class QrWebController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/generate")
    public ResponseEntity<byte[]> generateQr(
            @RequestParam("content") String content,
            @RequestParam("size") int size,
            @RequestParam("moduleStyle") String moduleStyle,
            @RequestParam("finderStyle") String finderStyle,
            @RequestParam("colorStrategy") String colorStrategy,
            @RequestParam(value = "solidColor", required = false, defaultValue = "#000000") String solidColor,
            @RequestParam(value = "gradientStart", required = false, defaultValue = "#000000") String gradientStart,
            @RequestParam(value = "gradientEnd", required = false, defaultValue = "#FFFFFF") String gradientEnd,
            @RequestParam(value = "bgImage", required = false) MultipartFile bgImage,
            @RequestParam(value = "logo", required = false) MultipartFile logo,
            @RequestParam(value = "logoRatio", defaultValue = "0.2") double logoRatio,
            @RequestParam(value = "quietZone", defaultValue = "true") boolean quietZone
    ) throws IOException {

        QrConfig.Builder builder = CustomQrGenerator.builder()
                .content(content)
                .size(size);

        // Module Drawer
        switch (moduleStyle) {
            case "circle" -> builder.moduleDrawer(new CircleModuleDrawer());
            case "rounded" -> builder.moduleDrawer(new RoundedModuleDrawer());
            default -> builder.moduleDrawer(new SquareModuleDrawer());
        }

        // Finder Drawer
        switch (finderStyle) {
            case "rounded" -> builder.finderDrawer(new RoundedFinderDrawer());
            default -> builder.finderDrawer(new SquareFinderDrawer());
        }

        // Color Provider
        ColorProvider colorProvider = switch (colorStrategy) {
            case "gradient" -> new GradientColorProvider(Color.decode(gradientStart), Color.decode(gradientEnd));
            case "image" -> {
                if (bgImage != null && !bgImage.isEmpty()) {
                    yield new ImageColorProvider(ImageIO.read(bgImage.getInputStream()));
                }
                yield new SolidColorProvider(Color.BLACK);
            }
            default -> new SolidColorProvider(Color.decode(solidColor));
        };
        builder.colorProvider(colorProvider);

        // Logo
        if (logo != null && !logo.isEmpty()) {
            builder.logo(ImageIO.read(logo.getInputStream()), logoRatio, quietZone);
        }

        BufferedImage qrImage = CustomQrGenerator.fromConfig(builder.build()).generate();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(qrImage, "png", baos);
        byte[] imageBytes = baos.toByteArray();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"qr_code.png\"")
                .contentType(MediaType.IMAGE_PNG)
                .body(imageBytes);
    }
}
