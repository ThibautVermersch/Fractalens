package fr.univartois.butinfo.fractalens.complex.sequence;

import fr.univartois.butinfo.fractalens.complex.IComplex;

/**
 * The Tricorn sequence.
 */
public class TricornSequence extends AbstractComplexSequence{

    /**
     * The constant c
     */
    private final IComplex c;

    /**
     * Constructor of the sequence
     * @param initial The initial value of the sequence.
     * @param c The constant c
     * @param maxIterations The maximum number of iterations to compute.
     */
    public TricornSequence(IComplex initial, IComplex c, int maxIterations) {
        super(initial, maxIterations);
        this.c = c;
    }

    /**
     * Computes the next term: the square of the conjugate of the previous one, plus the constant.
     * @param previous The previous value in the sequence.
     *
     * @return The next value in the sequence.
     */
    @Override
    public IComplex computeNext(IComplex previous) {
        IComplex conjugate = previous.conjugate();
        return conjugate.multiply(conjugate).add(c);
    }

    /**
     * Getter of the constant c.
     * @return The constant c.
     */
    public IComplex getC() {
        return c;
    }
}
