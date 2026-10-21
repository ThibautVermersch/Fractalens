package fr.univartois.butinfo.fractalens.complex.sequence;
import java.util.Objects;
import java.util.function.BiFunction;

import fr.univartois.butinfo.fractalens.complex.Complex;
import fr.univartois.butinfo.fractalens.complex.IComplex;

import java.util.Objects;

public enum ComplexSequenceType {
    /**
     * The Mandelbrot sequence.
     */
    MANDELBROT(MandelbrotSequence::new),
    /**
     * The Julia sequence.
     */
    JULIA((z0, max) -> new JuliaSequence(z0, new Complex(-0.4, 0.6), max)),

    /**
     * The Julia sequence with a different constant.
     */
    JULIA_SPIRAL((z0, max) -> new JuliaSequence(z0, new Complex(0.285, 0.01), max)),

    /**
     * The multibrot sequence.
     */
    MULTIBROT_CUBIC((z0, max) -> new MultibrotSequence(z0, 3, max)),

    /**
     * The multibrot sequence with a different constant.
     */
    MULTIJULIA_CUBIC((z0, max) -> new MultijuliaSequence(z0, new Complex(-0.4, 0.6), 3, max)),

    /**
     * The tricorn sequence.
     */
    TRICORN((z0, max) -> new TricornSequence(z0, z0, max)),

    /**
     * The generalized tricorn sequence.
     */
    GENERALIZED_TRICORN((z0, max) -> new GeneralizedTricornSequence(z0, z0, max, 3)),

    /**
     * The burning ship sequence.
     */
    BURNING_SHIP(BurningShipSequence::new),

    /**
     * The cubic lambda sequence.
     */
    CUBIC_LAMBDA(GenericComplexSequence::createCubicLambdaSequence);

    /**
     * The factory function used to create a new sequence.
     */
    private final BiFunction<IComplex, Integer, IComplexSequence> factory;

    /**
     * Constructor of the enum.
     * @param factory The factory function used to create a new sequence.
     */
    ComplexSequenceType(BiFunction<IComplex, Integer, IComplexSequence> factory) {
        this.factory = factory;
    }

    /**
     * Returns the factory function used to create a new sequence.
     * @return The factory function used to create a new sequence.
     */
    public BiFunction<IComplex, Integer, IComplexSequence> getFactory() {
        return factory;
    }
}
