package fr.univartois.butinfo.fractalens.complex.generator;

import java.awt.Color;
import java.io.File;

import fr.univartois.butinfo.fractalens.generator.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.univartois.butinfo.fractalens.complex.Complex;
import fr.univartois.butinfo.fractalens.complex.IComplex;
import fr.univartois.butinfo.fractalens.complex.sequence.ComplexSequenceType;
import fr.univartois.butinfo.fractalens.complex.sequence.JuliaSequence;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour {@link FractalGeneratorBuilder}.
 *
 * @author Thibaut Vermersch
 * @version 0.1.0
 */
class FractalGeneratorBuilderTest {

    /**
     * The builder under test.
     */
    private FractalGeneratorBuilder builder;

    /**
     * the path to the generated GIF file.
     */
    private static final String ROT_GIF = "test_builder_rot.gif";

    /**
     * Set up the test.
     */
    @BeforeEach
    void setUp() {
        this.builder = new FractalGeneratorBuilder();
    }

    /**
     * Test that the builder throws an exception if the required parameters are not set.
     */
    @Test
    void testBuildWithoutZoomThrowsException() {
        this.builder.withSequence(ComplexSequenceType.MANDELBROT)
                .withColorStrategy(ColorStrategyType.GRAYSCALE);

        assertThrows(IllegalStateException.class, () -> this.builder.build());
    }

    /**
     * Test that the builder throws an exception if the required parameters are not set.
     */
    @Test
    void testBuildWithoutSequenceThrowsException() {
        this.builder.withZoom(0.001)
                .withColorStrategy(ColorStrategyType.GRAYSCALE);

        assertThrows(IllegalStateException.class, () -> this.builder.build());
    }

    /**
     * Test that the builder throws an exception if the required parameters are not set.
     */
    @Test
    void testBuildWithoutColorStrategyThrowsException() {
        this.builder.withZoom(0.001)
                .withSequence(ComplexSequenceType.JULIA);

        assertThrows(IllegalStateException.class, () -> this.builder.build());
    }

    /**
     * Test that the builder throws an exception if the required parameters are not set.
     */
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

    /**
     * Test that the builder throws an exception if the required parameters are not set.
     */
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

    /**
     * Test that the builder throws an exception if the required parameters are not set.
     */
    @Test
    void testSuccessfulBuildWithColorStrategyFactory() {
        IFractalGenerator generator = this.builder
                .withZoom(0.005)
                .withSequence(ComplexSequenceType.BURNING_SHIP)
                .withColorStrategyFactory(GrayScaleStrategy::new)
                .build();

        assertNotNull(generator);
    }

    /**
     * Test that the builder throws an exception if the dimensions are invalid.
     */
    @Test
    void testInvalidDimensions() {
        assertThrows(IllegalArgumentException.class, () -> this.builder.withDimensions(0, 500));
        assertThrows(IllegalArgumentException.class, () -> this.builder.withDimensions(500, -10));
    }

    /**
     * Test that the builder throws an exception if the max iterations are invalid.
     */
    @Test
    void testInvalidMaxIterations() {
        assertThrows(IllegalArgumentException.class, () -> this.builder.withMaxIterations(0));
        assertThrows(IllegalArgumentException.class, () -> this.builder.withMaxIterations(-5));
    }

    /**
     * Test that the builder throws an exception if the zoom is invalid.
     */
    @Test
    void testInvalidZoom() {
        assertThrows(IllegalArgumentException.class, () -> this.builder.withZoom(0.0));
        assertThrows(IllegalArgumentException.class, () -> this.builder.withZoom(-0.001));
    }

    /**
     * Test that the builder throws an exception if the center is null.
     */
    @Test
    void testNullParametersThrowException() {
        assertThrows(NullPointerException.class, () -> this.builder.withCenter(null));
        assertThrows(NullPointerException.class, () -> this.builder.withOutputFile(null));
        assertThrows(NullPointerException.class, () -> this.builder.withSequence((ComplexSequenceType) null));
        assertThrows(NullPointerException.class, () -> this.builder.withColorStrategy((ColorStrategyType) null));
        assertThrows(NullPointerException.class, () -> this.builder.withColorStrategy((IColorStrategy) null));
        assertThrows(NullPointerException.class, () -> this.builder.withColorStrategyFactory(null));
    }

    // --- TESTS ROTATION ANIMATION ---

    /**
     * Test the build method with a Julia sequence and a rotation animation.
     */
    @Test
    void testBuildJuliaRotationAnimationSuccess() {
        IComplex c0 = new Complex(0.7885, 0.0);

        FractalGeneratorBuilder builder = new FractalGeneratorBuilder()
                .withDimensions(40, 40)
                .withCenter(new Complex(0.0, 0.0))
                .withZoom(0.005)
                .withMaxIterations(20)
                .withColorStrategy(ColorStrategyType.GRAYSCALE)
                .withOutputFile(ROT_GIF);

        IFractalGenerator generator = builder.buildJuliaRotationAnimation(6, c0, 60);

        assertNotNull(generator);
        AnimatedFractalGenerator animated = assertInstanceOf(AnimatedFractalGenerator.class, generator);
        assertEquals(6, animated.getGenerators().size());

        // Vérifie la création du fichier GIF
        generator.generateImage();
        generator.saveImage();

        File file = new File(ROT_GIF);
        assertTrue(file.exists());
        assertTrue(file.length() > 0);
    }

    /**
     * Test the build method with invalid parameters.
     */
    @Test
    void testBuildJuliaRotationAnimationInvalidParameters() {
        FractalGeneratorBuilder builder = new FractalGeneratorBuilder()
                .withDimensions(40, 40)
                .withCenter(new Complex(0.0, 0.0))
                .withZoom(0.005)
                .withColorStrategy(ColorStrategyType.GRAYSCALE);

        IComplex c0 = new Complex(0.7885, 0.0);

        // Frame count <= 0
        assertThrows(IllegalArgumentException.class, () -> builder.buildJuliaRotationAnimation(0, c0, 50));
        assertThrows(IllegalArgumentException.class, () -> builder.buildJuliaRotationAnimation(-2, c0, 50));

        // Delay <= 0
        assertThrows(IllegalArgumentException.class, () -> builder.buildJuliaRotationAnimation(6, c0, 0));
        assertThrows(IllegalArgumentException.class, () -> builder.buildJuliaRotationAnimation(6, c0, -30));

        // Constante c null
        assertThrows(NullPointerException.class, () -> builder.buildJuliaRotationAnimation(6, null, 50));
    }

}
