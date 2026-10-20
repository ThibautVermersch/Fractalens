package fr.univartois.butinfo.fractalens.complex;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link TranslatedComplexPlane} class.
 */
class TranslatedComplexPlaneTest {

    /**
     * Margin of error for floating point comparisons.
     */
    private static final double EPSILON = 1e-9;

    /**
     * The plane to translate.
     */
    private IComplexPlane basePlane;

    /**
     * Set up the test.
     */
    @BeforeEach
    void setUp() {
        this.basePlane = new ComplexPlane(600, 800);
    }

    /**
     * Tests that the center of the image is translated to the chosen complex number.
     */
    @Test
    void testCenterBecomesTranslatedComplex() {
        IComplex center = new Complex(0.1, 0.6);
        IComplexPlane translatedPlane = new TranslatedComplexPlane(basePlane, center);

        IComplex actual = translatedPlane.asComplex(300, 400);

        assertEquals(0.1, actual.getRealPart(), EPSILON);
        assertEquals(0.6, actual.getImaginaryPart(), EPSILON);
    }

    /**
     * Tests translation on an off-center pixel.
     */
    @Test
    void testOffCenterTranslation() {
        IComplex center = new Complex(-0.47, -0.21);
        IComplexPlane translatedPlane = new TranslatedComplexPlane(basePlane, center);

        int row = 100;
        int col = 200;

        IComplex expected = basePlane.asComplex(row, col).add(center);
        IComplex actual = translatedPlane.asComplex(row, col);

        assertEquals(expected.getRealPart(), actual.getRealPart(), EPSILON);
        assertEquals(expected.getImaginaryPart(), actual.getImaginaryPart(), EPSILON);
    }

    /**
     * Tests combining zoom and translation decorators in the correct order.
     */
    @Test
    void testCombinedZoomAndTranslation() {
        IComplex center = new Complex(0.1, 0.6);
        double scale = 0.001;

        // Apply zoom first, then translate
        IComplexPlane zoomed = new ZoomComplexPlane(basePlane, scale);
        IComplexPlane fullyDecorated = new TranslatedComplexPlane(zoomed, center);

        IComplex actualCenter = fullyDecorated.asComplex(300, 400);

        assertEquals(0.1, actualCenter.getRealPart(), EPSILON);
        assertEquals(0.6, actualCenter.getImaginaryPart(), EPSILON);
    }
}
