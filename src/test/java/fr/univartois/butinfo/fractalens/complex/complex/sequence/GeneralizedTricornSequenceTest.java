package fr.univartois.butinfo.fractalens.complex.complex.sequence;

import fr.univartois.butinfo.fractalens.complex.Complex;
import fr.univartois.butinfo.fractalens.complex.IComplex;
import fr.univartois.butinfo.fractalens.complex.sequence.GeneralizedTricornSequence;
import fr.univartois.butinfo.fractalens.complex.sequence.IComplexSequence;
import fr.univartois.butinfo.fractalens.complex.sequence.TricornSequence;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeneralizedTricornSequenceTest {

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
     * Creates a new sequence implementing the generalized Tricorn sequence.
     *
     * @param first The first term of the sequence.
     * @param constant The constant number in the definition of the considered
     *        sequence.
     * @param degree The degree of the considered sequence.
     * @param nbTerms The maximum number of terms to compute.
     *
     * @return The created generalized Tricorn sequence.
     */
    private static IComplexSequence createGeneralizedTricornSequence(
            IComplex first, IComplex constant, int nbTerms,  int degree) {
        return new GeneralizedTricornSequence(first, constant, nbTerms, degree);
    }

    /**
     * Test method for testsComputeNext.
     */
    @Test
    void testComputeNext() {
        IComplex z0 = createComplex(1, 1);
        IComplex c = createComplex(0, 0);
        IComplexSequence tricorn = createGeneralizedTricornSequence(z0, c, 50, 3);

        IComplex z1 = createComplex(-2, -2);
        assertEquals(z1, tricorn.computeNext(z0));

        IComplex z2 = createComplex(16, 16);
        assertEquals(z2, tricorn.computeNext(z1));
    }

    /**
     * Test method for testsComputeNext with a constant.
     */
    @Test
    void testComputeNextWithConstant() {
        IComplex z0 = createComplex(1, 1);
        IComplex c = createComplex(1, 1);
        IComplexSequence tricorn = createGeneralizedTricornSequence(z0, c, 50, 3);

        IComplex z1 = createComplex(-1, -1);
        assertEquals(z1, tricorn.computeNext(z0));
    }

    /**
     * Test method for testsComputeNext with a degree of two.
     */
    @Test
    void testComputeNextWithDegreeTwoIsTricorn() {
        IComplex z0 = createComplex(0.7, 0.4);
        IComplex c = createComplex(0.3, -0.2);
        IComplexSequence tricorn = new TricornSequence(z0, c, 50);
        IComplexSequence generalized = createGeneralizedTricornSequence(z0, c, 50, 2);

        IComplex z = z0;
        for (int i = 0; i < 5; i++) {
            assertEquals(tricorn.computeNext(z), generalized.computeNext(z));
            z = tricorn.computeNext(z);
        }
    }

    /**
     * Test method for testIteratorOnConvergence with the generalized Tricorn
     */
    @Test
    void testIteratorOnConvergence() {
        IComplex z0 = createComplex(0.5, 0);
        IComplex c = createComplex(0, 0);
        int nbTerms = 50;

        IComplexSequence tricorn = createGeneralizedTricornSequence(z0, c, nbTerms, 3);
        int nbIterations = 0;

        for (var _ : tricorn) {
            nbIterations++;
        }

        assertEquals(nbTerms, nbIterations);
    }

    /**
     * Test method for testIteratorOnDivergence with the generalized Tricorn
     */
    @Test
    void testIteratorOnDivergence() {
        IComplex z0 = createComplex(2, 0);
        IComplex c = createComplex(0.5, 0.5);
        int nbTerms = 50;

        IComplexSequence tricorn = createGeneralizedTricornSequence(z0, c, nbTerms, 3);
        int nbIterations = 0;

        for (var _ : tricorn) {
            nbIterations++;
        }

        assertTrue(nbIterations < nbTerms);
    }

    /**
     * Test method for testInvalidDegree.
     */
    @Test
    void testInvalidDegree() {
        IComplex zero = createComplex(0, 0);

        assertThrows(IllegalArgumentException.class,
                () -> createGeneralizedTricornSequence(zero, zero, 50, 1));
        assertThrows(IllegalArgumentException.class,
                () -> createGeneralizedTricornSequence(zero, zero, 50, -3));
    }

}
