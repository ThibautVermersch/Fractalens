package fr.univartois.butinfo.fractalens.complex.complex.sequence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import fr.univartois.butinfo.fractalens.complex.Complex;
import fr.univartois.butinfo.fractalens.complex.IComplex;
import fr.univartois.butinfo.fractalens.complex.sequence.BurningShipSequence;
import fr.univartois.butinfo.fractalens.complex.sequence.ComplexSequenceType;
import fr.univartois.butinfo.fractalens.complex.sequence.GeneralizedTricornSequence;
import fr.univartois.butinfo.fractalens.complex.sequence.IComplexSequence;
import fr.univartois.butinfo.fractalens.complex.sequence.JuliaSequence;
import fr.univartois.butinfo.fractalens.complex.sequence.MandelbrotSequence;
import fr.univartois.butinfo.fractalens.complex.sequence.MultibrotSequence;
import fr.univartois.butinfo.fractalens.complex.sequence.MultijuliaSequence;
import fr.univartois.butinfo.fractalens.complex.sequence.TricornSequence;

/**
 * The ComplexSequenceTypeTest tests the enumeration {@link ComplexSequenceType}, which
 * associates each constant with a factory creating the corresponding
 * {@link IComplexSequence}.
 *
 * @author Yeleen Cividino
 *
 * @version 0.1.0
 */
class ComplexSequenceTypeTest {

    /**
     * Creates a new complex number.
     *
     * @param realPart The real part of the complex number.
     * @param imaginaryPart The imaginary part of the complex number.
     *
     * @return The created complex number.
     */
    private static IComplex createComplex(double realPart, double imaginaryPart) {
        return new Complex(realPart, imaginaryPart);
    }

    /**
     * Creates a sequence of the given type, using the factory associated with it.
     *
     * @param type The type of sequence to create.
     * @param first The first term of the sequence.
     *
     * @return The created sequence.
     */
    private static IComplexSequence create(ComplexSequenceType type, IComplex first) {
        return type.getFactory().apply(first, 50);
    }

    /**
     * Test method for {@link ComplexSequenceType#getFactory()}, checking that every
     * constant creates a sequence starting with the given first term.
     *
     * @param type The constant to test.
     */
    @ParameterizedTest
    @EnumSource(ComplexSequenceType.class)
    void testGetFactory(ComplexSequenceType type) {
        IComplex z0 = createComplex(0.5, 0);
        IComplexSequence sequence = create(type, z0);
        assertNotNull(sequence);
        assertEquals(z0, sequence.iterator().next());
    }

    /**
     * Test method for {@link ComplexSequenceType#getFactory()}, checking the
     * implementation that is used for each constant.
     */
    @Test
    void testGetFactoryUsesExpectedImplementation() {
        IComplex z0 = createComplex(0, 0);

        assertInstanceOf(MandelbrotSequence.class, create(ComplexSequenceType.MANDELBROT, z0));
        assertInstanceOf(JuliaSequence.class, create(ComplexSequenceType.JULIA, z0));
        assertInstanceOf(JuliaSequence.class, create(ComplexSequenceType.JULIA_SPIRAL, z0));
        assertInstanceOf(MultibrotSequence.class, create(ComplexSequenceType.MULTIBROT_CUBIC, z0));
        assertInstanceOf(MultijuliaSequence.class, create(ComplexSequenceType.MULTIJULIA_CUBIC, z0));
        assertInstanceOf(TricornSequence.class, create(ComplexSequenceType.TRICORN, z0));
        assertInstanceOf(GeneralizedTricornSequence.class,
                create(ComplexSequenceType.GENERALIZED_TRICORN, z0));
        assertInstanceOf(BurningShipSequence.class, create(ComplexSequenceType.BURNING_SHIP, z0));
    }

    /**
     * Test method for the computation of the next term of
     * {@link ComplexSequenceType#MANDELBROT}.
     */
    @Test
    void testComputeNextMandelbrot() {
        IComplex z0 = createComplex(0.5, 0);
        // z1 = 0.5² + 0.5
        assertEquals(createComplex(0.75, 0), create(ComplexSequenceType.MANDELBROT, z0).computeNext(z0));
    }

    /**
     * Test method for the computation of the next term of {@link ComplexSequenceType#JULIA}
     * ({@code c = -0.4 + 0.6i}).
     */
    @Test
    void testComputeNextJulia() {
        IComplex c = createComplex(-0.4, 0.6);
        IComplexSequence julia = create(ComplexSequenceType.JULIA, createComplex(0, 0));

        // 0² + c
        assertEquals(c, julia.computeNext(createComplex(0, 0)));
    }

    /**
     * Test method for the computation of the next term of
     * {@link ComplexSequenceType#MULTIBROT_CUBIC}.
     */
    @Test
    void testComputeNextMultibrotCubic() {
        IComplex z0 = createComplex(0.5, 0);
        // z1 = 1³ + 0.5
        assertEquals(createComplex(1.5, 0),
                create(ComplexSequenceType.MULTIBROT_CUBIC, z0).computeNext(createComplex(1, 0)));
    }

    /**
     * Test method for the computation of the next term of
     * {@link ComplexSequenceType#TRICORN}, where the constant is the first term.
     */
    @Test
    void testComputeNextTricorn() {
        IComplex z0 = createComplex(1, 1);
        // z1 = (1 - i)² + (1 + i) = 1 - i
        assertEquals(createComplex(1, -1), create(ComplexSequenceType.TRICORN, z0).computeNext(z0));
    }

    /**
     * Test method for the computation of the next term of
     * {@link ComplexSequenceType#BURNING_SHIP}.
     */
    @Test
    void testComputeNextBurningShip() {
        IComplex z0 = createComplex(0.5, 0);
        // z1 = (|-1| + |-1|i)² + 0.5 = 2i + 0.5
        assertEquals(createComplex(0.5, 2),
                create(ComplexSequenceType.BURNING_SHIP, z0).computeNext(createComplex(-1, -1)));
    }

    /**
     * Test method for the computation of the next term of
     * {@link ComplexSequenceType#CUBIC_LAMBDA}.
     */
    @Test
    void testComputeNextCubicLambda() {
        IComplex z0 = createComplex(0.5, 0);
        // z1 = 2³ - 2 + 0.5
        assertEquals(createComplex(6.5, 0),
                create(ComplexSequenceType.CUBIC_LAMBDA, z0).computeNext(createComplex(2, 0)));
    }

    /**
     * Test method for the iteration over every sequence when the first term already
     * exceeds the threshold.
     *
     * @param type The constant to test.
     */
    @ParameterizedTest
    @EnumSource(ComplexSequenceType.class)
    void testIteratorOnDivergence(ComplexSequenceType type) {
        assertFalse(create(type, createComplex(10, 0)).iterator().hasNext());
    }

}
