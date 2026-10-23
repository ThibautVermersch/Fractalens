package fr.univartois.butinfo.fractalens.generator;

import java.awt.Color;

/**
 * Defines the interface for color strategies.
 */
public interface IColorStrategy {

    /**
     * Gives the color corresponding to a given number of iterations.
     * @param iterations The number of iterations performed.
     * @return The color corresponding to the given number of iterations.
     */
    Color getColor(int iterations);
}
