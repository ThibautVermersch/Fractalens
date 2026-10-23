package fr.univartois.butinfo.fractalens.complex.generator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import fr.univartois.butinfo.fractalens.generator.GradientColorStrategy;
import fr.univartois.butinfo.fractalens.generator.IColorStrategy;

/**
 * Unit tests for {@link GradientColorStrategy}.
 *
 * @author Yeleen Cividino
 *
 * @version 0.1.0
 */
class GradientColorStrategyTest {

    /**
     * The maximum number of iterations used in most tests.
     */
    private static final int MAX_ITERATIONS = 100;

    /**
     * A start color whose components all change in different directions.
     */
    private static final Color START = new Color(10, 200, 50);

    /**
     * An end color whose components all change in different directions.
     */
    private static final Color END = new Color(110, 0, 250);

    /**
     * test getColor
     */
    @Test
    void testGetColor_WhenZeroIterations_ReturnsStartColor() {
        IColorStrategy strategy = new GradientColorStrategy(MAX_ITERATIONS, START, END);

        assertEquals(START, strategy.getColor(0));
    }

    /**
     * test getColor
     */
    @Test
    void testGetColor_WhenIterationsEqualsMax_ReturnsEndColor() {
        IColorStrategy strategy = new GradientColorStrategy(MAX_ITERATIONS, START, END);

        assertEquals(END, strategy.getColor(MAX_ITERATIONS));
    }

    /**
     * test getColor
     */
    @Test
    void testGetColor_WhenHalfIterations_ReturnsMiddleColor() {
        IColorStrategy strategy = new GradientColorStrategy(MAX_ITERATIONS, START, END);

        Color color = strategy.getColor(50);

        assertEquals(60, color.getRed());
        assertEquals(100, color.getGreen());
        assertEquals(150, color.getBlue());
    }

    /**
     * test getColor
     */
    @Test
    void testGetColor_WhenQuarterIterations_AppliesGradientFormula() {
        IColorStrategy strategy = new GradientColorStrategy(MAX_ITERATIONS, START, END);

        Color color = strategy.getColor(25);

        // (r1 + p * (r2 - r1), g1 + p * (g2 - g1), b1 + p * (b2 - b1)) with p = 0.25
        assertEquals(35, color.getRed());
        assertEquals(150, color.getGreen());
        assertEquals(100, color.getBlue());
    }

    /**
     * test getColor
     */
    @Test
    void testGetColor_WhenComponentIsNotAnInteger_RoundsToNearest() {
        IColorStrategy strategy = new GradientColorStrategy(3, Color.BLACK, new Color(100, 100, 100));

        // 100 / 3 = 33.33... and 200 / 3 = 66.66...
        assertEquals(new Color(33, 33, 33), strategy.getColor(1));
        assertEquals(new Color(67, 67, 67), strategy.getColor(2));
    }

    /**
     * test getColor
     */
    @Test
    void testGetColor_WithYellowToRedGradient() {
        IColorStrategy strategy = new GradientColorStrategy(MAX_ITERATIONS, Color.YELLOW, Color.RED);

        assertEquals(Color.YELLOW, strategy.getColor(0));
        assertEquals(new Color(255, 128, 0), strategy.getColor(50));
        assertEquals(Color.RED, strategy.getColor(MAX_ITERATIONS));
    }

    /**
     * test getColor
     */
    @Test
    void testGetColor_WhenIterationsExceedsMax_ReturnsEndColor() {
        IColorStrategy strategy = new GradientColorStrategy(MAX_ITERATIONS, START, END);

        assertEquals(END, strategy.getColor(MAX_ITERATIONS + 50));
    }

    /**
     * test getColor
     */
    @Test
    void testGetColor_WhenIterationsIsNegative_ReturnsStartColor() {
        IColorStrategy strategy = new GradientColorStrategy(MAX_ITERATIONS, START, END);

        assertEquals(START, strategy.getColor(-10));
    }

    /**
     * Test method for {@link GradientColorStrategy#getColor(int)} with the same start and end color.
     */
    @Test
    void testGetColor_WhenSameStartAndEnd_AlwaysReturnsThisColor() {
        IColorStrategy strategy = new GradientColorStrategy(MAX_ITERATIONS, Color.CYAN, Color.CYAN);

        for (int iterations = 0; iterations <= MAX_ITERATIONS; iterations++) {
            assertEquals(Color.CYAN, strategy.getColor(iterations));
        }
    }

    /**
     * test getColor
     */
    @Test
    void testGetColor_ComponentsEvolveMonotonicallyFromStartToEnd() {
        IColorStrategy strategy = new GradientColorStrategy(MAX_ITERATIONS, START, END);

        Color previous = strategy.getColor(0);
        for (int iterations = 1; iterations <= MAX_ITERATIONS; iterations++) {
            Color color = strategy.getColor(iterations);

            // Red and blue increase, while green decreases.
            assertTrue(color.getRed() >= previous.getRed());
            assertTrue(color.getGreen() <= previous.getGreen());
            assertTrue(color.getBlue() >= previous.getBlue());
            previous = color;
        }
    }

    /**
     * test getColor
     */
    @Test
    void testGetColor_ReturnsOpaqueColor() {
        IColorStrategy strategy = new GradientColorStrategy(MAX_ITERATIONS,
                new Color(0, 0, 0, 0), new Color(255, 255, 255, 0));

        assertEquals(255, strategy.getColor(40).getAlpha());
    }

    /**
     * test constructor
     * @param maxIterations the maximum number of iterations
     */
    @ParameterizedTest
    @ValueSource(ints = { 0, -1, -100 })
    void testConstructor_WhenMaxIterationsIsNotPositive_ThrowsException(int maxIterations) {
        assertThrows(IllegalArgumentException.class,
                () -> new GradientColorStrategy(maxIterations, START, END));
    }

    /**
     * test constructor
     */
    @Test
    void testConstructor_WhenStartColorIsNull_ThrowsException() {
        assertThrows(NullPointerException.class,
                () -> new GradientColorStrategy(MAX_ITERATIONS, null, END));
    }

    /**
     * test constructor
     */
    @Test
    void testConstructor_WhenEndColorIsNull_ThrowsException() {
        assertThrows(NullPointerException.class,
                () -> new GradientColorStrategy(MAX_ITERATIONS, START, null));
    }

}
