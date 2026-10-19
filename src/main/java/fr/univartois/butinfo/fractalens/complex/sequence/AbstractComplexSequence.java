package fr.univartois.butinfo.fractalens.complex.sequence;

import fr.univartois.butinfo.fractalens.complex.IComplex;

import java.util.Iterator;

public abstract class AbstractComplexSequence implements IComplexSequence {

    protected final IComplex initial;
    protected final int maxIterations;

    protected AbstractComplexSequence(IComplex initial, int maxIterations) {
        this.initial = initial;
        this.maxIterations = maxIterations;
    }

    @Override
    public Iterator<IComplex> iterator() {
        return new ComplexSequenceIterator(this, this.initial, this.maxIterations);
    }

    @Override
    public abstract IComplex computeNext(IComplex previous);

}
