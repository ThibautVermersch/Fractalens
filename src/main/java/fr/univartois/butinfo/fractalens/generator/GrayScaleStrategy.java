package fr.univartois.butinfo.fractalens.generator;

import java.awt.Color;

public class GrayScaleStrategy implements IColorStrategy {
    private int maxIterations;

    public GrayScaleStrategy(int maxIterations) {
        this.maxIterations = maxIterations;
    }


    /**
     * Calculates the shade of gray based on the number of iterations,
     * the brightness decreases as the number of iterations increases.
     *
     * @param iterations the current number of iterations completed
     * @return a {@link Color} representing the grayscale value, or {@link Color#BLACK}
     *         if {@code iterations >= maxIterations}
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