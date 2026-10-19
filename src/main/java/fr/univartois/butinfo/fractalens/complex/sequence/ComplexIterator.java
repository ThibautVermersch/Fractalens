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

import java.util.Iterator;
import java.util.NoSuchElementException;

import fr.univartois.butinfo.fractalens.complex.IComplex;

/**
 * The ComplexIterator class makes it possible to iterate over the terms of any
 * {@link IComplexSequence}, starting from its first term.
 * <p>
 * It only relies on {@link IComplexSequence#computeNext(IComplex)}, so that it can be
 * reused for any kind of complex sequence.
 *
 * @author Yeleen Cividino
 *
 * @version 0.1.0
 */
public class ComplexIterator implements Iterator<IComplex> {

    /**
     * The modulus above which the sequence is considered to diverge.
     */
    private static final double DIVERGENCE_THRESHOLD = 2;

    /**
     * The sequence over which this iterator iterates.
     */
    private final IComplexSequence sequence;

    /**
     * The maximum number of terms to compute.
     */
    private final int maxIterations;

    /**
     * The number of terms computed so far.
     */
    private int nbIterations;

    /**
     * The term to return on the next call to {@link #next()} (initially, the first term
     * of the sequence).
     */
    private IComplex current;

    /**
     * Creates a new ComplexIterator.
     *
     * @param sequence The sequence over which to iterate.
     * @param first The first term of the sequence.
     * @param maxIterations The maximum number of terms to compute.
     */
    public ComplexIterator(IComplexSequence sequence, IComplex first, int maxIterations) {
        this.sequence = sequence;
        this.current = first;
        this.maxIterations = maxIterations;
        this.nbIterations = 0;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.Iterator#hasNext()
     */
    @Override
    public boolean hasNext() {
        return (nbIterations < maxIterations) && (current.abs() <= DIVERGENCE_THRESHOLD);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.Iterator#next()
     */
    @Override
    public IComplex next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more terms to compute in the sequence");
        }

        IComplex term = current;
        current = sequence.computeNext(current);
        nbIterations++;
        return term;
    }

}