package fr.univartois.butinfo.fractalens.generator;

import java.awt.*;

public class InvertedColorStrategy implements IColorStrategy{

    /**
     * The color strategy to decorate
     */
    private final IColorStrategy decorated;

    /**
     * The maximum value of a color component
     */
    private static final int MAX_COMPONENT = 255;

    /**
     * Constructor
     * @param decorated
     */
    public InvertedColorStrategy(IColorStrategy decorated) {
        this.decorated = decorated;
    }

    /**
     * Returns the color of the pixel at the given iteration
     * @param iterations
     * @return
     */
    @Override
    public Color getColor(int iterations) {
        Color color = decorated.getColor(iterations);
        return new Color(MAX_COMPONENT - color.getRed(), MAX_COMPONENT - color.getGreen(),
                MAX_COMPONENT - color.getBlue());
    }
}
