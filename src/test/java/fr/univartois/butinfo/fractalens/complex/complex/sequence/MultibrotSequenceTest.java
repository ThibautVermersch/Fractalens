package fr.univartois.butinfo.fractalens.complex.complex.sequence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import fr.univartois.butinfo.fractalens.complex.Complex;
import fr.univartois.butinfo.fractalens.complex.IComplex;
import fr.univartois.butinfo.fractalens.complex.sequence.IComplexSequence;
import fr.univartois.butinfo.fractalens.complex.sequence.MandelbrotSequence;
import fr.univartois.butinfo.fractalens.complex.sequence.MultibrotSequence;

/**
 * The MultibrotSequenceTest tests the implementation of {@link IComplexSequence} for
 * the Multibrot sequence ({@link MultibrotSequence}), i.e., the generalized Mandelbrot
 * sequence.
 *
 * @author Yeleen Cividino
 *
 * @version 0.1.0
 */
class MultibrotSequenceTest {

    /**
     * Creates a new complex number.
     *
     * @param realPart The real part of the complex number.
     * @param imaginaryPart The imaginary part of the complex number.
     *
     * @return The created complex number.
     */
    private static IComplex createComplex(double realPart, double imaginaryPart) {
        return new Complex(realPart, imaginaryPart);
    }

    /**
     * Counts the number of terms produced by the given sequence.
     *
     * @param sequence The sequence to iterate over.
     *
     * @return The number of terms computed before reaching the maximum number of
     *         iterations or detecting the divergence of the sequence.
     */
    private static int countTerms(IComplexSequence sequence) {
        int nbIterations = 0;
        for (var _ : sequence) {
            nbIterations++;
        }
        return nbIterations;
    }

    /**
     * Test method for
     * {@link MultibrotSequence#MultibrotSequence(IComplex, int, int)} when the power is
     * too small.
     *
     * @param power The (invalid) power to use.
     */
    @ParameterizedTest
    @ValueSource(ints = { -3, 0, 1 })
    void testConstructorWithInvalidPower(int power) {
        IComplex z0 = createComplex(0, 0);
        assertThrows(IllegalArgumentException.class, () -> new MultibrotSequence(z0, power, 50));
    }

    /**
     * Test method for {@link MultibrotSequence#getPower()}.
     */
    @Test
    void testGetPower() {
        IComplex z0 = createComplex(0, 0);
        assertEquals(2, new MultibrotSequence(z0, 2, 50).getPower());
        assertEquals(5, new MultibrotSequence(z0, 5, 50).getPower());
    }

    /**
     * Test method for {@link MultibrotSequence#computeNext(IComplex)} on real numbers.
     */
    @Test
    void testComputeNextOnRealNumbers() {
        IComplex z0 = createComplex(0.5, 0);
        IComplexSequence multibrot = new MultibrotSequence(z0, 3, 50);

        // z1 = 0.5³ + 0.5
        assertEquals(createComplex(0.625, 0), multibrot.computeNext(z0));

        // The first term (and not a constant) is added at each step.
        assertEquals(z0, multibrot.computeNext(createComplex(0, 0)));

        // 2^4 + 0.5
        IComplexSequence power4 = new MultibrotSequence(z0, 4, 50);
        assertEquals(createComplex(16.5, 0), power4.computeNext(createComplex(2, 0)));
    }

    /**
     * Test method for {@link MultibrotSequence#computeNext(IComplex)} on complex
     * numbers, with {@code z0 = i} and {@code p = 3}, for which the sequence is
     * periodic.
     */
    @Test
    void testComputeNextOnComplexNumbers() {
        IComplex z0 = createComplex(0, 1);
        IComplexSequence multibrot = new MultibrotSequence(z0, 3, 50);

        // z1 = i³ + i = -i + i = 0
        IComplex z1 = createComplex(0, 0);
        assertEquals(z1, multibrot.computeNext(z0));

        // z2 = 0³ + i = i = z0
        assertEquals(z0, multibrot.computeNext(z1));

        // (1 + i)³ + i = -2 + 2i + i
        assertEquals(createComplex(-2, 3), multibrot.computeNext(createComplex(1, 1)));
    }

    /**
     * Test method for {@link MultibrotSequence#computeNext(IComplex)}, checking that
     * the Multibrot sequence with power {@code 2} is the Mandelbrot sequence.
     */
    @Test
    void testComputeNextWithPowerTwoIsMandelbrot() {
        IComplex z0 = createComplex(0.3, -0.5);
        IComplexSequence multibrot = new MultibrotSequence(z0, 2, 50);
        IComplexSequence mandelbrot = new MandelbrotSequence(z0, 50);

        IComplex term = z0;
        for (int i = 0; i < 10; i++) {
            IComplex next = mandelbrot.computeNext(term);
            assertEquals(next, multibrot.computeNext(term));
            term = next;
        }
    }

    /**
     * Test method for {@link MultibrotSequence#iterator()} when the point belongs to
     * the Multibrot set.
     */
    @Test
    void testIteratorOnConvergence() {
        int nbTerms = 50;
        assertEquals(nbTerms, countTerms(new MultibrotSequence(createComplex(0, 0), 3, nbTerms)));
        assertEquals(nbTerms, countTerms(new MultibrotSequence(createComplex(0, 1), 3, nbTerms)));
        assertEquals(nbTerms, countTerms(new MultibrotSequence(createComplex(0.5, 0), 5, nbTerms)));
    }

    /**
     * Test method for {@link MultibrotSequence#iterator()} when the point does not
     * belong to the Multibrot set.
     */
    @Test
    void testIteratorOnDivergence() {
        int nbTerms = 50;

        // 1, 2, 9: the third term exceeds the threshold.
        assertEquals(2, countTerms(new MultibrotSequence(createComplex(1, 0), 3, nbTerms)));

        // 1, 2, 17: the third term exceeds the threshold.
        assertEquals(2, countTerms(new MultibrotSequence(createComplex(1, 0), 4, nbTerms)));

        // The first term already exceeds the threshold.
        assertEquals(0, countTerms(new MultibrotSequence(createComplex(0, 3), 3, nbTerms)));
    }

}