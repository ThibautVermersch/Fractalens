package fr.univartois.butinfo.fractalens;

import java.awt.Color;

import fr.univartois.butinfo.fractalens.complex.Complex;
import fr.univartois.butinfo.fractalens.complex.ComplexPlane;
import fr.univartois.butinfo.fractalens.complex.IComplex;
import fr.univartois.butinfo.fractalens.complex.IComplexPlane;
import fr.univartois.butinfo.fractalens.complex.TranslatedComplexPlane;
import fr.univartois.butinfo.fractalens.complex.ZoomComplexPlane;
import fr.univartois.butinfo.fractalens.complex.sequence.BurningShipSequence;
import fr.univartois.butinfo.fractalens.complex.sequence.GenericComplexSequence;
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
 * Luncher class for the fractalens application.
 */
public final class Fractalens {

    /**
     * Private constructor to prevent instantiation.
     */
    private Fractalens() {

    }

    /**
     * Luncher method.
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        String fileFig1 = (args.length > 0) ? args[0] : "figure1.png";
        String fileFig2 = (args.length > 1) ? args[1] : "figure2.png";
        String fileFig3 = (args.length > 2) ? args[2] : "burningship.png";
        String fileFig4 = (args.length > 3) ? args[3] : "mandelbrot.png";

        // Figur 1
        IColorStrategy palette1 = new GradientColorStrategy(250, Color.YELLOW, Color.RED);
        createJuliaFigure(800, 800, new Complex(-0.4, 0.6), new Complex(0.1, 0.6), 0.001, 250, palette1, fileFig1);

        // Figur 2
        IColorStrategy baseGradient = new GradientColorStrategy(250, Color.WHITE, new Color(30, 144, 255));
        IColorStrategy invertedColor = new InvertedColorStrategy(baseGradient);
        createJuliaFigure(800, 800, new Complex(0.285, 0.01), new Complex(-0.47, -0.21), 0.001, 250, invertedColor, fileFig2);


        IColorStrategy baseGradient2 = new GradientColorStrategy(250, Color.WHITE, new Color(0, 50, 200));
        IColorStrategy burningFire = new InvertedColorStrategy(baseGradient2);
        createBurningShipFigure(800, 800, new Complex(-1.75, -0.03), 0.00015, 250, burningFire, fileFig3);


        IColorStrategy baseMandel = new GradientColorStrategy(250, Color.WHITE, new Color(230, 20, 120));
        IColorStrategy emeraldMandelbrot = new InvertedColorStrategy(baseMandel);
        createMandelbrotFigure(800, 600, new Complex(-0.75, 0.0), 0.005, 250, emeraldMandelbrot, fileFig4);
    }

    /**
     * Create a complex plane with a zoom and a center.
     * @param width The width of the plane.
     * @param height The height of the plane.
     * @param center The center of the plane.
     * @param zoom The zoom factor of the plane.
     * @return
     */
    private static IComplexPlane createConfiguredPlane(int width, int height, IComplex center, double zoom) {
        IComplexPlane base = new ComplexPlane(height, width);
        IComplexPlane zoomed = new ZoomComplexPlane(base, zoom);
        return new TranslatedComplexPlane(zoomed, center);
    }

    /**
     * Generate a Julia fractal.
     * @param width The width of the image.
     * @param height The height of the image.
     * @param c The constant of the Julia set.
     * @param center The center of the plane.
     * @param zoom The zoom factor of the plane.
     * @param maxIterations The maximum number of iterations.
     * @param palette The color palette.
     * @param outputFile The output file.
     */
    private static void createJuliaFigure(int width, int height, IComplex c, IComplex center, double zoom, int maxIterations, IColorStrategy palette, String outputFile) {
        IComplexPlane plane = createConfiguredPlane(width, height, center, zoom);
        IFractalImage image = new BufferedImageAdaptater(width, height);

        IFractalGenerator generator = new FractalGenerator(
                (z0, max) -> new JuliaSequence(z0, c, max),
                image, plane, maxIterations, palette, outputFile);

        generator.generateImage();
        generator.saveImage();
    }

    /**
     * Create a burning ship fractal.
     * @param width The width of the image.
     * @param height The height of the image.
     * @param center The center of the plane.
     * @param zoom The zoom factor of the plane.
     * @param maxIterations The maximum number of iterations.
     * @param palette The color palette.
     * @param outputFile The output file.
     */
    private static void createBurningShipFigure(int width, int height, IComplex center, double zoom, int maxIterations, IColorStrategy palette, String outputFile) {
        IComplexPlane plane = createConfiguredPlane(width, height, center, zoom);
        IFractalImage image = new BufferedImageAdaptater(width, height);

        IFractalGenerator generator = new FractalGenerator(
                (z0, max) -> new BurningShipSequence(z0, max),
                image, plane, maxIterations, palette, outputFile);

        generator.generateImage();
        generator.saveImage();
    }

    /**
     * Create a Mandelbrot fractal.
     * @param width The width of the image.
     * @param height The height of the image.
     * @param center The center of the plane.
     * @param zoom The zoom factor of the plane.
     * @param maxIterations The maximum number of iterations.
     * @param palette The color palette.
     * @param outputFile The output file.
     */
    private static void createMandelbrotFigure(int width, int height, IComplex center, double zoom, int maxIterations, IColorStrategy palette, String outputFile) {
        IComplexPlane plane = createConfiguredPlane(width, height, center, zoom);
        IFractalImage image = new BufferedImageAdaptater(width, height);

        IFractalGenerator generator = new FractalGenerator(
                (z0, max) -> new GenericComplexSequence(z0, max, (prev, init) -> prev.multiply(prev).add(init)),
                image, plane, maxIterations, palette, outputFile);

        generator.generateImage();
        generator.saveImage();
    }
}
