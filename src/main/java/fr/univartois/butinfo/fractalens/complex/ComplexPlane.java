package fr.univartois.butinfo.fractalens.complex;

public class ComplexPlane implements IComplexPlane{

    private final int height;
    private final int width;

    public ComplexPlane(int height, int width) {
        this.height = height;
        this.width = width;
    }

    public IComplex asComplex(int row, int column) {
        double a = column - (width/2);
        double b = (height/2) - row;
        return new Complex(a, b);
    }
}
