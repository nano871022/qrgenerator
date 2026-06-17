package co.com.japl.dev.qr.generator.api;

import com.google.zxing.WriterException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import com.google.zxing.qrcode.encoder.Encoder;
import com.google.zxing.qrcode.encoder.QRCode;

/**
 * Implementation of MatrixGenerator using Google ZXing.
 */
public class ZxingMatrixGenerator implements MatrixGenerator {

    @Override
    public boolean[][] generate(String content, int size) throws WriterException {
        // Note: size is ignored here as we generate the logical QR matrix.
        // Scaling to physical size happens during the rendering phase (T5).
        QRCode qrCode = Encoder.encode(content, ErrorCorrectionLevel.H, null);
        ByteMatrix byteMatrix = qrCode.getMatrix();
        int width = byteMatrix.getWidth();
        int height = byteMatrix.getHeight();
        boolean[][] matrix = new boolean[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                matrix[y][x] = byteMatrix.get(x, y) == 1;
            }
        }
        return matrix;
    }
}
