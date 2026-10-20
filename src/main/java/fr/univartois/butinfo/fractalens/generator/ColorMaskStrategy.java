package fr.univartois.butinfo.fractalens.generator;

import java.awt.*;

public class ColorMaskStrategy implements IColorStrategy {

    /**
     * The color strategy to decorate
     */
    private final IColorStrategy decorated;

    /**
     * The color used when no iteration has been performed.
     */
    private final double redCoefficient;

    /**
     * The color used when the maximum number of iterations has been reached.
     */
    private final double greenCoefficient;

    /**
     * The color used when the maximum number of iterations has been reached.
     */
    private final double blueCoefficient;

    /**
     * Constructor
     * @param decorated
     * @param keepRed
     * @param keepGreen
     * @param keepBlue
     */
    public ColorMaskStrategy(IColorStrategy decorated, boolean keepRed, boolean keepGreen, boolean keepBlue) {
        this(decorated, keepRed ? 1.0 : 0.0, keepGreen ? 1.0 : 0.0, keepBlue ? 1.0 : 0.0);
    }

    public ColorMaskStrategy(IColorStrategy decorated, double redCoefficient,
            double greenCoefficient, double blueCoefficient) {
        checkCoefficient(redCoefficient);
        checkCoefficient(greenCoefficient);
        checkCoefficient(blueCoefficient);
        this.decorated = decorated;
        this.redCoefficient = redCoefficient;
        this.greenCoefficient = greenCoefficient;
        this.blueCoefficient = blueCoefficient;
    }

    private static void checkCoefficient(double coefficient) {
        if (!(coefficient >= 0.0 && coefficient <= 1.0)) {
            throw new IllegalArgumentException(
                    "A coefficient must be between 0.0 and 1.0: " + coefficient);
        }
    }

    @Override
    public Color getColor(int iterations) {
        Color baseColor = decorated.getColor(iterations);
        int newR = (int) Math.round(baseColor.getRed() * redCoefficient);
        int newG = (int) Math.round(baseColor.getGreen() * greenCoefficient);
        int newB = (int) Math.round(baseColor.getBlue() * blueCoefficient);
        return new Color(newR, newG, newB);
    }
}
