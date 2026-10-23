package fr.univartois.butinfo.fractalens.complex.sequence;

import java.util.Objects;
import fr.univartois.butinfo.fractalens.complex.IComplex;

/**
 * Generalized Julia sequence.
 */
public class MultijuliaSequence extends AbstractComplexSequence {

    /**
     * The constant c
     */
    private final IComplex constant;

    /**
     * The power of the constant
     */
    private final int power;

    /**
     * Constructor of the sequence
     * @param initial The initial value of the sequence.
     * @param constant The constant c
     * @param power The power of the constant
     * @param maxIterations The maximum number of iterations to compute.
     */
    public MultijuliaSequence(IComplex initial, IComplex constant, int power, int maxIterations) {
        super(initial, maxIterations);
        if (power < 1) {
            throw new IllegalArgumentException("Power must be at least 1: " + power);
        }
        this.constant = Objects.requireNonNull(constant, "Constant cannot be null");
        this.power = power;
    }

    /**
     * Computes the next term of the sequence, using the formula
     * @param previous The previous value in the sequence.
     *
     * @return The next value in the sequence.
     */
    @Override
    public IComplex computeNext(IComplex previous) {
        return previous.pow(this.power).add(this.constant);
    }
}
