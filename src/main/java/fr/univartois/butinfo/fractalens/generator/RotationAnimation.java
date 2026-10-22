package fr.univartois.butinfo.fractalens.generator;

import fr.univartois.butinfo.fractalens.complex.IComplex;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.function.Function;

/**
 * @author Pispisa Matheo
 */
public class RotationAnimation {

    /**
     * Private constructor to hide the implicit public one
     */
    private RotationAnimation() {

    }

    /**
     * Rotates a complex number around the origin, i.e., multiplies it by cos(θ) + i sin(θ).
     * @param c The complex number to rotate.
     * @param theta The angle of the rotation, in radians.
     * @return The rotated complex number.
     */
    public static IComplex rotate(IComplex c, double theta) {
        return null;
    }

    /**
     *  Generates all the frames of the animation.
     * @param c The complex number to rotate.
     * @param frames The number of frames to generate.
     * @param delay The delay between frames, in milliseconds.
     * @return A list of images representing the animation frames.
     */
    public static List<BufferedImage> generateFrames(IComplex c, int frames, int delay) {
        return null;
    }

    /**
     * Saves the frames as a GIF file.
     * @param frames The list of frames to save.
     * @param path The path where to save the GIF file.
     */
    public static void safeAsGif(List<BufferedImage> frames, String path) {

    }
}
