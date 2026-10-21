package fr.univartois.butinfo.fractalens.complex.complex.sequence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

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
     * Counts the number of terms produced by the given sequence.
     *
     * @param sequence The sequence to iterate over.
     *
     * @return The number of terms computed before reaching the maximum number of
     *         iterations or detecting the divergence of the sequence.
     */
    private static int countTerms(IComplexSequence sequence) {
        int nbIterations = 0;
        for (var _ : sequence) {
            nbIterations++;
        }
        return nbIterations;
    }

    /**
     * Collects the terms produced by the given sequence.
     *
     * @param sequence The sequence to iterate over.
     *
     * @return The list of the terms of the sequence.
     */
    private static List<IComplex> collectTerms(IComplexSequence sequence) {
        List<IComplex> terms = new ArrayList<>();
        for (IComplex term : sequence) {
            terms.add(term);
        }
        return terms;
    }

    /**
     * Test method for {@link ComplexSequenceType#getFactory()}.
     *
     * @param type The constant to test.
     */
    @ParameterizedTest
    @EnumSource(ComplexSequenceType.class)
    void testGetFactory(ComplexSequenceType type) {
        assertNotNull(type.getFactory());
    }

    /**
     * Test method for {@link ComplexSequenceType#create(IComplex, int)}, checking that
     * the first term of the sequence is the one that was given.
     *
     * @param type The constant to test.
     */
    @ParameterizedTest
    @EnumSource(ComplexSequenceType.class)
    void testCreateKeepsFirstTerm(ComplexSequenceType type) {
        IComplex z0 = createComplex(0.5, 0);
        IComplexSequence sequence = type.create(z0, 50);
        assertNotNull(sequence);
        assertEquals(z0, sequence.iterator().next());
    }

    /**
     * Test method for {@link ComplexSequenceType#create(IComplex, int)} and
     * {@link ComplexSequenceType#getFactory()}, checking that both produce the same
     * terms.
     *
     * @param type The constant to test.
     */
    @ParameterizedTest
    @EnumSource(ComplexSequenceType.class)
    void testCreateAndGetFactoryAreEquivalent(ComplexSequenceType type) {
        IComplex z0 = createComplex(0.25, 0.25);
        BiFunction<IComplex, Integer, IComplexSequence> factory = type.getFactory();

        List<IComplex> viaCreate = collectTerms(type.create(z0, 10));
        List<IComplex> viaFactory = collectTerms(factory.apply(z0, 10));
        assertEquals(viaCreate, viaFactory);
    }

    /**
     * Test method for {@link ComplexSequenceType#create(IComplex, int)} with a
     * {@code null} first term.
     *
     * @param type The constant to test.
     */
    @ParameterizedTest
    @EnumSource(ComplexSequenceType.class)
    void testCreateWithNullFirstTerm(ComplexSequenceType type) {
        assertThrows(NullPointerException.class, () -> type.create(null, 50));
    }

    /**
     * Test method for {@link ComplexSequenceType#create(IComplex, int)}, checking the
     * implementation that is used for each constant.
     */
    @Test
    void testCreateUsesExpectedImplementation() {
        IComplex z0 = createComplex(0, 0);

        assertInstanceOf(MandelbrotSequence.class, ComplexSequenceType.MANDELBROT.create(z0, 50));
        assertInstanceOf(JuliaSequence.class, ComplexSequenceType.JULIA.create(z0, 50));
        assertInstanceOf(JuliaSequence.class, ComplexSequenceType.JULIA_SPIRAL.create(z0, 50));
        assertInstanceOf(MultibrotSequence.class, ComplexSequenceType.MULTIBROT_CUBIC.create(z0, 50));
        assertInstanceOf(MultijuliaSequence.class, ComplexSequenceType.MULTIJULIA_CUBIC.create(z0, 50));
        assertInstanceOf(TricornSequence.class, ComplexSequenceType.TRICORN.create(z0, 50));
        assertInstanceOf(GeneralizedTricornSequence.class,
                ComplexSequenceType.GENERALIZED_TRICORN.create(z0, 50));
        assertInstanceOf(BurningShipSequence.class, ComplexSequenceType.BURNING_SHIP.create(z0, 50));
    }

    /**
     * Test method for the computation of the next term of
     * {@link ComplexSequenceType#MANDELBROT}.
     */
    @Test
    void testComputeNextMandelbrot() {
        IComplex z0 = createComplex(0.5, 0);
        IComplexSequence mandelbrot = ComplexSequenceType.MANDELBROT.create(z0, 50);

        // z1 = 0.5² + 0.5
        IComplex z1 = createComplex(0.75, 0);
        assertEquals(z1, mandelbrot.computeNext(z0));

        // z2 = 0.75² + 0.5
        IComplex z2 = createComplex(1.0625, 0);
        assertEquals(z2, mandelbrot.computeNext(z1));
    }

    /**
     * Test method for the computation of the next term of {@link ComplexSequenceType#JULIA}
     * ({@code c = -0.4 + 0.6i}).
     */
    @Test
    void testComputeNextJulia() {
        IComplex c = createComplex(-0.4, 0.6);
        IComplexSequence julia = ComplexSequenceType.JULIA.create(createComplex(0, 0), 50);

        // 0² + c
        assertEquals(c, julia.computeNext(createComplex(0, 0)));

        // i² + c
        assertEquals(createComplex(-1, 0).add(c), julia.computeNext(createComplex(0, 1)));
    }

    /**
     * Test method for the computation of the next term of
     * {@link ComplexSequenceType#JULIA_SPIRAL} ({@code c = 0.285 + 0.01i}).
     */
    @Test
    void testComputeNextJuliaSpiral() {
        IComplex c = createComplex(0.285, 0.01);
        IComplexSequence julia = ComplexSequenceType.JULIA_SPIRAL.create(createComplex(0, 0), 50);

        // 0² + c
        assertEquals(c, julia.computeNext(createComplex(0, 0)));

        // 1² + c
        assertEquals(createComplex(1, 0).add(c), julia.computeNext(createComplex(1, 0)));
    }

    /**
     * Test method for the computation of the next term of
     * {@link ComplexSequenceType#MULTIBROT_CUBIC}.
     */
    @Test
    void testComputeNextMultibrotCubic() {
        IComplex z0 = createComplex(0.5, 0);
        IComplexSequence multibrot = ComplexSequenceType.MULTIBROT_CUBIC.create(z0, 50);

        // z1 = 1³ + 0.5
        assertEquals(createComplex(1.5, 0), multibrot.computeNext(createComplex(1, 0)));

        // z1 = i³ + 0.5 = -i + 0.5
        assertEquals(createComplex(0.5, -1), multibrot.computeNext(createComplex(0, 1)));
    }

    /**
     * Test method for the computation of the next term of
     * {@link ComplexSequenceType#MULTIJULIA_CUBIC} ({@code c = -0.4 + 0.6i}).
     */
    @Test
    void testComputeNextMultijuliaCubic() {
        IComplex c = createComplex(-0.4, 0.6);
        IComplexSequence multijulia = ComplexSequenceType.MULTIJULIA_CUBIC.create(createComplex(0, 0), 50);

        // 0³ + c
        assertEquals(c, multijulia.computeNext(createComplex(0, 0)));

        // 1³ + c
        assertEquals(createComplex(1, 0).add(c), multijulia.computeNext(createComplex(1, 0)));
    }

    /**
     * Test method for the computation of the next term of
     * {@link ComplexSequenceType#TRICORN}, where the constant is the first term.
     */
    @Test
    void testComputeNextTricorn() {
        IComplex z0 = createComplex(1, 1);
        IComplexSequence tricorn = ComplexSequenceType.TRICORN.create(z0, 50);

        // z1 = (1 - i)² + (1 + i) = -2i + 1 + i = 1 - i
        assertEquals(createComplex(1, -1), tricorn.computeNext(z0));
    }

    /**
     * Test method for the computation of the next term of
     * {@link ComplexSequenceType#GENERALIZED_TRICORN}, where the constant is the first
     * term.
     */
    @Test
    void testComputeNextGeneralizedTricorn() {
        IComplex z0 = createComplex(1, 1);
        IComplexSequence tricorn = ComplexSequenceType.GENERALIZED_TRICORN.create(z0, 50);

        // z1 = (1 - i)³ + (1 + i) = (-2 - 2i) + (1 + i) = -1 - i
        assertEquals(createComplex(-1, -1), tricorn.computeNext(z0));
    }

    /**
     * Test method for the computation of the next term of
     * {@link ComplexSequenceType#BURNING_SHIP}.
     */
    @Test
    void testComputeNextBurningShip() {
        IComplex z0 = createComplex(0.5, 0);
        IComplexSequence ship = ComplexSequenceType.BURNING_SHIP.create(z0, 50);

        // z1 = (|-1| + |-1|i)² + 0.5 = 2i + 0.5
        assertEquals(createComplex(0.5, 2), ship.computeNext(createComplex(-1, -1)));
    }

    /**
     * Test method for the computation of the next term of
     * {@link ComplexSequenceType#CUBIC_LAMBDA}.
     */
    @Test
    void testComputeNextCubicLambda() {
        IComplex z0 = createComplex(0.5, 0);
        IComplexSequence lambda = ComplexSequenceType.CUBIC_LAMBDA.create(z0, 50);

        // z1 = 2³ - 2 + 0.5
        assertEquals(createComplex(6.5, 0), lambda.computeNext(createComplex(2, 0)));

        // z1 = i³ - i + 0.5 = -2i + 0.5
        assertEquals(createComplex(0.5, -2), lambda.computeNext(createComplex(0, 1)));
    }

    /**
     * Test method for the iteration over the sequences for which {@code 0} is a fixed
     * point, i.e., that never diverge when starting from {@code 0}.
     *
     * @param type The constant to test.
     */
    @ParameterizedTest
    @EnumSource(value = ComplexSequenceType.class, names = { "MANDELBROT", "MULTIBROT_CUBIC",
            "TRICORN", "GENERALIZED_TRICORN", "BURNING_SHIP", "CUBIC_LAMBDA" })
    void testIteratorOnConvergence(ComplexSequenceType type) {
        int nbTerms = 50;
        assertEquals(nbTerms, countTerms(type.create(createComplex(0, 0), nbTerms)));
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
        assertFalse(type.create(createComplex(10, 0), 50).iterator().hasNext());
    }

}
