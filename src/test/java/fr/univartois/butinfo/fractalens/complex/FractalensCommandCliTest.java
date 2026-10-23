package fr.univartois.butinfo.fractalens.complex;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import fr.univartois.butinfo.fractalens.FractalensCommandCli;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import picocli.CommandLine;

/**
 * Unit tests for the {@link FractalensCommandCli} command line interface.
 *
 * @author Thibaut Vermersch
 * @version 0.1.0
 */
class FractalensCommandCliTest {

    private static final String FILE_STATIC = "test_cli_static.png";
    private static final String FILE_ZOOM = "test_cli_zoom.gif";
    private static final String FILE_ROT = "test_cli_rot.gif";

    /**
     * Cleans up generated test artifacts after each test run.
     */
    @AfterEach
    void tearDown() {
        for (String filename : new String[]{FILE_STATIC, FILE_ZOOM, FILE_ROT}) {
            File file = new File(filename);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    /**
     * Tests the values and lookup methods of the GenerationType enumeration.
     */
    @Test
    void testGenerationTypeEnumCoverage() {
        for (FractalensCommandCli.GenerationType type : FractalensCommandCli.GenerationType.values()) {
            assertNotNull(type);
            assertEquals(type, FractalensCommandCli.GenerationType.valueOf(type.name()));
        }
    }

    /**
     * Tests standard static image generation using Picocli arguments.
     */
    @Test
    void testExecuteDefaultStatic() {
        CommandLine cl = new CommandLine(new FractalensCommandCli());
        String[] args = {
                "-f", "MANDELBROT",
                "-t", "STATIC",
                "-w", "10",
                "-h", "10",
                "-n", "2",
                "-s", "0.01",
                "-x", "0.0",
                "-y", "0.0",
                "-p", "GRAYSCALE",
                "-o", FILE_STATIC
        };

        int exitCode = cl.execute(args);

        assertEquals(0, exitCode);
        File file = new File(FILE_STATIC);
        assertTrue(file.exists());
        assertTrue(file.length() > 0);
    }

    /**
     * Tests zoom animation generation using Picocli arguments.
     */
    @Test
    void testExecuteZoomAnimation() {
        CommandLine cl = new CommandLine(new FractalensCommandCli());
        String[] args = {
                "-f", "MANDELBROT",
                "-t", "ZOOM",
                "-w", "10",
                "-h", "10",
                "-n", "2",
                "-i", "2",
                "-o", FILE_ZOOM
        };

        int exitCode = cl.execute(args);

        assertEquals(0, exitCode);
        File file = new File(FILE_ZOOM);
        assertTrue(file.exists());
        assertTrue(file.length() > 0);
    }

    /**
     * Tests Julia set rotation animation generation using Picocli arguments.
     */
    @Test
    void testExecuteRotationAnimation() {
        CommandLine cl = new CommandLine(new FractalensCommandCli());
        String[] args = {
                "-f", "JULIA",
                "-t", "ROTATION",
                "-w", "10",
                "-h", "10",
                "-n", "2",
                "-i", "2",
                "-o", FILE_ROT
        };

        int exitCode = cl.execute(args);

        assertEquals(0, exitCode);
        File file = new File(FILE_ROT);
        assertTrue(file.exists());
        assertTrue(file.length() > 0);
    }
}
