
package fr.univartois.butinfo.fractalens;

import java.util.function.BiFunction;

import fr.univartois.butinfo.fractalens.complex.Complex;
import fr.univartois.butinfo.fractalens.complex.ComplexPlane;
import fr.univartois.butinfo.fractalens.complex.IComplex;
import fr.univartois.butinfo.fractalens.complex.IComplexPlane;
import fr.univartois.butinfo.fractalens.complex.ZoomComplexPlane;
import fr.univartois.butinfo.fractalens.generator.GrayScaleStrategy;
import fr.univartois.butinfo.fractalens.generator.IColorStrategy;
import fr.univartois.butinfo.fractalens.image.BufferedImageAdaptater;
import fr.univartois.butinfo.fractalens.complex.sequence.IComplexSequence;
import fr.univartois.butinfo.fractalens.complex.sequence.JuliaSequence;
import fr.univartois.butinfo.fractalens.generator.FractalGenerator;
import fr.univartois.butinfo.fractalens.generator.IFractalGenerator;
import fr.univartois.butinfo.fractalens.image.IFractalImage;

/**
 * The Fractalens class is the entry point of the application.
 * <p>
 * It generates an image of the Julia fractal for {@code c = -0.4 + 0.6i} in grey
 * scale, and saves it in the file whose name is given as first argument.
 *
 * @author Yeleen Cividino
 *
 * @version 0.1.0
 */
public final class Fractalens {

    /**
     * The height of the generated image, measured in pixels.
     */
    static final int HEIGHT = 600;

    /**
     * The width of the generated image, measured in pixels.
     */
    static final int WIDTH = 800;

    /**
     * The maximum number of iterations to perform for each pixel.
     */
    static final int MAX_ITERATIONS = 100;

    /**
     * The zoom factor to apply on the complex plane.
     */
    static final double ZOOM = 0.004;

    /**
     * The constant used in the definition of the Julia sequence.
     */
    static final IComplex JULIA_CONSTANT = new Complex(-0.4, 0.6);

    /**
     * The name of the file in which to save the image when no name is given.
     */
    static final String DEFAULT_OUTPUT_FILE = "julia.png";

    /**
     * Disables instantiation of this class.
     */
    private Fractalens() {
        // This class only provides static methods.
    }

    /**
     * Generates an image of the Julia fractal and saves it in a file.
     *
     * @param args The command line arguments. The first one, if any, is the name of
     *        the file in which to save the image.
     */
    public static void main(String[] args) {
        IFractalGenerator generator = createGenerator(getOutputFile(args));
        generator.generateImage();
        generator.saveImage();
    }

    /**
     * Gives the name of the file in which to save the image.
     *
     * @param args The command line arguments.
     *
     * @return The first argument if there is one, or {@link #DEFAULT_OUTPUT_FILE}
     *         otherwise.
     */
    static String getOutputFile(String[] args) {
        return (args.length > 0) ? args[0] : DEFAULT_OUTPUT_FILE;
    }

    /**
     * Creates the complex plane on which the fractal is drawn, zoomed with
     * {@link #ZOOM}.
     *
     * @return The complex plane to use.
     */
    static IComplexPlane createPlane() {
        return new ZoomComplexPlane(new ComplexPlane(HEIGHT, WIDTH), ZOOM);
    }

    /**
     * Creates the function that builds the Julia sequence starting from the complex
     * number corresponding to a pixel.
     *
     * @return The function creating a Julia sequence from its first term and its
     *         maximum number of terms.
     */
    static BiFunction<IComplex, Integer, IComplexSequence> createJuliaSequenceFactory() {
        return (z0, nbTerms) -> new JuliaSequence(z0, JULIA_CONSTANT, nbTerms);
    }

    /**
     * Creates the fractal generator, configured with the characteristics of the image
     * to generate.
     *
     * @param outputFile The name of the file in which to save the image.
     *
     * @return The configured fractal generator.
     */
    static IFractalGenerator createGenerator(String outputFile) {
        IFractalImage image = new BufferedImageAdaptater(WIDTH, HEIGHT);
        IColorStrategy palette = new GrayScaleStrategy(MAX_ITERATIONS);
        return new FractalGenerator(createJuliaSequenceFactory(), image, createPlane(),
                MAX_ITERATIONS, palette, outputFile);
    }

}