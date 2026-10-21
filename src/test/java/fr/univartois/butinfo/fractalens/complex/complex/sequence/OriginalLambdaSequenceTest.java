package fr.univartois.butinfo.fractalens.complex.complex.sequence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;

import fr.univartois.butinfo.fractalens.complex.sequence.GenericComplexSequence;
import fr.univartois.butinfo.fractalens.complex.sequence.IComplexSequence;
import org.junit.jupiter.api.Test;

import fr.univartois.butinfo.fractalens.complex.Complex;
import fr.univartois.butinfo.fractalens.complex.IComplex;

/**
 * Tests for the custom cubic sequence defined using a lambda expression.
 */
class OriginalLambdaSequenceTest {

    /**
     * The margin of error for floating point comparisons.
     */
    private static final double EPSILON = 1e-9;

    /**
     * Test that the sequence can be created and evaluated.
     */
    @Test
    void testSequenceCreation() {
        IComplex z0 = new Complex(0.2, 0.1);
        IComplexSequence seq = new GenericComplexSequence(
                z0, 50,
                (prev, init) -> prev.pow(3).add(prev.multiply(-1.0)).add(init)
        );
        assertNotNull(seq);
    }

    /**
     * Test that the first step of the sequence is correctly evaluated.
     */
    @Test
    void testFirstStepEvaluation() {
        IComplex z0 = new Complex(0.5, 0.0);
        GenericComplexSequence seq = new GenericComplexSequence(
                z0, 10,
                (prev, init) -> prev.pow(3).add(prev.multiply(-1.0)).add(init)
        );

        IComplex next = seq.computeNext(z0);

        assertEquals(0.125, next.getRealPart(), EPSILON);
        assertEquals(0.0, next.getImaginaryPart(), EPSILON);
    }

    /**
     * Test that the iterator returns the correct sequence of terms.
     */
    @Test
    void testIteratorProgression() {
        IComplex z0 = new Complex(0.1, 0.0);
        IComplexSequence seq = new GenericComplexSequence(
                z0, 5,
                (prev, init) -> prev.pow(3).add(prev.multiply(-1.0)).add(init)
        );

        Iterator<IComplex> it = seq.iterator();

        assertTrue(it.hasNext());
        IComplex term0 = it.next();
        assertEquals(0.1, term0.getRealPart(), EPSILON);

        assertTrue(it.hasNext());
        IComplex term1 = it.next();
        // 0.1^3 - 0.1 + 0.1 = 0.001
        assertEquals(0.001, term1.getRealPart(), EPSILON);
    }
}
