package fr.univartois.butinfo.fractalens.generator;

import java.awt.Color;

public class FixedColorStrategy implements IColorStrategy {

    /**
     * The maximum number of iterations before the color is black.
     */
    private final int maxIterations;

    /**
     * The palette to use.
     */
    private final ColorPalette palette;

    /**
     * The custom colors to use.
     */
    private final Color[] customColors;

    /**
     * Constructor.
     * @param maxIterations The maximum number of iterations before the color is black.
     */
    public FixedColorStrategy(int maxIterations) {
        this(maxIterations, ColorPalette.CYBER);
    }

    /**
     * Constructor.
     * @param maxIterations The maximum number of iterations before the color is black.
     * @param palette The palette to use.
     */
    public FixedColorStrategy(int maxIterations, ColorPalette palette) {
        this.maxIterations = maxIterations;
        this.palette = palette;
        this.customColors = null;
    }

    /**
     * Constructor.
     * @param maxIterations The maximum number of iterations before the color is black.
     * @param customColors The custom colors to use.
     */
    public FixedColorStrategy(int maxIterations, Color[] customColors) {
        this.maxIterations = maxIterations;
        this.palette = null;
        this.customColors = customColors;
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

        if (palette != null) {
            return palette.getColor(iterations, maxIterations);
        }

        if (customColors != null && customColors.length > 0) {
            int index = Math.min(customColors.length - 1, (int) ((double) iterations / maxIterations * customColors.length));
            return customColors[index];
        }

        return Color.BLACK;
    }
}
