package fr.univartois.butinfo.fractalens.complex.generator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;

import fr.univartois.butinfo.fractalens.generator.AnimatedFractalGenerator;
import fr.univartois.butinfo.fractalens.generator.ColorStrategyType;
import fr.univartois.butinfo.fractalens.generator.FractalGeneratorBuilder;
import fr.univartois.butinfo.fractalens.generator.IFractalGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import fr.univartois.butinfo.fractalens.complex.Complex;
import fr.univartois.butinfo.fractalens.complex.sequence.ComplexSequenceType;

/**
 * Tests unitaires ciblant les animations construites par {@link FractalGeneratorBuilder}.
 *
 * @version 0.1.0
 */
class FractalGeneratorBuildTest {

    /**
     * The name of the test GIF file.
     */
    private static final String TEST_GIF = "anim_test.gif";

    /**
     * Delete the test GIF file after each test.
     */
    @AfterEach
    void tearDown() {
        File file = new File(TEST_GIF);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * Test the build method with valid parameters.
     */
    @Test
    void testBuildZoomAnimationSuccess() {
        FractalGeneratorBuilder builder = new FractalGeneratorBuilder()
                .withDimensions(100, 100)
                .withCenter(new Complex(0.0, 0.0))
                .withZoom(0.01)
                .withMaxIterations(50)
                .withSequence(ComplexSequenceType.MANDELBROT)
                .withColorStrategy(ColorStrategyType.GRAYSCALE)
                .withOutputFile(TEST_GIF);

        IFractalGenerator generator = builder.buildZoomAnimation(5, 0.8, 100);

        assertNotNull(generator);
        AnimatedFractalGenerator animated = assertInstanceOf(
                AnimatedFractalGenerator.class, generator);
        assertEquals(5, animated.getGenerators().size());
    }

    /**
     * Test the build method with invalid parameters.
     */
    @Test
    void testBuildZoomAnimationInvalidParameters() {
        FractalGeneratorBuilder builder = new FractalGeneratorBuilder()
                .withDimensions(100, 100)
                .withCenter(new Complex(0.0, 0.0))
                .withZoom(0.01)
                .withSequence(ComplexSequenceType.MANDELBROT)
                .withColorStrategy(ColorStrategyType.GRAYSCALE);

        // Frame count <= 0
        assertThrows(IllegalArgumentException.class, () -> builder.buildZoomAnimation(0, 0.8, 100));
        assertThrows(IllegalArgumentException.class, () -> builder.buildZoomAnimation(-5, 0.8, 100));

        // Frame count <= 0
        assertThrows(IllegalArgumentException.class, () -> builder.buildZoomAnimation(10, 0.0, 100));
        assertThrows(IllegalArgumentException.class, () -> builder.buildZoomAnimation(10, 1.0, 100));
        assertThrows(IllegalArgumentException.class, () -> builder.buildZoomAnimation(10, 1.5, 100));
        assertThrows(IllegalArgumentException.class, () -> builder.buildZoomAnimation(10, -0.5, 100));

        // Delay <= 0
        assertThrows(IllegalArgumentException.class, () -> builder.buildZoomAnimation(10, 0.8, 0));
        assertThrows(IllegalArgumentException.class, () -> builder.buildZoomAnimation(10, 0.8, -50));
    }

    /**
     * Test the build method without center.
     */
    @Test
    void testBuildZoomAnimationWithoutZoomThrowsException() {
        FractalGeneratorBuilder builder = new FractalGeneratorBuilder()
                .withDimensions(100, 100)
                .withSequence(ComplexSequenceType.MANDELBROT)
                .withColorStrategy(ColorStrategyType.GRAYSCALE);

        assertThrows(IllegalStateException.class, () -> builder.buildZoomAnimation(10, 0.8, 100));
    }
}
