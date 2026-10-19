package fr.univartois.butinfo.fractalens.complex.sequence;

import fr.univartois.butinfo.fractalens.complex.IComplex;

import java.util.Iterator;

public class JuliaSequence implements IComplexSequence{

    private final IComplex z0;
    private final IComplex c;
    private final int maxIterations;

    public JuliaSequence(IComplex z0, IComplex c, int maxIterations) {
        this.z0 = z0;
        this.c = c;
        this.maxIterations = maxIterations;
    }

    @Override
    public IComplex computeNext(IComplex previous) {
        //Formule de Julia (z1= z0^2 + c)
        return previous.multiply(previous).add(c);
    }

    @Override
    public Iterator<IComplex> iterator() {

        return null;
    }

}
