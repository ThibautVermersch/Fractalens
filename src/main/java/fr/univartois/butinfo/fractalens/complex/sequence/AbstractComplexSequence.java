package fr.univartois.butinfo.fractalens.complex.sequence;

import fr.univartois.butinfo.fractalens.complex.IComplex;

import java.util.Iterator;

/**
 * Abstract class for complex sequences.
 */
public abstract class AbstractComplexSequence implements IComplexSequence {

    /**
     * The initial complex amount of the sequence.
     */
    protected final IComplex initial;

    /**
     * The maximum number of iterations to compute.
     */
    protected final int maxIterations;

    /**
     * Constructor of the abstract class ComplexSequence
     * @param initial The initial complex number of the sequence.
     * @param maxIterations The maximum number of iterations to compute.
     */
    protected AbstractComplexSequence(IComplex initial, int maxIterations) {
        this.initial = initial;
        this.maxIterations = maxIterations;
    }

    /**
     * Returns an iterator over the sequence.
     * @return An iterator over the sequence.
     */
    @Override
    public Iterator<IComplex> iterator() {
        return new ComplexIterator(this, this.initial, this.maxIterations);
    }

    /**
     * Returns the initial complex number of the sequence.
     * @return The initial complex number of the sequence.
     */
    public IComplex getInitial() {
        return initial;
    }

}
