package fr.univartois.butinfo.fractalens.complex;

import java.util.Objects;

/**
 * A complex number represented by its real and imaginary parts.
 */
public class Complex implements IComplex{

    /**
     * The real part of this complex number.
     */
    private final double a;

    /**
     * The imaginary part of this complex number.
     */
    private final double b;

    /**
     * Constructor of the complex number.
     * @param a real part of the complex number
     * @param b imaginary part of the complex number
     */
    public Complex(double a, double b) {
        this.a = a;
        this.b = b;
    }

    /**
     * Gives the real part of this complex number.
     *
     * @return The real part of this complex number.
     */
    @Override
    public double getRealPart() {
        return a;
    }

    /**
     * Gives the imaginary part of this complex number.
     *
     * @return The imaginary part of this complex number.
     */
    @Override
    public double getImaginaryPart() {
        return b;
    }

    /**
     * Gives the modulus of this complex number.
     *
     * @return The modulus of this complex number.
     */
    @Override
    public double abs() {
        return Math.sqrt(a*a+b*b);
    }

    /**
     * Gives the opposite of this complex number.
     *
     * @return The opposite of this complex number.
     */
    @Override
    public IComplex negate() {
        return new Complex(-a,-b);
    }

    /**
     * Gives the conjugate of this complex number.
     *
     * @return The conjugate of this complex number.
     */
    @Override
    public IComplex conjugate() {
        return new Complex(a,-b);
    }

    /**
     * Adds this complex number to an other one.
     *
     * @param other The complex number to add to this one.
     *
     * @return The result of {@code this + other}.
     */
    @Override
    public IComplex add(IComplex other) {
        return new Complex(a+other.getRealPart(),b+ other.getImaginaryPart());
    }

    /**
     * Subtracts a complex number from this one.
     *
     * @param other The complex number to subtract from this one.
     *
     * @return The result of {@code this - other}.
     */
    @Override
    public IComplex subtract(IComplex other) {
        return new Complex(a-other.getRealPart(),b-other.getImaginaryPart());
    }

    /**
     * Multiplies this complex number by a real value.
     *
     * @param value The value by which to multiply this number.
     *
     * @return The result of {@code this * value}.
     */
    @Override
    public IComplex multiply(double value) {
        return new Complex(value*a,value*b);
    }

    /**
     * Multiplies this complex number by an other one.
     *
     * @param other The complex number to multiply this one with.
     *
     * @return The result of {@code this * other}.
     */
    @Override
    public IComplex multiply(IComplex other) {
        double c = other.getRealPart();
        double d = other.getImaginaryPart();
        return new Complex(a*c-b*d,a*d+b*c);
    }

    /**
     * Divides this complex number by an other one.
     *
     * @param other The complex number to divide this one by.
     *
     * @return The result of {@code this / other}.
     */
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

    /**
     * Squares this complex number.
     *
     * @return This complex number squared.
     */

    public IComplex squared(){
        return new Complex(a*a - b*b,2*a*b);
    }

    /**
     * Returns a string representation of this complex number in algebraic form (a + bi).
     *
     * @return The algebraic string representation of this complex number.
     */
    @Override
    public String toString() {
        return a+" + "+b+"i";
    }


    /**
     * Compares this complex number to the specified object for equality.
     * The result is true if and only if the argument is not null and is an
     * {@code IComplex} object with the same real and imaginary parts.
     *
     * @param obj The object to compare with.
     * @return {@code true} if the objects are equal; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof IComplex)) return false;
        IComplex other = (IComplex) obj;
        return this.a == other.getRealPart() && this.b == other.getImaginaryPart();
    }

    /**
     * Returns a hash code value for this complex number, based on its
     * real and imaginary parts.
     *
     * @return A hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(a, b);
    }
}
