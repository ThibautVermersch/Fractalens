package fr.univartois.butinfo.fractalens;

import java.util.concurrent.Callable;

import fr.univartois.butinfo.fractalens.complex.Complex;
import fr.univartois.butinfo.fractalens.complex.IComplex;
import fr.univartois.butinfo.fractalens.complex.sequence.ComplexSequenceType;
import fr.univartois.butinfo.fractalens.generator.ColorStrategyType;
import fr.univartois.butinfo.fractalens.generator.FractalGeneratorBuilder;
import fr.univartois.butinfo.fractalens.generator.IFractalGenerator;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * Main Picocli command for generating fractals via FractaLens.
 */
@Command(
        name = "fractalens",
        mixinStandardHelpOptions = true,
        version = "1.0.0",
        description = "Generate still images and GIF animations of fractals."
)
public class FractalensCommandCli implements Callable<Integer> {

    /**
     * Supported render types for the generation.
     *
     * @author Thibaut Vermersch
     * @version 0.1.0
     */
    public enum GenerationType {
        /**
         * Generates a single static fractal image.
         */
        STATIC,

        /**
         * Generates an animated zoom GIF.
         */
        ZOOM,

        /**
         * Generates an animated Julia constant rotation GIF.
         */
        ROTATION
    }

    /**
     * The type of fractal sequence to compute.
     */
    @Option(names = {"-f", "--fractal-name"}, defaultValue = "MANDELBROT",
            description = "Name of the fractal to generate: ${COMPLETION-CANDIDATES}")
    private ComplexSequenceType fractalName;

    /**
     * The maximum number of iterations per point.
     */
    @Option(names = {"-n", "--nb-iterations"}, defaultValue = "100",
            description = "Maximum number of iterations.")
    private int nbIterations;

    /**
     * The height of the output image in pixels.
     */
    @Option(names = {"-h", "--height"}, defaultValue = "600",
            description = "Image height in pixels.")
    private int height;

    /**
     * The width of the output image in pixels.
     */
    @Option(names = {"-w", "--width"}, defaultValue = "600",
            description = "Image width in pixels.")
    private int width;

    /**
     * The scaling factor representing the zoom level.
     */
    @Option(names = {"-s", "--scale"}, defaultValue = "0.005",
            description = "Scale / zoom to apply.")
    private double scale;

    /**
     * The real component of the central focus point.
     */
    @Option(names = {"-x", "--focus-x"}, defaultValue = "0.0",
            description = "Real part of the central point.")
    private double focusX;

    /**
     * The imaginary component of the central focus point.
     */
    @Option(names = {"-y", "--focus-y"}, defaultValue = "0.0",
            description = "Imaginary part of the central point.")
    private double focusY;

    /**
     * The color palette strategy to apply.
     */
    @Option(names = {"-p", "--palette-name"}, defaultValue = "GRAYSCALE",
            description = "Color palette name: ${COMPLETION-CANDIDATES}")
    private ColorStrategyType paletteName;

    /**
     * The type of rendering to execute.
     */
    @Option(names = {"-t", "--type"}, defaultValue = "STATIC",
            description = "Type of image to generate: ${COMPLETION-CANDIDATES}")
    private GenerationType type;

    /**
     * The number of frames to generate for animation sequences.
     */
    @Option(names = {"-i", "--image-count"}, defaultValue = "25",
            description = "Number of frames for animations.")
    private int imageCount;

    /**
     * The destination file path for the output image or animation.
     */
    @Option(names = {"-o", "--output"}, defaultValue = "fractale.png",
            description = "Output file (.png or .gif).")
    private String outputFile;

    /**
     * Constructs a new command line interface instance with default settings.
     */
    public FractalensCommandCli() {
        super();
    }

    /**
     * Executes the fractal generation process based on parsed options.
     *
     * @return 0 upon successful execution
     */
    @Override
    public Integer call() {
        IComplex center = new Complex(focusX, focusY);

        FractalGeneratorBuilder builder = new FractalGeneratorBuilder()
                .withDimensions(width, height)
                .withCenter(center)
                .withZoom(scale)
                .withMaxIterations(nbIterations)
                .withSequence(fractalName)
                .withColorStrategy(paletteName)
                .withOutputFile(outputFile);

        IFractalGenerator generator = switch (type) {
            case ZOOM -> builder.buildZoomAnimation(imageCount, 0.85, 80);
            case ROTATION -> {
                IComplex initialC = new Complex(0.7885, 0.0);
                yield builder.buildJuliaRotationAnimation(imageCount, initialC, 60);
            }
            case STATIC -> builder.build();
        };

        System.out.println("Generation of " + outputFile + " in progress");
        generator.generateImage();
        generator.saveImage();
        System.out.println("Generation successful.");

        return 0;
    }
}
