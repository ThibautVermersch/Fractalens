package fr.univartois.butinfo.fractalens;

import fr.univartois.butinfo.fractalens.complex.Complex;
import fr.univartois.butinfo.fractalens.complex.sequence.ComplexSequenceType;
import fr.univartois.butinfo.fractalens.generator.ColorStrategyType;
import fr.univartois.butinfo.fractalens.generator.FractalGeneratorBuilder;
import fr.univartois.butinfo.fractalens.generator.IFractalGenerator;

/**
 * Launcher class for the fractalens application.
 */
public final class Fractalens {

    /**
     * Private constructor to prevent instantiation.
     */
    private Fractalens() {
        // Utility class
    }

    /**
     * Launcher method.
     *
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        String fileFig1 = (args.length > 0) ? args[0] : "figure1.png";
        String fileFig2 = (args.length > 1) ? args[1] : "figure2.png";
        String fileFig3 = (args.length > 2) ? args[2] : "burningship.png";
        String fileFig4 = (args.length > 3) ? args[3] : "mandelbrot.png";
        String fileFig5 = (args.length > 4) ? args[4] : "tricorn.png";
        String fileFigLambda = (args.length > 5) ? args[5] : "lambda.png";
        String fileZoomGif = (args.length > 6) ? args[6] : "mandelbrot-zoom.gif";
        String fileRotationGif = (args.length > 7) ? args[7] : "julia-rotation.gif";

        // Figure 1: Julia (-0.4 + 0.6i) with a warm color gradient
        generate(new FractalGeneratorBuilder()
                .withSequence(ComplexSequenceType.JULIA)
                .withDimensions(800, 800)
                .withCenter(new Complex(0.1, 0.6))
                .withZoom(0.001)
                .withMaxIterations(250)
                .withColorStrategy(ColorStrategyType.SUNSET)
                .withOutputFile(fileFig1)
                .build());

        // Figure 2: Julia spiral (0.285 + 0.01i) with an icy color palette
        generate(new FractalGeneratorBuilder()
                .withSequence(ComplexSequenceType.JULIA_SPIRAL)
                .withDimensions(800, 800)
                .withCenter(new Complex(-0.47, -0.21))
                .withZoom(0.001)
                .withMaxIterations(250)
                .withColorStrategy(ColorStrategyType.ICE)
                .withOutputFile(fileFig2)
                .build());

        // Figure 3: Burning Ship with a fire color palette
        generate(new FractalGeneratorBuilder()
                .withSequence(ComplexSequenceType.BURNING_SHIP)
                .withDimensions(800, 800)
                .withCenter(new Complex(-1.755, -0.03))
                .withZoom(0.0001)
                .withMaxIterations(64)
                .withColorStrategy(ColorStrategyType.FIRE)
                .withOutputFile(fileFig3)
                .build());

        // Figure 4: Mandelbrot with a blue/yellow gradient
        generate(new FractalGeneratorBuilder()
                .withSequence(ComplexSequenceType.MANDELBROT)
                .withDimensions(800, 600)
                .withCenter(new Complex(-0.75, 0.0))
                .withZoom(0.003)
                .withMaxIterations(250)
                .withColorStrategy(ColorStrategyType.GRADIENT_BLUE_YELLOW)
                .withOutputFile(fileFig4)
                .build());

        // Figure 5: Tricorn with inverted cyber-palette
        generate(new FractalGeneratorBuilder()
                .withSequence(ComplexSequenceType.TRICORN)
                .withDimensions(800, 800)
                .withCenter(new Complex(-0.1, 0.0))
                .withZoom(0.004)
                .withMaxIterations(250)
                .withColorStrategy(ColorStrategyType.INVERTED_CYBER)
                .withOutputFile(fileFig5)
                .build());

        // Figure 6: Cubic lambda sequence with neon palette
        generate(new FractalGeneratorBuilder()
                .withSequence(ComplexSequenceType.CUBIC_LAMBDA)
                .withDimensions(800, 800)
                .withCenter(new Complex(0.0, 0.0))
                .withZoom(0.003)
                .withMaxIterations(100)
                .withColorStrategy(ColorStrategyType.NEON)
                .withOutputFile(fileFigLambda)
                .build());

        // Animation 1: zoom into the Mandelbrot set at the point -0.743643887037151 + 0.131825904205330i
        // (60 frames, zoom factor multiplied by 0.9 per frame, 80 ms between frames)
        generate(new FractalGeneratorBuilder()
                .withSequence(ComplexSequenceType.MANDELBROT)
                .withDimensions(400, 400)
                .withCenter(new Complex(-0.743643887037151, 0.131825904205330))
                .withZoom(3.0 / 400)
                .withMaxIterations(500)
                .withColorStrategy(ColorStrategyType.FIRE)
                .withOutputFile(fileZoomGif)
                .buildZoomAnimation(60, 0.9, 80));

        // Animation 2: Julia set centered at 0, with rotation of the constant c = 0.7885 + 0i
        // (60 frames for a full rotation, 80 ms between frames)
        generate(new FractalGeneratorBuilder()
                .withDimensions(400, 400)
                .withCenter(new Complex(0.0, 0.0))
                .withZoom(3.2 / 400)
                .withMaxIterations(250)
                .withColorStrategy(ColorStrategyType.ICE)
                .withOutputFile(fileRotationGif)
                .buildJuliaRotationAnimation(60, new Complex(0.7885, 0.0), 80));
    }

    /**
     * Generates the image of the given generator and saves it in its output file.
     * This works both for a single image and for an animation.
     *
     * @param generator The generator built with the {@link FractalGeneratorBuilder}.
     */
    private static void generate(IFractalGenerator generator) {
        generator.generateImage();
        generator.saveImage();
    }
}
