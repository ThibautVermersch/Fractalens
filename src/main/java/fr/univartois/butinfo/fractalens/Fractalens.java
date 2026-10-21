package fr.univartois.butinfo.fractalens;

import java.awt.Color;

import fr.univartois.butinfo.fractalens.complex.Complex;
import fr.univartois.butinfo.fractalens.complex.ComplexPlane;
import fr.univartois.butinfo.fractalens.complex.IComplex;
import fr.univartois.butinfo.fractalens.complex.IComplexPlane;
import fr.univartois.butinfo.fractalens.complex.TranslatedComplexPlane;
import fr.univartois.butinfo.fractalens.complex.ZoomComplexPlane;
import fr.univartois.butinfo.fractalens.complex.sequence.BurningShipSequence;
import fr.univartois.butinfo.fractalens.complex.sequence.JuliaSequence;
import fr.univartois.butinfo.fractalens.generator.ColorMaskStrategy;
import fr.univartois.butinfo.fractalens.generator.FractalGenerator;
import fr.univartois.butinfo.fractalens.generator.GradientColorStrategy;
import fr.univartois.butinfo.fractalens.generator.GrayScaleStrategy;
import fr.univartois.butinfo.fractalens.generator.IColorStrategy;
import fr.univartois.butinfo.fractalens.generator.IFractalGenerator;
import fr.univartois.butinfo.fractalens.generator.InvertedColorStrategy;
import fr.univartois.butinfo.fractalens.image.BufferedImageAdaptater;
import fr.univartois.butinfo.fractalens.image.IFractalImage;

/**
 * Entry point of the Fractalens application for Sprint 3.
 *
 * @author Thibaut Vermersch
 * @version 0.1.0
 */
public final class Fractalens {

    /**
     * Width of the generated images in pixels.
     */
    private static final int WIDTH = 800;

    /**
     * Height of the generated images in pixels.
     */
    private static final int HEIGHT = 800;

    /**
     * Maximum number of iterations to compute for each pixel.
     */
    private static final int MAX_ITERATIONS = 250;

    /**
     * Zoom factor applied on the complex plane.
     */
    private static final double ZOOM = 0.00015;

    /**
     * Disables instantiation of this utility class.
     */
    private Fractalens() {}

    /**
     * Main entry point generating the two figures required for Sprint 3.
     *
     * @param args Command line arguments specifying output filenames.
     */
    public static void main(String[] args) {
        String fileFig1 = (args.length > 0) ? args[0] : "figure1.png";
        String fileFig2 = (args.length > 1) ? args[1] : "figure2.png";
        String fileFig3 = (args.length > 2) ? args[2] : "burningship.png";

        IColorStrategy palette1 = new GradientColorStrategy(MAX_ITERATIONS,Color.YELLOW, Color.RED);
        createFigure(new Complex(-0.4, 0.6), new Complex(0.1, 0.6), palette1, fileFig1);

        IColorStrategy gray = new GrayScaleStrategy(MAX_ITERATIONS);
        IColorStrategy cyan = new ColorMaskStrategy(gray, false, true, false);
        IColorStrategy invertedCyan = new InvertedColorStrategy(cyan);
        createFigure(new Complex(0.285, 0.01), new Complex(-0.47, -0.21), invertedCyan, fileFig2);

        IColorStrategy paletteShip = new GradientColorStrategy(MAX_ITERATIONS, Color.BLACK, Color.CYAN);
        createBurningShipFigure(new Complex(-1.75, -0.03), paletteShip, fileFig3);
    }

    /**
     * Configures the complex plane with zoom and translation.
     *
     * @param center The target center point in the complex plane.
     * @return The fully decorated complex plane.
     */
    private static IComplexPlane createConfiguredPlane(IComplex center) {
        IComplexPlane base = new ComplexPlane(HEIGHT, WIDTH);
        IComplexPlane zoomed = new ZoomComplexPlane(base, ZOOM);
        return new TranslatedComplexPlane(zoomed, center);
    }

    /**
     * Generates and saves a Julia fractal image according to specified parameters.
     *
     * @param c          The Julia sequence constant.
     * @param center     The center point of the view.
     * @param palette    The color strategy to apply.
     * @param outputFile The destination file path.
     */
    private static void createFigure(IComplex c, IComplex center, IColorStrategy palette, String outputFile) {
        IComplexPlane plane = createConfiguredPlane(center);
        IFractalImage image = new BufferedImageAdaptater(WIDTH, HEIGHT);

        IFractalGenerator generator = new FractalGenerator(
                (z0, max) -> new JuliaSequence(z0, c, max),
                image, plane, MAX_ITERATIONS, palette, outputFile);

        generator.generateImage();
        generator.saveImage();
    }
    private static void createBurningShipFigure(IComplex center, IColorStrategy palette, String outputFile) {
        IComplexPlane plane = createConfiguredPlane(center);
        IFractalImage image = new BufferedImageAdaptater(WIDTH, HEIGHT);

        IFractalGenerator generator = new FractalGenerator(
                (z0, max) -> new BurningShipSequence(z0, max),
                image, plane, MAX_ITERATIONS, palette, outputFile);

        generator.generateImage();
        generator.saveImage();
    }
}
