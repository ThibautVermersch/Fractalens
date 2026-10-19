package fr.univartois.butinfo.fractalens.complex;

public class Complex implements IComplex{


    private double a;
    private double b;



    public Complex(double a, double b) {
        this.a = a;
        this.b = b;
    }


    @Override
    public double getRealPart() {
        return a;
    }

    @Override
    public double getImaginaryPart() {
        return b;
    }

    @Override
    public double abs() {
        return Math.sqrt(a*a+b*b);
    }

    @Override
    public IComplex negate() {
        return new Complex(-a,-b);
    }

    @Override
    public IComplex conjugate() {
        return new Complex(a,-b);
    }

    @Override
    public IComplex add(IComplex other) {
        return new Complex(a+other.getRealPart(),b+ other.getImaginaryPart());
    }

    @Override
    public IComplex subtract(IComplex other) {
        return new Complex(a-other.getRealPart(),b-other.getImaginaryPart());
    }

    @Override
    public IComplex multiply(double value) {
        return new Complex(value*a,value*b);
    }

    @Override
    public IComplex multiply(IComplex other) {
        double c = other.getRealPart();
        double d = other.getImaginaryPart();
        return new Complex(a*c-b*d,a*d+b*c);
    }

    @Override
    public IComplex divide(IComplex other) {
        double c = other.getRealPart();
        double d = other.getImaginaryPart();
        double denominator = c * c + d * d;
        return new Complex((a*c+b*d)/denominator,(b*c-a*d)/denominator);
    }

}
