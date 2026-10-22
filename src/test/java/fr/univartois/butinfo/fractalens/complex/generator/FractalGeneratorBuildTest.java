package fr.univartois.butinfo.fractalens.complex.generator;

import fr.univartois.butinfo.fractalens.complex.Complex;
import fr.univartois.butinfo.fractalens.complex.sequence.ComplexSequenceType;
import fr.univartois.butinfo.fractalens.generator.ColorStrategyType;
import fr.univartois.butinfo.fractalens.generator.FractalGeneratorBuilder;
import fr.univartois.butinfo.fractalens.generator.IFractalGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FractalGeneratorBuildTest {
    @Test
    void testBuildZoomAnimationSuccess() {
        FractalGeneratorBuilder builder = new FractalGeneratorBuilder()
                .withDimensions(100, 100)
                .withCenter(new Complex(0.0, 0.0))
                .withZoom(0.01)
                .withMaxIterations(50)
                .withSequence(ComplexSequenceType.MANDELBROT)
                .withColorStrategy(ColorStrategyType.GRAYSCALE)
                .withOutputFile("anim_test.gif");

        IFractalGenerator generator = builder.buildZoomAnimation(5, 0.8, 100);

        assertNotNull(generator);
        if (generator instanceof AnimatedFractalGenerator animated) {
            assertEquals(5, animated.getGenerators().size());
        }
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
