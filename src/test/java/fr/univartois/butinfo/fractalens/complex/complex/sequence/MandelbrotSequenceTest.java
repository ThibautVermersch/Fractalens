package fr.univartois.butinfo.fractalens.complex.complex.sequence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;

import fr.univartois.butinfo.fractalens.complex.Complex;
import fr.univartois.butinfo.fractalens.complex.IComplex;
import fr.univartois.butinfo.fractalens.complex.sequence.IComplexSequence;
import fr.univartois.butinfo.fractalens.complex.sequence.MandelbrotSequence;

/**
 * The MandelbrotSequenceTest tests the implementation of {@link IComplexSequence} for
 * the Mandelbrot sequence ({@link MandelbrotSequence}).
 *
 * @author Yeleen Cividino
 *
 * @version 0.1.0
 */
class MandelbrotSequenceTest {

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
     * Test method for {@link MandelbrotSequence#computeNext(IComplex)} on real numbers.
     */
    @Test
    void testComputeNextOnRealNumbers() {
        IComplex z0 = createComplex(0.5, 0);
        IComplexSequence mandelbrot = new MandelbrotSequence(z0, 50);

        // z1 = 0.5² + 0.5
        IComplex z1 = createComplex(0.75, 0);
        assertEquals(z1, mandelbrot.computeNext(z0));

        // z2 = 0.75² + 0.5
        IComplex z2 = createComplex(1.0625, 0);
        assertEquals(z2, mandelbrot.computeNext(z1));
    }

    /**
     * Test method for {@link MandelbrotSequence#computeNext(IComplex)} on complex
     * numbers, with {@code z0 = i}, for which the sequence is periodic.
     */
    @Test
    void testComputeNextOnComplexNumbers() {
        IComplex z0 = createComplex(0, 1);
        IComplexSequence mandelbrot = new MandelbrotSequence(z0, 50);

        // z1 = i² + i = -1 + i
        IComplex z1 = createComplex(-1, 1);
        assertEquals(z1, mandelbrot.computeNext(z0));

        // z2 = (-1 + i)² + i = -2i + i = -i
        IComplex z2 = createComplex(0, -1);
        assertEquals(z2, mandelbrot.computeNext(z1));

        // z3 = (-i)² + i = -1 + i = z1
        assertEquals(z1, mandelbrot.computeNext(z2));
    }

    /**
     * Test method for {@link MandelbrotSequence#computeNext(IComplex)}, checking that
     * the first term (and not a constant) is added at each step.
     */
    @Test
    void testComputeNextAddsFirstTerm() {
        IComplex z0 = createComplex(0.25, -0.5);
        IComplexSequence mandelbrot = new MandelbrotSequence(z0, 50);

        assertEquals(z0, mandelbrot.computeNext(createComplex(0, 0)));
        assertEquals(createComplex(-0.75, -0.5), mandelbrot.computeNext(createComplex(0, 1)));
    }

    /**
     * Test method for {@link MandelbrotSequence#iterator()}, checking the terms that
     * are produced.
     */
    @Test
    void testIteratorTerms() {
        IComplex z0 = createComplex(0, 1);
        IComplexSequence mandelbrot = new MandelbrotSequence(z0, 4);

        List<IComplex> terms = new ArrayList<>();
        for (IComplex term : mandelbrot) {
            terms.add(term);
        }

        List<IComplex> expected = List.of(
                createComplex(0, 1),
                createComplex(-1, 1),
                createComplex(0, -1),
                createComplex(-1, 1));
        assertEquals(expected, terms);
    }

    /**
     * Test method for {@link MandelbrotSequence#iterator()} when the point belongs to
     * the Mandelbrot set.
     */
    @Test
    void testIteratorOnConvergence() {
        int nbTerms = 50;
        assertEquals(nbTerms, countTerms(new MandelbrotSequence(createComplex(0, 0), nbTerms)));
        assertEquals(nbTerms, countTerms(new MandelbrotSequence(createComplex(-1, 0), nbTerms)));
        assertEquals(nbTerms, countTerms(new MandelbrotSequence(createComplex(0, 1), nbTerms)));
        assertEquals(nbTerms, countTerms(new MandelbrotSequence(createComplex(-0.75, 0), nbTerms)));
    }

    /**
     * Test method for {@link MandelbrotSequence#iterator()} when the point does not
     * belong to the Mandelbrot set.
     */
    @Test
    void testIteratorOnDivergence() {
        int nbTerms = 50;

        // 1, 2, 5: the third term exceeds the threshold.
        assertEquals(2, countTerms(new MandelbrotSequence(createComplex(1, 0), nbTerms)));

        // The first term already exceeds the threshold.
        Iterator<IComplex> iterator = new MandelbrotSequence(createComplex(3, 0), nbTerms)
                .iterator();
        assertFalse(iterator.hasNext());
    }

}