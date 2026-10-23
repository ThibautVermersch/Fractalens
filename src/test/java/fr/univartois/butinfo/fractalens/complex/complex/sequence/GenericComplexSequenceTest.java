package fr.univartois.butinfo.fractalens.complex.complex.sequence;

import java.util.Iterator;

import fr.univartois.butinfo.fractalens.complex.sequence.GenericComplexSequence;
import org.junit.jupiter.api.Test;

import fr.univartois.butinfo.fractalens.complex.Complex;
import fr.univartois.butinfo.fractalens.complex.IComplex;
import fr.univartois.butinfo.fractalens.complex.sequence.IComplexSequence;

import static org.junit.jupiter.api.Assertions.*;

class GenericComplexSequenceTest {

    /**
     * The epsilon used for floating point comparisons.
     */
    private static final double EPSILON = 1e-9;

    /**
     * Tests that a null lambda throws a NullPointerException.
     */
    @Test
    void testNullFunctionThrowsException() {
        IComplex z0 = new Complex(1.0, 1.0);
        assertThrows(NullPointerException.class, () -> new GenericComplexSequence(z0, 10, null));
    }

    /**
     * Tests a linear sequence: z_n = z_{n-1} + z0.
     */
    @Test
    void testAdditiveSequence() {

        IComplex z0 = new Complex(0.2, 0.0);
        IComplexSequence sequence = new GenericComplexSequence(z0, 5, IComplex::add);
        Iterator<IComplex> it = sequence.iterator();

        assertTrue(it.hasNext());
        IComplex term0 = it.next();
        assertEquals(0.2, term0.getRealPart(), EPSILON);

        assertTrue(it.hasNext());
        IComplex term1 = it.next();
        assertEquals(0.4, term1.getRealPart(), EPSILON);

        assertTrue(it.hasNext());
        IComplex term2 = it.next();
        assertEquals(0.6, term2.getRealPart(), EPSILON);
    }

    /**
     * Tests computing next term directly.
     */
    @Test
    void testComputeNext() {
        IComplex z0 = new Complex(2.0, 0.0);
        GenericComplexSequence sequence = new GenericComplexSequence(z0, 10, (prev, init) -> prev.multiply(2.0));

        IComplex next = sequence.computeNext(z0);
        assertNotNull(next);
        assertEquals(4.0, next.getRealPart(), EPSILON);
    }
}
