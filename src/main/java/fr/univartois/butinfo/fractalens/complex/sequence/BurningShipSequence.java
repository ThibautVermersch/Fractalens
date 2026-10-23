package fr.univartois.butinfo.fractalens.complex.sequence;

import fr.univartois.butinfo.fractalens.complex.Complex;
import fr.univartois.butinfo.fractalens.complex.IComplex;

/**
 * The Burning ship sequence.
 */
public class BurningShipSequence extends AbstractComplexSequence {

    /**
     * The initial value of the sequence.
     */
    private final IComplex z0;

    /**
     * Constructor of the sequence
     * @param z0 The initial value of the sequence.
     * @param maxIterations The maximum number of iterations to compute.
     */
    public BurningShipSequence(IComplex z0, int maxIterations) {

        super(z0, maxIterations);
        this.z0 = z0;
    }

    /**
     * Computes the next term: the square of the absolute value of the previous one, plus the constant.
     * @param previous The previous value in the sequence.
     *
     * @return The next value in the sequence.
     */
    @Override
    public IComplex computeNext(IComplex previous) {

        double absRe = Math.abs(previous.getRealPart());
        double absIm = Math.abs(previous.getImaginaryPart());

        IComplex absZ = new Complex(absRe, absIm);

        return absZ.pow(2).add(z0);
    }
}
