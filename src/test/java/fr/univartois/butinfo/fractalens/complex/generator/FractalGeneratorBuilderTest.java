package fr.univartois.butinfo.fractalens.generator;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.Color;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.univartois.butinfo.fractalens.complex.Complex;
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
        this.builder.withSequence((z0, max) -> new JuliaSequence(z0, new Complex(0.3, 0.5), max))
                .withColorStrategy(new GradientColorStrategy(100, Color.BLACK, Color.WHITE));

        assertThrows(IllegalStateException.class, () -> this.builder.build());
    }

    @Test
    void testBuildWithoutSequenceThrowsException() {
        this.builder.withZoom(0.001)
                .withColorStrategy(new GradientColorStrategy(100, Color.BLACK, Color.WHITE));

        assertThrows(IllegalStateException.class, () -> this.builder.build());
    }

    @Test
    void testBuildWithoutColorStrategyThrowsException() {
        this.builder.withZoom(0.001)
                .withSequence((z0, max) -> new JuliaSequence(z0, new Complex(0.3, 0.5), max));

        assertThrows(IllegalStateException.class, () -> this.builder.build());
    }

    @Test
    void testSuccessfulBuildWithRawObjects() {
        IColorStrategy strategy = new GradientColorStrategy(100, Color.RED, Color.YELLOW);

        IFractalGenerator generator = this.builder
                .withDimensions(600, 400)
                .withMaxIterations(150)
                .withZoom(0.002)
                .withCenter(new Complex(-0.7, 0.2))
                .withSequence((z0, max) -> new JuliaSequence(z0, new Complex(0.3, 0.5), max))
                .withColorStrategy(strategy)
                .withOutputFile("test_raw.png")
                .build();

        assertNotNull(generator);
    }

    @Test
    void testSuccessfulBuildWithColorStrategyFactory() {
        IFractalGenerator generator = this.builder
                .withZoom(0.005)
                .withSequence((z0, max) -> new JuliaSequence(z0, new Complex(0.0, 0.0), max))
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
        assertThrows(NullPointerException.class, () -> this.builder.withSequence(null));
        assertThrows(NullPointerException.class, () -> this.builder.withColorStrategy(null));
        assertThrows(NullPointerException.class, () -> this.builder.withColorStrategyFactory(null));
    }
}
