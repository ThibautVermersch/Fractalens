package fr.univartois.butinfo.fractalens.complex.sequence;

import fr.univartois.butinfo.fractalens.complex.Complex;
import fr.univartois.butinfo.fractalens.complex.IComplex;

public class BurningShipSequence extends AbstractComplexSequence {
    private final IComplex z0;

    public BurningShipSequence(IComplex z0, int maxIterations) {

        super(z0, maxIterations);
        this.z0 = z0;
    }

    @Override
    public IComplex computeNext(IComplex previous) {

        double absRe = Math.abs(previous.getRealPart());
        double absIm = Math.abs(previous.getImaginaryPart());

        IComplex absZ = new Complex(absRe, absIm);

        return absZ.pow(2).add(z0);
    }
}
