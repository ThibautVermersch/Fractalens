package fr.univartois.butinfo.fractalens.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.IntFunction;

import fr.univartois.butinfo.fractalens.complex.Complex;
import fr.univartois.butinfo.fractalens.complex.ComplexPlane;
import fr.univartois.butinfo.fractalens.complex.IComplex;
import fr.univartois.butinfo.fractalens.complex.IComplexPlane;
import fr.univartois.butinfo.fractalens.complex.TranslatedComplexPlane;
import fr.univartois.butinfo.fractalens.complex.ZoomComplexPlane;
import fr.univartois.butinfo.fractalens.complex.sequence.ComplexSequenceType;
import fr.univartois.butinfo.fractalens.complex.sequence.IComplexSequence;
import fr.univartois.butinfo.fractalens.image.BufferedImageAdaptater;
import fr.univartois.butinfo.fractalens.image.IFractalImage;

/**
 * Builder used to configure and assemble an {@link IFractalGenerator}.
 */
public class FractalGeneratorBuilder {

    private int width = 800;
    private int height = 800;
    private int maxIterations = 250;
    private Double zoom = null;
    private IComplex center = new Complex(0.0, 0.0);
    private String outputFile = "fractal.png";
    private BiFunction<IComplex, Integer, IComplexSequence> sequenceFactory;
    private IntFunction<IColorStrategy> colorStrategyFactory;

    /**
     * Constructs a new builder instance.
     */
    public FractalGeneratorBuilder() {
        // Default constructor
    }

    /**
     * Sets the dimensions of the generated image.
     *
     * @param width  Width in pixels.
     * @param height Height in pixels.
     * @return The builder instance.
     */
    public FractalGeneratorBuilder withDimensions(int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Dimensions must be strictly positive.");
        }
        this.width = width;
        this.height = height;
        return this;
    }

    /**
     * Sets the maximum number of iterations.
     *
     * @param maxIterations Number of iterations.
     * @return The builder instance.
     */
    public FractalGeneratorBuilder withMaxIterations(int maxIterations) {
        if (maxIterations <= 0) {
            throw new IllegalArgumentException("Max iterations must be strictly positive.");
        }
        this.maxIterations = maxIterations;
        return this;
    }

    /**
     * Sets the zoom scale factor defined by the caller.
     *
     * @param zoom The zoom factor (must be &gt; 0).
     * @return The builder instance.
     */
    public FractalGeneratorBuilder withZoom(double zoom) {
        if (zoom <= 0.0) {
            throw new IllegalArgumentException("Zoom must be strictly positive.");
        }
        this.zoom = zoom;
        return this;
    }

    /**
     * Sets the center point on the complex plane.
     *
     * @param center Target center point.
     * @return The builder instance.
     */
    public FractalGeneratorBuilder withCenter(IComplex center) {
        this.center = Objects.requireNonNull(center, "Center cannot be null.");
        return this;
    }

    /**
     * Sets the destination file path.
     *
     * @param outputFile Output file name.
     * @return The builder instance.
     */
    public FractalGeneratorBuilder withOutputFile(String outputFile) {
        this.outputFile = Objects.requireNonNull(outputFile, "Output file cannot be null.");
        return this;
    }

    /**
     * Sets the sequence factory using the enum type.
     *
     * @param type The sequence type.
     * @return The builder instance.
     */
    public FractalGeneratorBuilder withSequence(ComplexSequenceType type) {
        Objects.requireNonNull(type, "Sequence type cannot be null.");
        this.sequenceFactory = type.getFactory();
        return this;
    }

    /**
     * Sets the sequence factory using a raw functional factory.
     *
     * @param factory Sequence functional factory.
     * @return The builder instance.
     */
    public FractalGeneratorBuilder withSequence(BiFunction<IComplex, Integer, IComplexSequence> factory) {
        this.sequenceFactory = Objects.requireNonNull(factory, "Sequence factory cannot be null.");
        return this;
    }

    /**
     * Sets the color strategy using the enum type.
     *
     * @param type The color strategy type.
     * @return The builder instance.
     */
    public FractalGeneratorBuilder withColorStrategy(ColorStrategyType type) {
        Objects.requireNonNull(type, "Color strategy type cannot be null.");
        this.colorStrategyFactory = type::createStrategy;
        return this;
    }

    /**
     * Sets a raw color strategy.
     *
     * @param strategy The color strategy.
     * @return The builder instance.
     */
    public FractalGeneratorBuilder withColorStrategy(IColorStrategy strategy) {
        Objects.requireNonNull(strategy, "Color strategy cannot be null.");
        this.colorStrategyFactory = max -> strategy;
        return this;
    }

    /**
     * Sets a color strategy factory depending on max iterations.
     *
     * @param factory Functional strategy factory.
     * @return The builder instance.
     */
    public FractalGeneratorBuilder withColorStrategyFactory(IntFunction<IColorStrategy> factory) {
        this.colorStrategyFactory = Objects.requireNonNull(factory, "Color strategy factory cannot be null.");
        return this;
    }

    /**
     * Builds and wires the complete fractal generator.
     *
     * @return The configured {@link IFractalGenerator}.
     * @throws IllegalStateException If the zoom, sequence factory or color strategy was not defined.
     */
    public IFractalGenerator build() {
        if (this.zoom == null) {
            throw new IllegalStateException("Zoom factor must be explicitly defined before building.");
        }
        if (this.sequenceFactory == null) {
            throw new IllegalStateException("Sequence factory must be defined before building.");
        }
        if (this.colorStrategyFactory == null) {
            throw new IllegalStateException("Color strategy must be defined before building.");
        }

        IComplexPlane base = new ComplexPlane(this.height, this.width);
        IComplexPlane zoomed = new ZoomComplexPlane(base, this.zoom);
        IComplexPlane plane = new TranslatedComplexPlane(zoomed, this.center);

        IFractalImage image = new BufferedImageAdaptater(this.width, this.height);
        IColorStrategy colorStrategy = this.colorStrategyFactory.apply(this.maxIterations);

        return new FractalGenerator(
                this.sequenceFactory,
                image,
                plane,
                this.maxIterations,
                colorStrategy,
                this.outputFile
        );
    }

    /**
     * Builds an animated fractal generator.
     * @param frame the number of frames
     * @param zoomFactor the zoom factor
     * @param delay the delay between frames
     * @return the animated fractal generator
     */
    public IFractalGenerator buildZoomAnimation(int frame, double zoomFactor, int delay){
        if (frame <= 0) {
            throw new IllegalArgumentException("the number of frames must be positive.");
        }
        if (zoomFactor <= 0.0 || zoomFactor >= 1.0) {
            throw new IllegalArgumentException("the zoom factor must be between 0.0 and 1.0.");
        }
        if (delay <= 0) {
            throw new IllegalArgumentException("the delay must be positive.");
        }
        if (this.zoom == null) {
            throw new IllegalStateException("the initial zoom must be defined before building.");
        }
        List<IFractalGenerator> frameGenerators = new ArrayList<>();
        double currentZoom = this.zoom;

        for (int i = 0; i < frame; i++) {
            this.zoom = currentZoom;
            frameGenerators.add(this.build());
            currentZoom *= zoomFactor;
        }

        return new AnimatedFractalGenerator(frameGenerators, this.outputFile, delay);
    }
}
