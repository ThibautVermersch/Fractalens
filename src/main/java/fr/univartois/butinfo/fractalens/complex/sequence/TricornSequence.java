package fr.univartois.butinfo.fractalens.complex.sequence;

import fr.univartois.butinfo.fractalens.complex.IComplex;

public class TricornSequence extends AbstractComplexSequence{
    private final IComplex c;

    public TricornSequence(IComplex initial, IComplex c, int maxIterations) {
        super(initial, maxIterations);
        this.c = c;
    }

    @Override
    public IComplex computeNext(IComplex previous) {
        IComplex conjugate = previous.conjugate();
        return conjugate.multiply(conjugate).add(c);
    }

    public IComplex getC() {
        return c;
    }
}
