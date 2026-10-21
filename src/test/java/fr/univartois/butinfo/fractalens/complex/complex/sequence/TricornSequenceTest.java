package fr.univartois.butinfo.fractalens.complex.complex.sequence;

import fr.univartois.butinfo.fractalens.complex.Complex;
import fr.univartois.butinfo.fractalens.complex.IComplex;
import fr.univartois.butinfo.fractalens.complex.sequence.IComplexSequence;
import fr.univartois.butinfo.fractalens.complex.sequence.TricornSequence;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TricornSequenceTest {

    /**
     * Creates a new complex number.
     * @param realPart
     * @param imaginaryPart
     * @return
     */
    private static IComplex createComplex(double realPart, double imaginaryPart) {
        return new Complex(realPart, imaginaryPart);
    }

    /**
     * Creates a new sequence implementing Tricorn's sequence
     * @param first
     * @param constant
     * @param nbTerms
     * @return
     */
    private static IComplexSequence createTricornSequence(
            IComplex first, IComplex constant, int nbTerms) {
        return new TricornSequence(first, constant, nbTerms);
    }

    /**
     * Test method for testsComputeNext.
     */
    @Test
    void testComputeNext() {
        IComplex z0 = createComplex(1, 1);
        IComplex c = createComplex(0, 0);
        IComplexSequence tricorn = createTricornSequence(z0, c, 50);

        IComplex z1 = createComplex(0, -2);
        assertEquals(z1, tricorn.computeNext(z0));

        IComplex z2 = createComplex(-4, 0);
        assertEquals(z2, tricorn.computeNext(z1));

        IComplex z3 = createComplex(16, 0);
        assertEquals(z3, tricorn.computeNext(z2));
    }

    /**
     * Test method for testsComputeNext with a constant.
     */
    @Test
    void testComputeNextWithConstant() {
        IComplex z0 = createComplex(1, 1);
        IComplex c = createComplex(0.5, 0.5);
        IComplexSequence tricorn = createTricornSequence(z0, c, 50);

        IComplex z1 = createComplex(0.5, -1.5);
        assertEquals(z1, tricorn.computeNext(z0));
    }

    /**
     * Test method for testIteratorOnConvergence with Tricorn's sequence.
     */
    @Test
    void testIteratorOnConvergence() {
        IComplex z0 = createComplex(0.5, 0);
        IComplex c = createComplex(0, 0);
        int nbTerms = 50;

        IComplexSequence tricorn = createTricornSequence(z0, c, nbTerms);
        int nbIterations = 0;

        for (var _ : tricorn) {
            nbIterations++;
        }

        assertEquals(nbTerms, nbIterations);
    }

    /**
     * Test method for testIteratorOnDivergence with Tricorn's sequence.
     */
    @Test
    void testIteratorOnDivergence() {
        IComplex z0 = createComplex(2, 0);
        IComplex c = createComplex(0.5, 0.5);
        int nbTerms = 50;

        IComplexSequence tricorn = createTricornSequence(z0, c, nbTerms);
        int nbIterations = 0;

        for (var _ : tricorn) {
            nbIterations++;
        }

        assertTrue(nbIterations < nbTerms);
    }

}
