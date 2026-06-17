package co.com.japl.dev.qr.generator.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import com.google.zxing.WriterException;

class ZxingMatrixGeneratorTest {

    @Test
    void testMatrixSizeForSmallContent() throws WriterException {
        ZxingMatrixGenerator generator = new ZxingMatrixGenerator();
        // "Hello" with Error Correction Level H should result in a Version 1 QR (21x21)
        boolean[][] matrix = generator.generate("Hello", 400);
        assertEquals(21, matrix.length);
        assertEquals(21, matrix[0].length);
    }

    @Test
    void testFinderPatternsPresence() throws WriterException {
        ZxingMatrixGenerator generator = new ZxingMatrixGenerator();
        boolean[][] matrix = generator.generate("https://japl.com.co", 400);
        int m = matrix.length;

        // Top-left finder pattern
        assertTrue(matrix[0][0]);
        assertTrue(matrix[0][6]);
        assertTrue(matrix[6][0]);
        assertTrue(matrix[6][6]);

        // Top-right finder pattern
        assertTrue(matrix[0][m - 7]);
        assertTrue(matrix[0][m - 1]);
        assertTrue(matrix[6][m - 7]);
        assertTrue(matrix[6][m - 1]);

        // Bottom-left finder pattern
        assertTrue(matrix[m - 7][0]);
        assertTrue(matrix[m - 1][0]);
        assertTrue(matrix[m - 7][6]);
        assertTrue(matrix[m - 1][6]);
    }
}
