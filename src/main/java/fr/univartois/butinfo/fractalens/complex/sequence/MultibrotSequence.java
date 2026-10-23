package fr.univartois.butinfo.fractalens.complex.sequence;

import fr.univartois.butinfo.fractalens.complex.IComplex;

/**
 * The MultibrotSequence class represents the generalization of the Mandelbrot sequence
 * to a power {@code p} greater than or equal to {@code 2}:
 * <pre>
 *     z(n+1) = z(n)<sup>p</sup> + z0
 * </pre>
 * The fractal obtained from this sequence has {@code p - 1} "lobes" (with
 * {@code p = 2}, this is exactly the Mandelbrot set).
 *
 * @author Yeleen Cividino
 *
 * @version 0.1.0
 */
public class MultibrotSequence extends AbstractComplexSequence {

    /**
     * The smallest power that can be used in this sequence.
     */
    private static final int MIN_POWER = 2;

    /**
     * The power {@code p} to which the previous term is raised.
     */
    private final int power;

    /**
     * Creates a new MultibrotSequence.
     *
     * @param initial The first term {@code z0} of the sequence, i.e., the complex
     *        number for which the sequence is evaluated.
     * @param power The power {@code p} to which the previous term is raised (at least
     *        {@code 2}).
     * @param maxIterations The maximum number of terms to compute.
     *
     * @throws IllegalArgumentException If {@code power} is less than {@code 2}.
     */
    public MultibrotSequence(IComplex initial, int power, int maxIterations) {
        if (power < MIN_POWER) {
            throw new IllegalArgumentException(
                    "The power must be at least " + MIN_POWER + ": " + power);
        }
        super(initial, maxIterations);
        this.power = power;
    }

    /**
     * Computes the next term of the sequence, using the formula
     * {@code z(n+1) = z(n)^p + z0}.
     *
     * @param previous The previous term {@code z(n)} of the sequence.
     *
     * @return The next term {@code z(n+1)} of the sequence.
     */
    @Override
    public IComplex computeNext(IComplex previous) {
        return previous.pow(power).add(initial);
    }

    /**
     * Gives the power {@code p} used in this sequence.
     *
     * @return The power to which the previous term is raised.
     */
    public int getPower() {
        return power;
    }

}
