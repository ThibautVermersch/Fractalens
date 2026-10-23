package fr.univartois.butinfo.fractalens.complex.sequence;

import fr.univartois.butinfo.fractalens.complex.IComplex;

public class JuliaSequence extends AbstractComplexSequence{

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
    public JuliaSequence(IComplex initial, IComplex c, int maxIterations) {
        super(initial, maxIterations);
        this.c = c;
    }

    /**
     * Computes the next term: the square of the previous one, plus the constant.
     * @param previous The previous value in the sequence.
     *
     * @return The next value in the sequence.
     */
    @Override
    public IComplex computeNext(IComplex previous) {
        //Formule de Julia (z1= z0^2 + c)
        return previous.multiply(previous).add(c);
    }

    /**
     * Getter of the constant c.
     * @return The constant c.
     */
    public IComplex getC() {
        return c;
    }

}
