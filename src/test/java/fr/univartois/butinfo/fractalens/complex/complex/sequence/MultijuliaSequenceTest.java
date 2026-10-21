package fr.univartois.butinfo.fractalens.complex.complex.sequence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import fr.univartois.butinfo.fractalens.complex.sequence.MultijuliaSequence;
import org.junit.jupiter.api.Test;

import fr.univartois.butinfo.fractalens.complex.Complex;
import fr.univartois.butinfo.fractalens.complex.IComplex;

/**
 * Unit tests for {@link MultijuliaSequence}.
 */
class MultijuliaSequenceTest {

    /**
     * The margin of error for floating point comparisons.
     */
    private static final double EPSILON = 1e-9;

    /**
     * Test that a sequence with a power less than 2 throws an exception.
     */
    @Test
    void testInvalidPowerThrowsException() {
        IComplex z0 = new Complex(0.0, 0.0);
        IComplex c = new Complex(1.0, 0.0);
        assertThrows(IllegalArgumentException.class, () -> new MultijuliaSequence(z0, c, 0, 10));
    }

    /**
     * Test that a null constant throws an exception.
     */
    @Test
    void testNullConstantThrowsException() {
        IComplex z0 = new Complex(0.0, 0.0);
        assertThrows(NullPointerException.class, () -> new MultijuliaSequence(z0, null, 2, 10));
    }

    /**
     * Test that the next term is computed correctly for a power of 3.
     */
    @Test
    void testComputeNextWithPowerThree() {
        IComplex z0 = new Complex(1.0, 1.0);
        IComplex c = new Complex(0.0, 1.0);
        MultijuliaSequence seq = new MultijuliaSequence(z0, c, 3, 10);

        IComplex next = seq.computeNext(z0);

        assertEquals(-2.0, next.getRealPart(), EPSILON);
        assertEquals(3.0, next.getImaginaryPart(), EPSILON);
    }

}
