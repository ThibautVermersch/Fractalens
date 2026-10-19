package fr.univartois.butinfo.fractalens.complex.sequence;

import fr.univartois.butinfo.fractalens.complex.IComplex;

import java.util.Iterator;

public class JuliaSequence extends AbstractComplexSequence{
    private final IComplex c;

    public JuliaSequence(IComplex initial ,IComplex c, int maxIterations) {
        super(initial, maxIterations);
        this.c = c;
    }

    @Override
    public IComplex computeNext(IComplex previous) {
        //Formule de Julia (z1= z0^2 + c)
        return previous.multiply(previous).add(c);
    }

    public IComplex getC() {
        return c;
    }
}
