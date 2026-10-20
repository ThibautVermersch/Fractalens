package fr.univartois.butinfo.fractalens.complex;

public class TranslatedComplexPlane implements IComplexPlane{

    /**
     * The plane to decorate
     */
    private final IComplexPlane decorated;

    /**
     * The center of the translation
     */
    private final IComplex center;

    /**
     * Constructor of the decorator
     * @param decorated The plane to decorate
     * @param center The center of the translation
     */
    public TranslatedComplexPlane(IComplexPlane decorated, IComplex center) {
        this.decorated = decorated;
        this.center = center;
    }

    /**
     * Returns the complex value of the pixel at the given row and column.
     * @param row The row of the pixel in the image.
     * @param column The column of the pixel in the image.
     *
     * @return The complex number at the given position.
     */
    @Override
    public IComplex asComplex(int row, int column) {
        return this.decorated.asComplex(row, column).add(this.center);
    }

}
