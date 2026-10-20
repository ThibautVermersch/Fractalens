package fr.univartois.butinfo.fractalens.complex.generator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import fr.univartois.butinfo.fractalens.generator.ColorMaskStrategy;
import fr.univartois.butinfo.fractalens.generator.IColorStrategy;
import org.junit.jupiter.api.Test;

/**
 * The ColorMaskStrategyTest tests the class {@link ColorMaskStrategy}.
 * <p>
 * It relies on fake decorated strategies, so that the decorator is tested independently
 * of the actual color strategies.
 */
class ColorMaskStrategyTest {

    /**
     * Creates a strategy with the given channels kept, decorating a strategy that
     * always gives white.
     *
     * @param red Whether to keep the red channel.
     * @param green Whether to keep the green channel.
     * @param blue Whether to keep the blue channel.
     *
     * @return The created strategy.
     */
    private static IColorStrategy maskOnWhite(boolean red, boolean green, boolean blue) {
        return new ColorMaskStrategy(i -> Color.WHITE, red, green, blue);
    }

    /**
     * Test method for {@link ColorMaskStrategy#getColor(int)}.
     * Each channel must be isolated when it is the only one kept.
     */
    @Test
    void testGetColorIsolatesEachChannel() {
        assertEquals(new Color(255, 0, 0), maskOnWhite(true, false, false).getColor(0));
        assertEquals(new Color(0, 255, 0), maskOnWhite(false, true, false).getColor(0));
        assertEquals(new Color(0, 0, 255), maskOnWhite(false, false, true).getColor(0));
    }

    /**
     * Test method for {@link ColorMaskStrategy#getColor(int)}.
     * Keeping two channels must give a combination of them.
     */
    @Test
    void testGetColorCombinesChannels() {
        assertEquals(new Color(0, 255, 255), maskOnWhite(false, true, true).getColor(0));
        assertEquals(new Color(255, 0, 255), maskOnWhite(true, false, true).getColor(0));
        assertEquals(new Color(255, 255, 0), maskOnWhite(true, true, false).getColor(0));
    }

    /**
     * Test method for {@link ColorMaskStrategy#getColor(int)}.
     * Keeping every channel leaves the color unchanged, and keeping none gives black.
     */
    @Test
    void testGetColorKeepingAllOrNoChannel() {
        IColorStrategy base = i -> new Color(10, 100, 200);

        assertEquals(new Color(10, 100, 200),
                new ColorMaskStrategy(base, true, true, true).getColor(0));
        assertEquals(Color.BLACK,
                new ColorMaskStrategy(base, false, false, false).getColor(0));
    }

    /**
     * Test method for {@link ColorMaskStrategy#getColor(int)}.
     * The filter must only keep the original value of the kept channels.
     */
    @Test
    void testGetColorKeepsOriginalValues() {
        IColorStrategy base = i -> new Color(10, 100, 200);

        assertEquals(new Color(0, 100, 0),
                new ColorMaskStrategy(base, false, true, false).getColor(0));
    }

    /**
     * Test method for {@link ColorMaskStrategy#getColor(int)} with coefficients.
     * Each channel must be scaled by its own coefficient.
     */
    @Test
    void testGetColorWithCoefficients() {
        IColorStrategy base = i -> new Color(200, 100, 50);
        IColorStrategy strategy = new ColorMaskStrategy(base, 1.0, 0.5, 0.0);

        assertEquals(new Color(200, 50, 0), strategy.getColor(0));
    }

    /**
     * Test method for {@link ColorMaskStrategy#getColor(int)} with coefficients.
     * The result must be rounded to the nearest integer.
     */
    @Test
    void testGetColorWithCoefficientsRounds() {
        IColorStrategy strategy = new ColorMaskStrategy(i -> Color.WHITE, 0.5, 0.5, 0.5);

        assertEquals(new Color(128, 128, 128), strategy.getColor(0));
    }

    /**
     * Test method for {@link ColorMaskStrategy#getColor(int)}.
     * The number of iterations must be given to the decorated strategy.
     */
    @Test
    void testGetColorPassesIterationsToDecorated() {
        List<Integer> received = new ArrayList<>();
        IColorStrategy strategy = new ColorMaskStrategy(i -> {
            received.add(i);
            return Color.WHITE;
        }, true, true, true);

        strategy.getColor(42);

        assertEquals(List.of(42), received);
    }

    /**
     * Test method for
     * {@link ColorMaskStrategy#ColorMaskStrategy(IColorStrategy, double, double, double)}.
     * Coefficients outside of [0.0, 1.0] must be rejected.
     */
    @Test
    void testConstructorRejectsInvalidCoefficients() {
        IColorStrategy base = i -> Color.WHITE;

        assertThrows(IllegalArgumentException.class,
                () -> new ColorMaskStrategy(base, -0.1, 0.5, 0.5));
        assertThrows(IllegalArgumentException.class,
                () -> new ColorMaskStrategy(base, 0.5, 1.1, 0.5));
        assertThrows(IllegalArgumentException.class,
                () -> new ColorMaskStrategy(base, 0.5, 0.5, Double.NaN));
    }

}
