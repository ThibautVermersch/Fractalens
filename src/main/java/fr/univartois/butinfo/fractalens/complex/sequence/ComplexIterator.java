package fr.univartois.butinfo.fractalens.complex.sequence;

import java.util.Iterator;
import java.util.NoSuchElementException;

import fr.univartois.butinfo.fractalens.complex.IComplex;

public class ComplexIterator implements Iterator<IComplex> {

    private static final double DIVERGENCE_THRESHOLD = 2;

    private final IComplexSequence sequence;
    private final int maxIterations;
    private int nbIterations;
    private IComplex current;

    public ComplexIterator(IComplexSequence sequence, IComplex first, int maxIterations) {
        this.sequence = sequence;
        this.current = first;
        this.maxIterations = maxIterations;
        this.nbIterations = 0;
    }

    @Override
    public boolean hasNext() {
        return (nbIterations < maxIterations) && (current.abs() <= DIVERGENCE_THRESHOLD);
    }

    @Override
    public IComplex next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more terms to compute in the sequence");
        }
        current = sequence.computeNext(current);
        nbIterations++;
        return current;
    }
}