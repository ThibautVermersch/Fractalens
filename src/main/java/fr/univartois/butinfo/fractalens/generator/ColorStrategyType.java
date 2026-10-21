package fr.univartois.butinfo.fractalens.generator;

import java.awt.Color;
import java.util.function.Function;

/**
 * Enumeration encapsulating the different available color strategies.
 */
public enum ColorStrategyType {

    CYBER(maxIterations -> new FixedColorStrategy(maxIterations, ColorPalette.CYBER)),
    FIRE(maxIterations -> new FixedColorStrategy(maxIterations, ColorPalette.FIRE)),
    ICE(maxIterations -> new FixedColorStrategy(maxIterations, ColorPalette.ICE)),
    RAINBOW(maxIterations -> new FixedColorStrategy(maxIterations, ColorPalette.RAINBOW)),
    SUNSET(maxIterations -> new FixedColorStrategy(maxIterations, ColorPalette.SUNSET)),
    FOREST(maxIterations -> new FixedColorStrategy(maxIterations, ColorPalette.FOREST)),
    NEON(maxIterations -> new FixedColorStrategy(maxIterations, ColorPalette.NEON)),

    GRAYSCALE(GrayScaleStrategy::new),

    GRADIENT_BLUE_YELLOW(maxIterations -> new GradientColorStrategy(maxIterations, Color.BLUE, Color.YELLOW)),
    GRADIENT_RED_CYAN(maxIterations -> new GradientColorStrategy(maxIterations, Color.RED, Color.CYAN)),
    GRADIENT_PURPLE_GREEN(maxIterations -> new GradientColorStrategy(maxIterations, new Color(75, 0, 130), Color.GREEN)),
    GRADIENT_ORANGE_PURPLE(maxIterations -> new GradientColorStrategy(maxIterations, Color.ORANGE, new Color(128, 0, 128))),
    GRADIENT_MAGENTA_LIME(maxIterations -> new GradientColorStrategy(maxIterations, Color.MAGENTA, new Color(0, 255, 0))),
    GRADIENT_FIRE_ICE(maxIterations -> new GradientColorStrategy(maxIterations, new Color(255, 69, 0), new Color(0, 255, 255))),

    INVERTED_CYBER(max -> new InvertedColorStrategy(new FixedColorStrategy(max, ColorPalette.CYBER))),
    MASK_RED_ONLY(max -> new ColorMaskStrategy(new FixedColorStrategy(max, ColorPalette.RAINBOW), true, false, false));

    /**
     * Function building the strategy instance from maxIterations.
     */
    private final Function<Integer, IColorStrategy> factory;


    /**
     * Private constructor of the enumeration.
     *
     * @param factory Function receiving maxIterations and returning the strategy.
     */
    ColorStrategyType(Function<Integer, IColorStrategy> factory) {
        this.factory = factory;
    }


    /**
     * Creates an inverted strategy based on a specific palette.
     *
     * @param maxIterations The maximum number of iterations.
     * @param palette       The color palette to use.
     * @return The inverted color strategy.
     */
    public static IColorStrategy createInverted(int maxIterations, ColorPalette palette) {
        return new InvertedColorStrategy(new FixedColorStrategy(maxIterations, palette));
    }

    /**
     * Creates an inverted strategy from another existing strategy.
     *
     * @param strategy The base strategy to invert.
     * @return The inverted color strategy.
     */
    public static IColorStrategy createInverted(IColorStrategy strategy) {
        return new InvertedColorStrategy(strategy);
    }

    /**
     * Creates a masked strategy (RGB coefficients) from a palette.
     *
     * @param maxIterations The maximum number of iterations.
     * @param palette       The color palette to use.
     * @param r             The red coefficient.
     * @param g             The green coefficient.
     * @param b             The blue coefficient.
     * @return The masked color strategy.
     */
    public static IColorStrategy createMasked(int maxIterations, ColorPalette palette, double r, double g, double b) {
        return new ColorMaskStrategy(new FixedColorStrategy(maxIterations, palette), r, g, b);
    }


    /**
     * Creates and returns the instance of {@link IColorStrategy} associated with this type.
     *
     * @param maxIterations The maximum number of iterations.
     * @return The configured instance of the color strategy.
     */
    public IColorStrategy createStrategy(int maxIterations) {
        return factory.apply(maxIterations);
    }

    /**
     * Also allows directly obtaining the factory function.
     *
     * @return The strategy creation function.
     */
    public Function<Integer, IColorStrategy> getFactory() {
        return factory;
    }
}