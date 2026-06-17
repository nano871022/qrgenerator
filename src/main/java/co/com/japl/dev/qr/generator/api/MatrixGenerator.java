package co.com.japl.dev.qr.generator.api;

import com.google.zxing.WriterException;

/**
 * Interface for matrix generation abstraction.
 */
public interface MatrixGenerator {
    /**
     * Generates a boolean 2D array representation of the QR code.
     *
     * @param content The text content to encode.
     * @param size    The physical size in pixels.
     * @return A boolean matrix (true for active bits).
     * @throws WriterException if matrix generation fails.
     */
    boolean[][] generate(String content, int size) throws WriterException;
}
