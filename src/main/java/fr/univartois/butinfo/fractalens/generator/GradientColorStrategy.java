package fr.univartois.butinfo.fractalens.generator;

import java.awt.Color;
import java.util.Objects;

/**

 * @author Yeleen Cividino
 *
 * @version 0.1.0
 */
public class GradientColorStrategy implements IColorStrategy {

    /**
     * The maximum number of iterations, corresponding to the end color.
     */
    private final int maxIterations;

    /**
     * The color used when no iteration has been performed.
     */
    private final Color startColor;

    /**
     * The color used when the maximum number of iterations has been reached.
     */
    private final Color endColor;

    /**
     * Creates a new GradientColorStrategy.
     *
     * @param maxIterations The maximum number of iterations, corresponding to the end
     *        color.
     * @param startColor The color used when no iteration has been performed.
     * @param endColor The color used when the maximum number of iterations has been
     *        reached.
     *
     * @throws IllegalArgumentException If {@code maxIterations} is not positive.
     * @throws NullPointerException If one of the colors is {@code null}.
     */
    public GradientColorStrategy(int maxIterations, Color startColor, Color endColor) {
        if (maxIterations <= 0) {
            throw new IllegalArgumentException(
                    "The maximum number of iterations must be positive: " + maxIterations);
        }
        this.maxIterations = maxIterations;
        this.startColor = Objects.requireNonNull(startColor, "startColor must not be null");
        this.endColor = Objects.requireNonNull(endColor, "endColor must not be null");
    }

    /**
     * Computes the color of the gradient corresponding to the given number of
     * iterations.
     * The number of iterations is bounded between {@code 0} and {@code maxIterations},
     * so that the returned color always lies between the start color and the end
     * color.
     *
     * @param iterations The number of iterations performed.
     *
     * @return The color of the gradient for this number of iterations.
     */
    @Override
    public Color getColor(int iterations) {
        double ratio = computeRatio(iterations);
        int red = interpolate(startColor.getRed(), endColor.getRed(), ratio);
        int green = interpolate(startColor.getGreen(), endColor.getGreen(), ratio);
        int blue = interpolate(startColor.getBlue(), endColor.getBlue(), ratio);
        return new Color(red, green, blue);
    }

    /**
     * Computes the ratio between the number of iterations performed and the maximum
     * number of iterations, bounded between {@code 0} and {@code 1}.
     *
     * @param iterations The number of iterations performed.
     *
     * @return The ratio of iterations, in {@code [0, 1]}.
     */
    private double computeRatio(int iterations) {
        int bounded = Math.clamp(iterations, 0, maxIterations);
        return (double) bounded / maxIterations;
    }

    /**
     * Computes the value of a color component in the gradient.
     *
     * @param start The value of the component in the start color.
     * @param end The value of the component in the end color.
     * @param ratio The position in the gradient, in {@code [0, 1]}.
     *
     * @return The value of the component, rounded to the nearest integer.
     */
    private static int interpolate(int start, int end, double ratio) {
        return (int) Math.round(start + ratio * (end - start));
    }

}