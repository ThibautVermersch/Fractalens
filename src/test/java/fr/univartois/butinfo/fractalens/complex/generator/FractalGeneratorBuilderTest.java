package fr.univartois.butinfo.fractalens.complex.generator;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.Color;

import fr.univartois.butinfo.fractalens.generator.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.univartois.butinfo.fractalens.complex.Complex;
import fr.univartois.butinfo.fractalens.complex.IComplex;
import fr.univartois.butinfo.fractalens.complex.sequence.ComplexSequenceType;
import fr.univartois.butinfo.fractalens.complex.sequence.JuliaSequence;

/**
 * Tests unitaires pour {@link FractalGeneratorBuilder}.
 *
 * @author Thibaut Vermersch
 * @version 0.1.0
 */
class FractalGeneratorBuilderTest {

    private FractalGeneratorBuilder builder;

    @BeforeEach
    void setUp() {
        this.builder = new FractalGeneratorBuilder();
    }

    @Test
    void testBuildWithoutZoomThrowsException() {
        this.builder.withSequence(ComplexSequenceType.MANDELBROT)
                .withColorStrategy(ColorStrategyType.GRAYSCALE);

        assertThrows(IllegalStateException.class, () -> this.builder.build());
    }

    @Test
    void testBuildWithoutSequenceThrowsException() {
        this.builder.withZoom(0.001)
                .withColorStrategy(ColorStrategyType.GRAYSCALE);

        assertThrows(IllegalStateException.class, () -> this.builder.build());
    }

    @Test
    void testBuildWithoutColorStrategyThrowsException() {
        this.builder.withZoom(0.001)
                .withSequence(ComplexSequenceType.JULIA);

        assertThrows(IllegalStateException.class, () -> this.builder.build());
    }

    @Test
    void testSuccessfulBuildWithEnums() {
        IFractalGenerator generator = this.builder
                .withDimensions(800, 600)
                .withMaxIterations(200)
                .withZoom(0.002)
                .withCenter(new Complex(-0.75, 0.0))
                .withSequence(ComplexSequenceType.MANDELBROT)
                .withColorStrategy(ColorStrategyType.GRADIENT_BLUE_YELLOW)
                .withOutputFile("test_enums.png")
                .build();

        assertNotNull(generator);
    }

    @Test
    void testSuccessfulBuildWithRawObjects() {
        int max = 150;
        IComplex c = new Complex(0.3, 0.5);
        IColorStrategy strategy = new GradientColorStrategy(max, Color.RED, Color.YELLOW);

        IFractalGenerator generator = this.builder
                .withDimensions(600, 400)
                .withMaxIterations(max)
                .withZoom(0.002)
                .withCenter(new Complex(-0.7, 0.2))
                .withSequence((z0, m) -> new JuliaSequence(z0, c, m))
                .withColorStrategy(strategy)
                .withOutputFile("test_raw.png")
                .build();

        assertNotNull(generator);
    }

    @Test
    void testSuccessfulBuildWithColorStrategyFactory() {
        IFractalGenerator generator = this.builder
                .withZoom(0.005)
                .withSequence(ComplexSequenceType.BURNING_SHIP)
                .withColorStrategyFactory(GrayScaleStrategy::new)
                .build();

        assertNotNull(generator);
    }

    @Test
    void testInvalidDimensions() {
        assertThrows(IllegalArgumentException.class, () -> this.builder.withDimensions(0, 500));
        assertThrows(IllegalArgumentException.class, () -> this.builder.withDimensions(500, -10));
    }

    @Test
    void testInvalidMaxIterations() {
        assertThrows(IllegalArgumentException.class, () -> this.builder.withMaxIterations(0));
        assertThrows(IllegalArgumentException.class, () -> this.builder.withMaxIterations(-5));
    }

    @Test
    void testInvalidZoom() {
        assertThrows(IllegalArgumentException.class, () -> this.builder.withZoom(0.0));
        assertThrows(IllegalArgumentException.class, () -> this.builder.withZoom(-0.001));
    }

    @Test
    void testNullParametersThrowException() {
        assertThrows(NullPointerException.class, () -> this.builder.withCenter(null));
        assertThrows(NullPointerException.class, () -> this.builder.withOutputFile(null));
        assertThrows(NullPointerException.class, () -> this.builder.withSequence((ComplexSequenceType) null));
        assertThrows(NullPointerException.class, () -> this.builder.withColorStrategy((ColorStrategyType) null));
        assertThrows(NullPointerException.class, () -> this.builder.withColorStrategy((IColorStrategy) null));
        assertThrows(NullPointerException.class, () -> this.builder.withColorStrategyFactory(null));
    }
}
