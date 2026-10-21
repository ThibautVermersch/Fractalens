package fr.univartois.butinfo.fractalens.generator;

import java.awt.Color;

public class FixedColorStrategy implements IColorStrategy {

    private final int maxIterations;
    private final ColorPalette palette;
    private final Color[] customColors;

    public FixedColorStrategy(int maxIterations) {
        this(maxIterations, ColorPalette.CYBER);
    }

    public FixedColorStrategy(int maxIterations, ColorPalette palette) {
        this.maxIterations = maxIterations;
        this.palette = palette;
        this.customColors = null;
    }

    public FixedColorStrategy(int maxIterations, Color[] customColors) {
        this.maxIterations = maxIterations;
        this.palette = null;
        this.customColors = customColors;
    }

    @Override
    public Color getColor(int iterations) {
        if (iterations >= maxIterations) {
            return Color.BLACK;
        }

        if (palette != null) {
            return palette.getColor(iterations, maxIterations);
        }

        if (customColors != null && customColors.length > 0) {
            int index = (int) Math.min(customColors.length - 1, (double) iterations / maxIterations * customColors.length);
            return customColors[index];
        }

        return Color.BLACK;
    }
}
