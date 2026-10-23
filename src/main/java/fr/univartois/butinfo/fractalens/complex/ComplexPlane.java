package fr.univartois.butinfo.fractalens.complex;

/**
 * The complex plane.
 */
public class ComplexPlane implements IComplexPlane{

    /**
     * The height of the image.
     */
    private final int height;
    /**
     * The width of the image.
     */
    private final int width;

    /**
     * Constructor
     * @param height The height of the image.
     * @param width  The width of the image.
     */
    public ComplexPlane(int height, int width) {
        this.height = height;
        this.width = width;
    }

    /**
     * Converts a pixel in the image to a complex number.
     * @param row The row of the pixel in the image.
     * @param column The column of the pixel in the image.
     *
     * @return The complex number at the given position.
     */
    public IComplex asComplex(int row, int column) {
        double a = column - (width/2.);
        double b = (height/2.) - row;
        return new Complex(a, b);
    }
}
