package fr.univartois.butinfo.fractalens.complex;

/**
 * Plane decorator that zooms the plane
 */
public class ZoomComplexPlane implements IComplexPlane{

    /**
     * Plane to decorate
     */
    private final IComplexPlane decorated;

    /**
     * Zoom factor
     */
    double scale;

    /**
     * Constructor of the decorator
     * @param decorated The plane to decorate
     * @param scale The zoom factor
     */
    public ZoomComplexPlane(IComplexPlane decorated, double scale) {
        this.decorated = decorated;
        this.scale = scale;
    }

    /**
     * Multiplies the complex number by the zoom factor
     * @param row The row of the pixel in the image.
     * @param column The column of the pixel in the image.
     *
     * @return the complex number multiply by the factor
     */
    @Override
    public IComplex asComplex(int row, int column) {
        return this.decorated.asComplex(row, column).multiply(this.scale);
    }

}
