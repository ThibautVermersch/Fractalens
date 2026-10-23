package fr.univartois.butinfo.fractalens.complex.sequence;

import fr.univartois.butinfo.fractalens.complex.IComplex;
import java.util.Objects;
import java.util.function.BinaryOperator;

public class GenericComplexSequence extends AbstractComplexSequence{

    /**
     * The operator to use to compute the next value in the sequence.
     */
    private final BinaryOperator<IComplex> operator;

    /**
     * Constructor of the sequence.
     * @param initial The initial value of the sequence.
     * @param maxIteration The maximum number of iterations to compute.
     * @param operator The operator to use to compute the next value in the sequence.
     */
    public GenericComplexSequence(IComplex initial, int maxIteration, BinaryOperator<IComplex> operator) {
        super(initial, maxIteration);
        this.operator = Objects.requireNonNull(operator, "This operation can't be null");
    }

    /**
     * Computes the next value in the sequence using the given operator.
     * @param previous The previous value in the sequence.
     *
     * @return The next value in the sequence.
     */
    @Override
    public IComplex computeNext(IComplex previous) {
        return this.operator.apply(previous, getInitial());
    }

    /**
     * Creates a sequence of the form z_n = z_{n-1}^3 + z_{n-1} - z_0.
     * @param z0 The initial value of the sequence.
     * @param maxIterations The maximum number of iterations to compute.
     * @return The sequence of the form z_n = z_{n-1}^3 + z_{n-1} - z_0.
     */
    public static IComplexSequence createCubicLambdaSequence(IComplex z0, int maxIterations) {
        return new GenericComplexSequence(
                z0,
                maxIterations,
                (prev, init) -> prev.pow(3).add(prev.multiply(-1.0)).add(init)
        );
    }

}
