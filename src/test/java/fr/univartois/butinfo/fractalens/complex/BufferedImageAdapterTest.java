package fr.univartois.butinfo.fractalens.complex;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import javax.imageio.ImageIO;

import fr.univartois.butinfo.fractalens.image.BufferedImageAdaptater;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class BufferedImageAdapterTest {

    private static final int WIDTH = 100;
    private static final int HEIGHT = 100;
    private BufferedImageAdaptater adaptater;

    @BeforeEach
    void setUp() {
        adaptater = new BufferedImageAdaptater(WIDTH, HEIGHT);
    }

    @Test
    void testDimensions() {
        assertEquals(WIDTH, adaptater.getWidth());
        assertEquals(HEIGHT, adaptater.getHeight());
    }

    @Test
    void testSetColor() {
        BufferedImageAdaptater adapter = new BufferedImageAdaptater(800, 600);
        Color black = Color.BLACK;
        adapter.setColor(100, 200, black);
        int rgb = adapter.getBufferedImage().getRGB(200, 100);

        assertEquals(black.getRGB(), rgb);
    }
    @Test
    void testSave() throws IOException {
        BufferedImageAdaptater adapter = new BufferedImageAdaptater(800, 600);
        File tempFile = File.createTempFile("test", ".png");
        tempFile.deleteOnExit();
        adapter.saveAs(tempFile.getAbsolutePath());
        assertTrue(tempFile.exists());
        assertTrue(tempFile.canRead());
        assertTrue(tempFile.length() > 0);
    }
}
