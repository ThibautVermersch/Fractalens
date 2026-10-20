package fr.univartois.butinfo.fractalens.generator;

import fr.univartois.butinfo.fractalens.complex.IComplex;
import fr.univartois.butinfo.fractalens.complex.IComplexPlane;
import fr.univartois.butinfo.fractalens.complex.sequence.IComplexSequence;
import fr.univartois.butinfo.fractalens.image.IFractalImage;

import java.awt.*;
import java.util.function.BiFunction;

public class FractalGenerator implements IFractalGenerator{

    /**
     * Construct the sequence starting from z0 and the maximum number of iterations.
     */
    private  final BiFunction<IComplex, Integer, IComplexSequence> sequenceFactory;

    /**
     * The image to generate.
     */
    private final IFractalImage image;

    /**
     * The plane on which the fractal is drawn.
     */
    private final IComplexPlane plane;

    /**
     * The maximum number of iterations to compute.
     */
    private final int maxIterations;

    /**
     * The color strategy to use.
     */
    private final IColorStrategy colorStrategy;

    /**
     * The path of the output file.
     */
    private final String path;

    /**
     * Constructor
     * @param sequenceFactory
     * @param image
     * @param plane
     * @param maxIterations
     * @param colorStrategy
     * @param path
     */
    public FractalGenerator(BiFunction<IComplex, Integer, IComplexSequence> sequenceFactory, IFractalImage image,
            IComplexPlane plane, int maxIterations, IColorStrategy colorStrategy,
            String path) {
        this.sequenceFactory = sequenceFactory;
        this.image = image;
        this.plane = plane;
        this.maxIterations = maxIterations;
        this.colorStrategy = colorStrategy;
        this.path = path;
    }

    /**
     * Generates a full fractal image.
     *
     * @return The generated image.
     */
    @Override
    public IFractalImage generateImage(){
        for (int row = 0, row < image.getWidth(), row++) {
            for (int column = 0, column < image.getHeight(), column++) {
                image.setColor(row,column,computePixelColor(row,column));
            }
        }
        return image;
    }

    /**
     * Computes the color of a given pixel.
     * This method iterates of the sequence considered by this generator to evaluate the
     * number of iterations required to decide whether this sequence diverges.
     *
     * @param row The row of the pixel.
     * @param column The column of the pixel.
     *
     * @return The computed color for the pixel.
     */
    @Override
    public Color computePixelColor(int row, int column){
        IComplex z0 = plane.asComplex(row,column);

        IComplexSequence sequence = sequenceFactory.apply(z0, maxIterations);
        int itetations = 0;
        for (IComplex term : sequence) {
            itetations ++;
        }

        return  colorStrategy.getColor(iterations);
    }

    /**
     * Saves the generated image in the output file.
     */
    @Override
    public void saveImage(){
        image.saveAs(path);
    }
}
