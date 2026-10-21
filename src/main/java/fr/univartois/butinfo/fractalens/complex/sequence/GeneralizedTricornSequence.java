package fr.univartois.butinfo.fractalens.complex.sequence;

import fr.univartois.butinfo.fractalens.complex.IComplex;

public class GeneralizedTricornSequence extends AbstractComplexSequence {

    /**
     * The constant c
     */
    private final IComplex c;

    /**
     * Constructor of the sequence
     * @param initial
     * @param c
     * @param maxIterations
     */
    public GeneralizedTricornSequence(IComplex initial, IComplex c, int maxIterations) {
        super(initial, maxIterations);
        this.c = c;
    }

    /**
     * Computes the next term: the square of the conjugate of the previous one, plus the constant.
     * @param previous The previous value in the sequence.
     *
     * @return
     */
    public IComplex computeNext(IComplex previous) {
        IComplex conjugate = previous.conjugate();
        return conjugate.multiply(conjugate).add(c);
    }

    /**
     * Getter of the constant c.
     * @return
     */
    public IComplex getC() {
        return c;
    }
}
