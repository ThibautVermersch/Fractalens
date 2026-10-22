package fr.univartois.butinfo.fractalens.generator;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.madgag.gif.fmsware.AnimatedGifEncoder;
import fr.univartois.butinfo.fractalens.image.IFractalImage;


public class AnimatedFractalGenerator implements IFractalGenerator {

    /**
     * The list of individual fractal generators composing the animation frames.
     */
    private final List<IFractalGenerator> generators;

    /**
     * The output file path where the animated GIF will be saved.
     */
    private final String outputPath;

    /**
     * Images générées par les sous-générateurs.
     */
    private final List<IFractalImage> frames;

    /**
     * The delay between each frame in milliseconds.
     */
    private final int delayMs;

    /**
     * Creates a new AnimatedFractalGenerator.
     *
     * @param generators The list of fractal generators.
     * @param outputPath The destination file path.
     * @param delayMs    The delay between frames in milliseconds.
     */
    public AnimatedFractalGenerator(List<IFractalGenerator> generators, String outputPath, int delayMs) {
        this.generators = new ArrayList<>(Objects.requireNonNull(generators, "Generators list cannot be null."));
        this.outputPath = Objects.requireNonNull(outputPath, "Output path cannot be null.");
        if (delayMs <= 0) {
            throw new IllegalArgumentException("Delay must be strictly positive.");
        }
        this.delayMs = delayMs;
        this.frames = new ArrayList<>();
    }

    public List<IFractalGenerator> getGenerators() {
        return Collections.unmodifiableList(this.generators);
    }

    /**
     * Generates all fractal images in the sequence.
     *
     * @return The first generated fractal image of the sequence.
     */
    @Override
    public IFractalImage generateImage() {
        this.frames.clear();
        IFractalImage firstImage = null;
        for (IFractalGenerator generator : generators) {
            IFractalImage img = generator.generateImage();
            this.frames.add(img);
            if (firstImage == null) {
                firstImage = img;
            }
        }
        return firstImage;
    }

    /**
     * Computes the color of a specific pixel by delegating to the first generator.
     *
     * @param row    The row of the pixel.
     * @param column The column of the pixel.
     * @return The computed color for the pixel.
     */
    @Override
    public Color computePixelColor(int row, int column) {
        if (!generators.isEmpty()) {
            return generators.getFirst().computePixelColor(row, column);
        }
        return Color.BLACK;
    }

    /**
     * Saves all generated frames as an animated GIF into the output file.
     */
    @Override
    public void saveImage() {
        if (this.generators.isEmpty()) {
            throw new IllegalStateException("Cannot save GIF without frames.");
        }

        if (this.frames.isEmpty()) {
            generateImage();
        }

        AnimatedGifEncoder encoder = new AnimatedGifEncoder();
        encoder.start(outputPath);
        encoder.setDelay(delayMs);
        encoder.setRepeat(0);
        encoder.setQuality(10);

        for (IFractalImage frame : this.frames) {
            if (frame != null) {
                BufferedImage buf = frame.asBufferedImage();
                if (buf != null) {
                    encoder.addFrame(buf);
                }
            }
        }

        encoder.finish();
    }
}
