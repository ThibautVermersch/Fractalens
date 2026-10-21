package fr.univartois.butinfo.fractalens;

import java.awt.Color;

import fr.univartois.butinfo.fractalens.complex.Complex;
import fr.univartois.butinfo.fractalens.complex.sequence.ComplexSequenceType;
import fr.univartois.butinfo.fractalens.complex.sequence.GenericComplexSequence;
import fr.univartois.butinfo.fractalens.generator.FractalGeneratorBuilder;
import fr.univartois.butinfo.fractalens.generator.GradientColorStrategy;
import fr.univartois.butinfo.fractalens.generator.IFractalGenerator;
import fr.univartois.butinfo.fractalens.generator.InvertedColorStrategy;

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
        String fileFig5 = (args.length > 4) ? args[4] : "tricorn.png";
        String fileFigLambda = (args.length > 5) ? args[5] : "lambda.png";

        // Figure 1 : Julia, c = -0.4 + 0.6i
        generate(new FractalGeneratorBuilder()
                .withSequence(ComplexSequenceType.JULIA.getFactory())
                .withDimensions(800, 800)
                .withCenter(new Complex(0.1, 0.6))
                .withZoom(0.001)
                .withMaxIterations(250)
                .withColorStrategyFactory(max -> new GradientColorStrategy(max, Color.YELLOW, Color.RED))
                .withOutputFile(fileFig1)
                .build());

        // Figure 2 : Julia, c = 0.285 + 0.01i
        generate(new FractalGeneratorBuilder()
                .withSequence(ComplexSequenceType.JULIA_SPIRAL.getFactory())
                .withDimensions(800, 800)
                .withCenter(new Complex(-0.47, -0.21))
                .withZoom(0.001)
                .withMaxIterations(250)
                .withColorStrategyFactory(max -> new InvertedColorStrategy(
                        new GradientColorStrategy(max, Color.WHITE, new Color(30, 144, 255))))
                .withOutputFile(fileFig2)
                .build());

        // Figure 3 : Burning Ship
        generate(new FractalGeneratorBuilder()
                .withSequence(ComplexSequenceType.BURNING_SHIP.getFactory())
                .withDimensions(800, 800)
                .withCenter(new Complex(-1.755, -0.03))
                .withZoom(0.0001)
                .withMaxIterations(64)
                .withColorStrategyFactory(max -> new InvertedColorStrategy(
                        new GradientColorStrategy(max, Color.WHITE, new Color(0, 50, 200))))
                .withOutputFile(fileFig3)
                .build());

        // Figure 4 : Mandelbrot
        generate(new FractalGeneratorBuilder()
                .withSequence(ComplexSequenceType.MANDELBROT.getFactory())
                .withDimensions(800, 600)
                .withCenter(new Complex(-0.75, 0.0))
                .withZoom(0.003)
                .withMaxIterations(250)
                .withColorStrategyFactory(max -> new InvertedColorStrategy(
                        new GradientColorStrategy(max, Color.WHITE, new Color(230, 20, 120))))
                .withOutputFile(fileFig4)
                .build());

        // Figure 5 : Tricorn
        generate(new FractalGeneratorBuilder()
                .withSequence(ComplexSequenceType.TRICORN.getFactory())
                .withDimensions(800, 800)
                .withCenter(new Complex(-0.1, 0.0))
                .withZoom(0.004)
                .withMaxIterations(250)
                .withColorStrategyFactory(max -> new GradientColorStrategy(max, Color.BLACK, new Color(0, 230, 140)))
                .withOutputFile(fileFig5)
                .build());

        // Figure 6 : suite definie directement par une expression lambda, z^3 - z + z0
        generate(new FractalGeneratorBuilder()
                .withSequence((z0, max) -> new GenericComplexSequence(z0, max,
                        (prev, init) -> prev.pow(3).add(prev.multiply(-1.0)).add(init)))
                .withDimensions(800, 800)
                .withCenter(new Complex(0.0, 0.0))
                .withZoom(0.003)
                .withMaxIterations(100)
                .withColorStrategyFactory(max -> new GradientColorStrategy(max, new Color(15, 15, 30), new Color(255, 140, 20)))
                .withOutputFile(fileFigLambda)
                .build());
    }

    /**
     * Generates the image of the given generator and saves it in its output file.
     *
     * @param generator The generator built with the {@link FractalGeneratorBuilder}.
     */
    private static void generate(IFractalGenerator generator) {
        generator.generateImage();
        generator.saveImage();
    }
}