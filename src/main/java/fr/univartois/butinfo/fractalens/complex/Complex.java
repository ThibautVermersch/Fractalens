package fr.univartois.butinfo.fractalens.complex;

import java.util.Objects;

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
    public String toString() {
        return a+" + "+b+"i";
    }

    @Override
    public IComplex divide(IComplex other) {
        double c = other.getRealPart();
        double d = other.getImaginaryPart();
        double denominator = c * c + d * d;
        return new Complex((a*c+b*d)/denominator,(b*c-a*d)/denominator);
    }

    /**
     * Raises this complex number to the given power, by multiplying it by itself
     * {@code exponent} times.
     *
     * @param exponent The (non-negative) power to which this number is raised.
     *
     * @return The result of {@code this}<sup>{@code exponent}</sup>.
     *
     * @throws IllegalArgumentException If {@code exponent} is negative.
     */
    @Override
    public IComplex pow(int exponent) {
        if (exponent < 0) {
            throw new IllegalArgumentException("The exponent must not be negative: " + exponent);
        }

        IComplex result = new Complex(1, 0);
        for (int i = 0; i < exponent; i++) {
            result = result.multiply(this);
        }
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof IComplex)) return false;
        IComplex other = (IComplex) obj;
        return this.a == other.getRealPart() && this.b == other.getImaginaryPart();
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b);
    }
}