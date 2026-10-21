package fr.univartois.butinfo.fractalens.complex.sequence;
import java.util.Objects;
import java.util.function.BiFunction;

import fr.univartois.butinfo.fractalens.complex.Complex;
import fr.univartois.butinfo.fractalens.complex.IComplex;

import java.util.Objects;

public enum ComplexSequenceType {

    MANDELBROT(MandelbrotSequence::new),

    JULIA((z0, max) -> new JuliaSequence(z0, new Complex(-0.4, 0.6), max)),

    JULIA_SPIRAL((z0, max) -> new JuliaSequence(z0, new Complex(0.285, 0.01), max)),

    MULTIBROT_CUBIC((z0, max) -> new MultibrotSequence(z0, 3, max)),

    MULTIJULIA_CUBIC((z0, max) -> new MultijuliaSequence(z0, new Complex(-0.4, 0.6), 3, max)),

    TRICORN((z0, max) -> new TricornSequence(z0, z0, max)),

    GENERALIZED_TRICORN((z0, max) -> new GeneralizedTricornSequence(z0, z0, max, 3)),

    BURNING_SHIP(BurningShipSequence::new),

    CUBIC_LAMBDA(GenericComplexSequence::createCubicLambdaSequence);

    private final BiFunction<IComplex, Integer, IComplexSequence> factory;

    ComplexSequenceType(BiFunction<IComplex, Integer, IComplexSequence> factory) {
        this.factory = factory;
    }

    public BiFunction<IComplex, Integer, IComplexSequence> getFactory() {
        return factory;
    }

    public IComplexSequence create(IComplex initial, int maxIterations) {
        Objects.requireNonNull(initial, "The first term cannot be null");
        return factory.apply(initial, maxIterations);
    }
}
