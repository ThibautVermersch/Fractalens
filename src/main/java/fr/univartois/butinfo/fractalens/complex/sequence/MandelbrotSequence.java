package fr.univartois.butinfo.fractalens.complex.sequence;

import fr.univartois.butinfo.fractalens.complex.IComplex;

/**
 * The MandelbrotSequence class represents the complex sequence used to draw the
 * Mandelbrot set.
 * <p>
 * This sequence uses the same formula as Julia's sequence, except that the constant
 * {@code c} is replaced by the complex number for which the sequence is evaluated,
 * i.e., its first term {@code z0}:
 * <pre>
 *     z(n+1) = z(n)<sup>2</sup> + z0
 * </pre>
 *
 * @author Yeleen Cividino
 *
 * @version 0.1.0
 */
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