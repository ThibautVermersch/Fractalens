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
 * @author Thibaut Vermersch
 * @version 0.1.0
 */
class FractalGeneratorBuildTest {

    private static final String TEST_GIF = "anim_test.gif";

    @AfterEach
    void tearDown() {
        File file = new File(TEST_GIF);
        if (file.exists()) {
            file.delete();
        }
    }

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

    @Test
    void testBuildZoomAnimationInvalidParameters() {
        FractalGeneratorBuilder builder = new FractalGeneratorBuilder()
                .withDimensions(100, 100)
                .withCenter(new Complex(0.0, 0.0))
                .withZoom(0.01)
                .withSequence(ComplexSequenceType.MANDELBROT)
                .withColorStrategy(ColorStrategyType.GRAYSCALE);

        // Nombre d'images <= 0
        assertThrows(IllegalArgumentException.class, () -> builder.buildZoomAnimation(0, 0.8, 100));
        assertThrows(IllegalArgumentException.class, () -> builder.buildZoomAnimation(-5, 0.8, 100));

        // Facteur de zoom hors intervalle ]0, 1[
        assertThrows(IllegalArgumentException.class, () -> builder.buildZoomAnimation(10, 0.0, 100));
        assertThrows(IllegalArgumentException.class, () -> builder.buildZoomAnimation(10, 1.0, 100));
        assertThrows(IllegalArgumentException.class, () -> builder.buildZoomAnimation(10, 1.5, 100));
        assertThrows(IllegalArgumentException.class, () -> builder.buildZoomAnimation(10, -0.5, 100));

        // Délai <= 0
        assertThrows(IllegalArgumentException.class, () -> builder.buildZoomAnimation(10, 0.8, 0));
        assertThrows(IllegalArgumentException.class, () -> builder.buildZoomAnimation(10, 0.8, -50));
    }

    @Test
    void testBuildZoomAnimationWithoutZoomThrowsException() {
        FractalGeneratorBuilder builder = new FractalGeneratorBuilder()
                .withDimensions(100, 100)
                .withSequence(ComplexSequenceType.MANDELBROT)
                .withColorStrategy(ColorStrategyType.GRAYSCALE);

        assertThrows(IllegalStateException.class, () -> builder.buildZoomAnimation(10, 0.8, 100));
    }
}
