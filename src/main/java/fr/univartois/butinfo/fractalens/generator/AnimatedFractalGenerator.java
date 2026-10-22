package fr.univartois.butinfo.fractalens.generator;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;

import com.madgag.gif.fmsware.AnimatedGifEncoder;
import fr.univartois.butinfo.fractalens.image.BufferedImageAdaptater;
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
        this.generators = generators;
        this.outputPath = outputPath;
        this.delayMs = delayMs;
    }

    /**
     * Generates all fractal images in the sequence.
     *
     * @return The first generated fractal image of the sequence.
     */
    @Override
    public IFractalImage generateImage() {
        IFractalImage firstImage = null;
        for (IFractalGenerator generator : generators) {
            IFractalImage img = generator.generateImage();
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
            return generators.get(0).computePixelColor(row, column);
        }
        return Color.BLACK;
    }

    /**
     * Saves all generated frames as an animated GIF into the output file.
     */
    @Override
    public void saveImage() {
        AnimatedGifEncoder encoder = new AnimatedGifEncoder();
        encoder.start(outputPath);
        encoder.setDelay(delayMs);
        encoder.setRepeat(0);
        encoder.setQuality(10);

        for (IFractalGenerator generator : generators) {
            IFractalImage fractalImage = generator.generateImage();

            if (fractalImage instanceof BufferedImageAdaptater) {
                BufferedImage bufferedImage = ((BufferedImageAdaptater) fractalImage).getBufferedImage();
                encoder.addFrame(bufferedImage);
            }
        }

        encoder.finish();
    }
}