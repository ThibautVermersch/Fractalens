package fr.univartois.butinfo.fractalens.generator;

import fr.univartois.butinfo.fractalens.complex.IComplex;
import fr.univartois.butinfo.fractalens.complex.IComplexPlane;
import fr.univartois.butinfo.fractalens.complex.sequence.IComplexSequence;
import fr.univartois.butinfo.fractalens.image.IFractalImage;

import java.awt.*;
import java.util.function.BiFunction;

public class FractalGenerator implements IFractalGenerator{

    private  final BiFunction<IComplex, Integer, IComplexSequence> sequenceFactory;
    private final IFractalImage image;
    private final IComplexPlane plane;

    /**
     * Constructor
     * @param sequenceFactory
     * @param image
     */
    public FractalGenerator(BiFunction<IComplex, Integer, IComplexSequence> sequenceFactory, IFractalImage image,
            IComplexPlane plane) {
        this.sequenceFactory = sequenceFactory;
        this.image = image;
        this.plane = plane;
    }

    /**
     * Generates a full fractal image.
     *
     * @return The generated image.
     */
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
    public Color computePixelColor(int row, int column){
        return null;
    }

    /**
     * Saves the generated image in the output file.
     */
    public void saveImage(){
        return;
    }
}
