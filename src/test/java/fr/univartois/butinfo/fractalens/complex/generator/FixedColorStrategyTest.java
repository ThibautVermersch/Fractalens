package fr.univartois.butinfo.fractalens.complex.generator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.awt.Color;

import fr.univartois.butinfo.fractalens.generator.ColorPalette;
import fr.univartois.butinfo.fractalens.generator.FixedColorStrategy;

class FixedColorStrategyTest {

    @Test
    void testGetColorMaxIterations() {
        FixedColorStrategy strategy = new FixedColorStrategy(100, ColorPalette.RAINBOW);
        assertEquals(Color.BLACK, strategy.getColor(100));
        assertEquals(Color.BLACK, strategy.getColor(150));
    }

    @Test
    void testGetColorStartOfRainbow() {
        FixedColorStrategy strategy = new FixedColorStrategy(100, ColorPalette.RAINBOW);
        Color expected = new Color(200, 0, 0);
        assertEquals(expected, strategy.getColor(0));
    }

    @Test
    void testGetColorMiddleOfRainbow() {
        FixedColorStrategy strategy = new FixedColorStrategy(100, ColorPalette.RAINBOW);
        Color expected = new Color(0, 180, 0);
        assertEquals(expected, strategy.getColor(50));
    }

    @Test
    void testGetColorEndOfRainbow() {
        FixedColorStrategy strategy = new FixedColorStrategy(100, ColorPalette.RAINBOW);
        Color expected = new Color(0, 0, 200);
        assertEquals(expected, strategy.getColor(90));
    }

    @Test
    void testGetColorBoundaryValues() {
        FixedColorStrategy strategy = new FixedColorStrategy(10, ColorPalette.RAINBOW);
        assertEquals(new Color(200, 0, 0), strategy.getColor(0));
        assertEquals(new Color(0, 0, 200), strategy.getColor(9));
        assertEquals(Color.BLACK, strategy.getColor(10));
    }
}