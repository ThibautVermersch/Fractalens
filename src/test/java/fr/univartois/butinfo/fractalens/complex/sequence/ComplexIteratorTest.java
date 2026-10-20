/*
 * FractaLens - A Fractal Generator Library Developed in Lens.
 * Copyright (c) 2022-2026 Romain Wallon - Université d'Artois.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE
 * USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package fr.univartois.butinfo.fractalens.complex.sequence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.UnaryOperator;

import org.junit.jupiter.api.Test;

import fr.univartois.butinfo.fractalens.complex.IComplex;

/**
 * The ComplexIteratorTest tests the class {@link ComplexIterator}.
 * <p>
 * It relies on fake complex numbers and sequences, so that the iterator is tested
 * independently of the actual implementations of {@link IComplex} and
 * {@link IComplexSequence}.
 *
 * @author Yeleen Cividino
 *
 * @version 0.1.0
 */
class ComplexIteratorTest {

    /**
     * Test method for {@link ComplexIterator#next()}.
     */
    @Test
    void testNextReturnsTermsFromFirstTerm() {
        IComplexSequence sequence = new FakeSequence(z -> new FakeComplex(z.abs() / 2));
        Iterator<IComplex> iterator = new ComplexIterator(sequence, new FakeComplex(1), 10);

        assertEquals(new FakeComplex(1), iterator.next());
        assertEquals(new FakeComplex(0.5), iterator.next());
        assertEquals(new FakeComplex(0.25), iterator.next());
    }

    /**
     * Test method for {@link ComplexIterator#hasNext()} when the maximum number of
     * iterations is reached.
     */
    @Test
    void testHasNextStopsAfterMaxIterations() {
        IComplexSequence sequence = new FakeSequence(z -> z);
        Iterator<IComplex> iterator = new ComplexIterator(sequence, new FakeComplex(1), 5);

        for (int i = 0; i < 5; i++) {
            assertTrue(iterator.hasNext());
            iterator.next();
        }

        assertFalse(iterator.hasNext());
    }

    /**
     * Test method for {@link ComplexIterator#hasNext()} when the sequence diverges.
     * A term whose modulus is exactly 2 must still allow the iteration to continue.
     */
    @Test
    void testHasNextStopsOnDivergence() {
        IComplexSequence sequence = new FakeSequence(z -> new FakeComplex(z.abs() * 2));
        Iterator<IComplex> iterator = new ComplexIterator(sequence, new FakeComplex(1), 50);

        assertTrue(iterator.hasNext());
        assertEquals(new FakeComplex(1), iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(new FakeComplex(2), iterator.next());
        assertFalse(iterator.hasNext());
    }

    /**
     * Test method for {@link ComplexIterator#hasNext()} when the first term already
     * diverges.
     */
    @Test
    void testHasNextIsFalseWhenFirstTermDiverges() {
        IComplexSequence sequence = new FakeSequence(z -> z);
        Iterator<IComplex> iterator = new ComplexIterator(sequence, new FakeComplex(3), 50);

        assertFalse(iterator.hasNext());
    }

    /**
     * Test method for {@link ComplexIterator#next()} when there are no more terms to
     * compute.
     */
    @Test
    void testNextThrowsWhenNoMoreTerms() {
        IComplexSequence sequence = new FakeSequence(z -> z);
        Iterator<IComplex> iterator = new ComplexIterator(sequence, new FakeComplex(1), 0);

        assertThrows(NoSuchElementException.class, iterator::next);
    }

    /**
     * A fake complex number lying on the real axis, which only supports the operations
     * needed by {@link ComplexIterator}.
     *
     * @param value The real value of this number.
     */
    private record FakeComplex(double value) implements IComplex {

        @Override
        public double getRealPart() {
            return value;
        }

        @Override
        public double getImaginaryPart() {
            return 0;
        }

        @Override
        public double abs() {
            return Math.abs(value);
        }

        @Override
        public IComplex negate() {
            throw new UnsupportedOperationException();
        }

        @Override
        public IComplex conjugate() {
            throw new UnsupportedOperationException();
        }

        @Override
        public IComplex add(IComplex other) {
            throw new UnsupportedOperationException();
        }

        @Override
        public IComplex subtract(IComplex other) {
            throw new UnsupportedOperationException();
        }

        @Override
        public IComplex multiply(double factor) {
            throw new UnsupportedOperationException();
        }

        @Override
        public IComplex multiply(IComplex other) {
            throw new UnsupportedOperationException();
        }

        @Override
        public IComplex divide(IComplex other) {
            throw new UnsupportedOperationException();
        }

    }

    /**
     * A fake complex sequence whose terms are computed by a given function.
     *
     * @param function The function computing the next term from the previous one.
     */
    private record FakeSequence(UnaryOperator<IComplex> function)
            implements IComplexSequence {

        @Override
        public IComplex computeNext(IComplex previous) {
            return function.apply(previous);
        }

        @Override
        public Iterator<IComplex> iterator() {
            throw new UnsupportedOperationException();
        }

    }

}
