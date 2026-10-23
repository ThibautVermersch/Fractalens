package fr.univartois.butinfo.fractalens.complex.sequence;

import fr.univartois.butinfo.fractalens.complex.IComplex;

public class MandelbrotSequence extends AbstractComplexSequence {

    /**
     * Creates a new MandelbrotSequence.
     *
     * @param initial The first term {@code z0} of the sequence, i.e., the complex
     *        number for which the sequence is evaluated.
     * @param maxIterations The maximum number of terms to compute.
     */
    public MandelbrotSequence(IComplex initial, int maxIterations) {
        super(initial, maxIterations);
    }

    /**
     * Computes the next term of the sequence, using the formula
     * {@code z(n+1) = z(n)² + z0}.
     *
     * @param previous The previous term {@code z(n)} of the sequence.
     *
     * @return The next term {@code z(n+1)} of the sequence.
     */
    @Override
    public IComplex computeNext(IComplex previous) {
        return previous.multiply(previous).add(initial);
    }

}
