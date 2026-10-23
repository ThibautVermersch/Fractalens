package fr.univartois.butinfo.fractalens.complex.sequence;

import fr.univartois.butinfo.fractalens.complex.IComplex;

/**
 * The generalized tricorn sequence.
 */
public class GeneralizedTricornSequence extends AbstractComplexSequence {

    /**
     * The constant c
     */
    private final IComplex c;

    /**
     * The minimum number of degrees of freedom for the sequence.
     */
    private static final int MIN_DEGREES = 2;

    /**
     * The degree of the sequence.
     */
    private final int degree;

    /**
     * Constructor of the sequence
     * @param initial The initial value of the sequence.
     * @param c The constant c
     * @param maxIterations The maximum number of iterations to compute.
     * @param degree The degree of the sequence.
     */
    public GeneralizedTricornSequence(IComplex initial, IComplex c, int maxIterations,
            int degree) {
        super(initial, maxIterations);
        if (degree < MIN_DEGREES) {
            throw new IllegalArgumentException("The degree must be at least " + MIN_DEGREES + ": " + degree);
        }
        this.c = c;
        this.degree = degree;
    }

    /**
     * Computes the next term: the conjugate of the previous
     * one raised to the degree * of the sequence, plus the constant
     * @param previous The previous value in the sequence.
     *
     * @return The next value in the sequence.
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
     * @return The constant c.
     */
    public IComplex getC() {
        return c;
    }

    /**
     * Getter of the constant c.
     * @return The degree of the sequence.
     */
    public int getDegree() {
        return degree;
    }
}
