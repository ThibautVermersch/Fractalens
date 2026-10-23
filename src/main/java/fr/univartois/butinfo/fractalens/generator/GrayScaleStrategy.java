package fr.univartois.butinfo.fractalens.generator;

import java.awt.Color;

/**
 * Gray scale color strategy.
 */
public class GrayScaleStrategy implements IColorStrategy {

    /**
     * The maximum number of iterations before the color is black.
     */
    private int maxIterations;

    /**
     * Constructor.
     * @param maxIterations The maximum number of iterations before the color is black.
     */
    public GrayScaleStrategy(int maxIterations) {
        this.maxIterations = maxIterations;
    }

    /**
     * Returns the color of the pixel at the given iteration
     * @param iterations The number of iterations performed.
     * @return The color of the pixel at the given iteration
     */
    @Override
    public Color getColor(int iterations) {
        if (iterations >= maxIterations) {
            return Color.BLACK;
        }

        double r = (double) iterations / maxIterations;

        float brightness = (float) (1. - r);

        return new Color(brightness, brightness, brightness);
    }
}
