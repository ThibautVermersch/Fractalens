package fr.univartois.butinfo.fractalens.generator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.awt.Color;

class GrayScaleStrategyTest {

    @Test
    void testGetColor_WhenIterationsEqualsMax_ReturnsBlack() {
        int maxIterations = 100;
        GrayScaleStrategy strategy = new GrayScaleStrategy(maxIterations);

        Color color = strategy.getColor(maxIterations);

        assertEquals(Color.BLACK, color);
    }

    @Test
    void testGetColor_WhenIterationsExceedsMax_ReturnsBlack() {
        int maxIterations = 100;
        GrayScaleStrategy strategy = new GrayScaleStrategy(maxIterations);

        Color color = strategy.getColor(150);

        assertEquals(Color.BLACK, color);
    }

    @Test
    void testGetColor_WhenZeroIterations_ReturnsWhite() {
        int maxIterations = 100;
        GrayScaleStrategy strategy = new GrayScaleStrategy(maxIterations);

        Color color = strategy.getColor(0);

        assertEquals(255, color.getRed());
        assertEquals(255, color.getGreen());
        assertEquals(255, color.getBlue());
    }

    @Test
    void testGetColor_IntermediateValue_ReturnsGrayShade() {
        int maxIterations = 100;
        GrayScaleStrategy strategy = new GrayScaleStrategy(maxIterations);

        Color color = strategy.getColor(50);

        assertEquals(color.getGreen(), color.getRed());
        assertEquals(color.getBlue(), color.getRed());

        assertEquals(128, color.getRed());
    }
}