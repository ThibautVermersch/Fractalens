package fr.univartois.butinfo.fractalens.complex.complex;
import static org.junit.jupiter.api.Assertions.assertEquals;

import fr.univartois.butinfo.fractalens.complex.ComplexPlane;
import fr.univartois.butinfo.fractalens.complex.IComplex;
import fr.univartois.butinfo.fractalens.complex.IComplexPlane;
import fr.univartois.butinfo.fractalens.complex.ZoomComplexPlane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ZoomComplexPlaneTest {

    /**
     * Margin of error for floating point comparisons.
     */
    private static final double EPSILON = 1e-9;

    /**
     * The plane to zoom
     */
    private IComplexPlane plane;

    /**
     * Set up the test
     */
    @BeforeEach
    void setUp() {
        plane = new ComplexPlane(600, 800);
    }

    /**
     * Test that the center of the zoomed plane is at (0, 0)
     */
    @Test
    void testCenterRemainsZero(){
        IComplexPlane zoomedPlane = new ZoomComplexPlane(plane, 0.004);
        IComplex center = zoomedPlane.asComplex(300, 400); // centre de 600 * 800

        assertEquals(0, center.getRealPart(), EPSILON);
        assertEquals(0, center.getImaginaryPart(), EPSILON);
    }

    /**
     * Test that the scaling of the coordinates is correct
     */
    @Test
    void testScalingCoordinates(){
        IComplexPlane zoomedPlane = new ZoomComplexPlane(plane, 0.004);
        IComplex center = zoomedPlane.asComplex(250, 500);
        assertEquals(0.4, center.getRealPart(), EPSILON);
        assertEquals(0.2, center.getImaginaryPart(), EPSILON);
    }

    /**
     * Test that the zoomed plane is equivalent to the direct base plane multiplied by the scale factor
     */
    @Test
    void testEqualityWithDirectBasePlaneMultiplication() {
        double scale = 0.004;
        IComplexPlane zoomedPlane = new ZoomComplexPlane(plane, scale);

        int row = 120;
        int col = 640;

        IComplex expected = plane.asComplex(row, col).multiply(scale);
        IComplex actual = zoomedPlane.asComplex(row, col);

        assertEquals(expected, actual);
    }
}
