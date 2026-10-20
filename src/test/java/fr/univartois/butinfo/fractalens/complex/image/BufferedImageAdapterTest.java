package fr.univartois.butinfo.fractalens.complex.image;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import fr.univartois.butinfo.fractalens.image.BufferedImageAdaptater;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link BufferedImageAdaptater} class.
 */
public class BufferedImageAdapterTest {

    /**
     * The width used for test images.
     */
    private static final int WIDTH = 100;

    /**
     * The height used for test images.
     */
    private static final int HEIGHT = 100;

    /**
     * The adaptater to test.
     */
    private BufferedImageAdaptater adaptater;

    /**
     * Set up the test.
     */
    @BeforeEach
    void setUp() {
        adaptater = new BufferedImageAdaptater(WIDTH, HEIGHT);
    }

    /**
     * Test that the dimensions of the adapted image are correct.
     */
    @Test
    void testDimensions() {
        assertEquals(WIDTH, adaptater.getWidth());
        assertEquals(HEIGHT, adaptater.getHeight());
    }

    /**
     * Test that the color of a pixel can be set and retrieved.
     */
    @Test
    void testSetColor() {
        BufferedImageAdaptater adapter = new BufferedImageAdaptater(800, 600);
        Color black = Color.BLACK;
        adapter.setColor(100, 200, black);
        int rgb = adapter.getBufferedImage().getRGB(200, 100);

        assertEquals(black.getRGB(), rgb);
    }

    /**
     * Test that the image can be saved to a file.
     * @throws IOException If an I/O error occurs when writing the file.
     */
    @Test
    void testSave() throws IOException {
        BufferedImageAdaptater adapter = new BufferedImageAdaptater(800, 600);
        File tempFile = File.createTempFile("test", ".jpg");
        tempFile.deleteOnExit();
        adapter.saveAs(tempFile.getAbsolutePath());
        assertTrue(tempFile.exists());
        assertTrue(tempFile.canRead());
        assertTrue(tempFile.length() > 0);
    }

    /**
     * Test that saving an image without an extension uses the default format.
     * @throws IOException If an I/O error occurs when writing the file.
     */
    @Test
    void testSaveWithoutExtensionUsesDefaultFormat() throws IOException {
        File tempFile = File.createTempFile("test_no_ext", "");
        tempFile.deleteOnExit();
        adaptater.saveAs(tempFile.getAbsolutePath());
        assertTrue(tempFile.exists());
        assertTrue(tempFile.length() > 0);
    }

    /**
     * Test that an exception is thrown when saving an image with an invalid format.
     */
    @Test
    void testSaveWithInvalidFormatThrowsException() {
        File tempFile = new File("test.formatinconnu1234");
        tempFile.deleteOnExit();

        assertThrows(IOException.class, () -> {
            adaptater.saveAs(tempFile.getAbsolutePath());
        });
    }
}
