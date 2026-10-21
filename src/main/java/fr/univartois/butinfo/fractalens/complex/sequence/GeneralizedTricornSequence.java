package fr.univartois.butinfo.fractalens.complex.sequence;

import fr.univartois.butinfo.fractalens.complex.IComplex;

public class GeneralizedTricornSequence extends AbstractComplexSequence {

    /**
     * The constant c
     */
    private final IComplex c;

    /**
     * The minimum number of degrees of freedom for the sequence.
     */
    private static final int MIN_DEGREES = 2;

    private final int degree;

    /**
     * Constructor of the sequence
     * @param initial
     * @param c
     * @param maxIterations
     */
    public GeneralizedTricornSequence(IComplex initial, IComplex c, int maxIterations,
            int degree) {
        if (degree < MIN_DEGREES) {
            throw new IllegalArgumentException("The degree must be at least " + MIN_DEGREES + ": " + degree);
        }
        super(initial, maxIterations);
        this.c = c;
        this.degree = degree;
    }

    /**
     * Computes the next term: the conjugate of the previous
     * one raised to the degree * of the sequence, plus the constant
     * @param previous The previous value in the sequence.
     *
     * @return
     */
    public IComplex computeNext(IComplex previous) {
        IComplex conjugate = previous.conjugate();
        IComplex power = conjugate;
        for (int i = 1; i < degree; i++) {
            power = power.multiply(conjugate);
        }
        return power.add(c);
    }

    /**
     * Getter of the constant c.
     * @return
     */
    public IComplex getC() {
        return c;
    }

    /**
     * Getter of the constant c.
     * @return
     */
    public int getDegree() {
        return degree;
    }
}
