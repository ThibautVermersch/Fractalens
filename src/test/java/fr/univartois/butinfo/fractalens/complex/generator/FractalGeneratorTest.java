/*
 * FractaLens - A Fractal Generator Library Developed in Lens.
 * Copyright (c) 2022-2026 Romain Wallon - Université d'Artois.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE
 * USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package fr.univartois.butinfo.fractalens.complex.generator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.Color;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiFunction;

import fr.univartois.butinfo.fractalens.generator.FractalGenerator;
import fr.univartois.butinfo.fractalens.generator.GrayScaleStrategy;
import fr.univartois.butinfo.fractalens.generator.IColorStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.univartois.butinfo.fractalens.complex.ComplexPlane;
import fr.univartois.butinfo.fractalens.complex.IComplex;
import fr.univartois.butinfo.fractalens.complex.IComplexPlane;
import fr.univartois.butinfo.fractalens.complex.sequence.IComplexSequence;
import fr.univartois.butinfo.fractalens.complex.sequence.JuliaSequence;
import fr.univartois.butinfo.fractalens.image.BufferedImageAdaptater;
import fr.univartois.butinfo.fractalens.image.IFractalImage;

/**
 * The FractalGeneratorTest tests the class {@link FractalGenerator}.
 * <p>
 * It relies on fake complex numbers, planes, sequences, images and color strategies,
 * so that the generator is tested independently of the actual implementations of the
 * interfaces it uses. A last test checks that it also works with the real classes.
 *
 * @version 0.1.0
 */
class FractalGeneratorTest {

    /**
     * The maximum number of iterations given to the generator.
     */
    private static final int MAX_ITERATIONS = 50;

    /**
     * The path of the output file given to the generator.
     */
    private static final String PATH = "fractal.png";

    /**
     * The image in which the generator draws (2 rows and 3 columns, so that rows and
     * columns cannot be mixed up).
     */
    private FakeImage image;

    /**
     * The first terms received by the function building the sequences.
     */
    private List<IComplex> receivedFirstTerms;

    /**
     * The maximum numbers of iterations received by the function building the
     * sequences.
     */
    private List<Integer> receivedMaxIterations;

    /**
     * The generator to test.
     */
    private FractalGenerator generator;

    /**
     * Creates the generator to test, with fake collaborators.
     * <p>
     * The pixel at (row, column) is mapped to the complex number {@code row * 10 +
     * column}, and the sequence built from this number has as many terms as its value.
     * Finally, a sequence of n terms is mapped to the color {@code rgb(n, 0, 0)}.
     */
    @BeforeEach
    void setUp() {
        image = new FakeImage(2, 3);
        receivedFirstTerms = new ArrayList<>();
        receivedMaxIterations = new ArrayList<>();

        IComplexPlane plane = (row, column) -> new FakeComplex(row * 10 + column);

        BiFunction<IComplex, Integer, IComplexSequence> sequenceFactory = (z0, max) -> {
            receivedFirstTerms.add(z0);
            receivedMaxIterations.add(max);
            return new FakeSequence((int) z0.getRealPart());
        };

        IColorStrategy strategy = iterations -> new Color(iterations, 0, 0);

        generator = new FractalGenerator(sequenceFactory, image, plane, MAX_ITERATIONS,
                strategy, PATH);
    }

    /**
     * Test method for {@link FractalGenerator#computePixelColor(int, int)}.
     * The color must depend on the number of iterations made on the sequence.
     */
    @Test
    void testComputePixelColorUsesNumberOfIterations() {
        assertEquals(new Color(0, 0, 0), generator.computePixelColor(0, 0));
        assertEquals(new Color(2, 0, 0), generator.computePixelColor(0, 2));
        assertEquals(new Color(10, 0, 0), generator.computePixelColor(1, 0));
        assertEquals(new Color(12, 0, 0), generator.computePixelColor(1, 2));
    }

    /**
     * Test method for {@link FractalGenerator#computePixelColor(int, int)}.
     * The first term of the sequence must be the complex number of the plane for the
     * given pixel (the row being given first).
     */
    @Test
    void testComputePixelColorUsesPlaneForFirstTerm() {
        generator.computePixelColor(1, 2);

        assertEquals(List.of(new FakeComplex(12)), receivedFirstTerms);
    }

    /**
     * Test method for {@link FractalGenerator#computePixelColor(int, int)}.
     * The maximum number of iterations must be given to the sequence factory.
     */
    @Test
    void testComputePixelColorPassesMaxIterations() {
        generator.computePixelColor(0, 1);

        assertEquals(List.of(MAX_ITERATIONS), receivedMaxIterations);
    }

    /**
     * Test method for {@link FractalGenerator#generateImage()}.
     * Every pixel must be colored exactly once, with its own color.
     */
    @Test
    void testGenerateImageColorsEveryPixel() {
        generator.generateImage();

        for (int row = 0; row < image.getHeight(); row++) {
            for (int column = 0; column < image.getWidth(); column++) {
                assertEquals(new Color(row * 10 + column, 0, 0), image.colors[row][column]);
            }
        }
        assertEquals(image.getHeight() * image.getWidth(), image.nbSetColor);
    }

    /**
     * Test method for {@link FractalGenerator#generateImage()}.
     */
    @Test
    void testGenerateImageReturnsTheImage() {
        assertSame(image, generator.generateImage());
    }

    /**
     * Test method for {@link FractalGenerator#saveImage()}.
     */
    @Test
    void testSaveImageUsesPath() {
        generator.saveImage();

        assertEquals(PATH, image.savedPath);
    }

    /**
     * Test method for {@link FractalGenerator#saveImage()} when saving fails.
     * The {@link IOException} must be reported as an {@link UncheckedIOException}.
     */
    @Test
    void testSaveImageWrapsIOException() {
        IOException failure = new IOException("disk full");
        image.failure = failure;

        UncheckedIOException thrown = assertThrows(UncheckedIOException.class,
                generator::saveImage);
        assertSame(failure, thrown.getCause());
    }

    /**
     * Test method for {@link FractalGenerator#generateImage()} with the real
     * implementations: the center of the plane belongs to the Julia set of {@code c = 0}
     * (the sequence never diverges), while the corner is far outside of it.
     */
    @Test
    void testGenerateImageWithRealImplementations() {
        BufferedImageAdaptater realImage = new BufferedImageAdaptater(8, 8);
        IComplex zero = new fr.univartois.butinfo.fractalens.complex.Complex(0, 0);

        FractalGenerator realGenerator = new FractalGenerator(
                (z0, max) -> new JuliaSequence(z0, zero, max), realImage,
                new ComplexPlane(8, 8), MAX_ITERATIONS,
                new GrayScaleStrategy(MAX_ITERATIONS), PATH);
        realGenerator.generateImage();

        // The center is the complex number 0: it never diverges.
        assertEquals(Color.BLACK.getRGB(), realImage.getBufferedImage().getRGB(4, 4));

        // The top-left corner is the complex number -4 + 4i: it diverges at once.
        assertEquals(Color.WHITE.getRGB(), realImage.getBufferedImage().getRGB(0, 0));
    }

    /**
     * A fake image, which remembers the colors set and the path of the output file.
     */
    private static final class FakeImage implements IFractalImage {

        /**
         * The height of this image.
         */
        private final int height;

        /**
         * The width of this image.
         */
        private final int width;

        /**
         * The colors that have been set, indexed by row and column.
         */
        private final Color[][] colors;

        /**
         * The number of calls to {@link #setColor(int, int, Color)}.
         */
        private int nbSetColor;

        /**
         * The path given to {@link #saveAs(String)}.
         */
        private String savedPath;

        /**
         * The exception to throw when saving, if any.
         */
        private IOException failure;

        /**
         * Creates a new FakeImage.
         *
         * @param height The height of the image.
         * @param width The width of the image.
         */
        FakeImage(int height, int width) {
            this.height = height;
            this.width = width;
            this.colors = new Color[height][width];
        }

        @Override
        public int getHeight() {
            return height;
        }

        @Override
        public int getWidth() {
            return width;
        }

        @Override
        public void setColor(int row, int column, Color color) {
            colors[row][column] = color;
            nbSetColor++;
        }

        @Override
        public void saveAs(String path) throws IOException {
            if (failure != null) {
                throw failure;
            }
            savedPath = path;
        }

    }

    /**
     * A fake sequence, which produces a fixed number of terms.
     */
    private static final class FakeSequence implements IComplexSequence {

        /**
         * The number of terms of this sequence.
         */
        private final int nbTerms;

        /**
         * Creates a new FakeSequence.
         *
         * @param nbTerms The number of terms of the sequence.
         */
        FakeSequence(int nbTerms) {
            this.nbTerms = nbTerms;
        }

        @Override
        public IComplex computeNext(IComplex previous) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Iterator<IComplex> iterator() {
            List<IComplex> terms = new ArrayList<>();
            for (int i = 0; i < nbTerms; i++) {
                terms.add(new FakeComplex(i));
            }
            return terms.iterator();
        }

    }

    /**
     * A fake complex number lying on the real axis, which only supports the operations
     * needed by the tests.
     *
     * @param value The real value of this number.
     */
    private record FakeComplex(double value) implements IComplex {

        @Override
        public double getRealPart() {
            return value;
        }

        @Override
        public double getImaginaryPart() {
            return 0;
        }

        @Override
        public double abs() {
            return Math.abs(value);
        }

        @Override
        public IComplex negate() {
            throw new UnsupportedOperationException();
        }

        @Override
        public IComplex conjugate() {
            throw new UnsupportedOperationException();
        }

        @Override
        public IComplex add(IComplex other) {
            throw new UnsupportedOperationException();
        }

        @Override
        public IComplex subtract(IComplex other) {
            throw new UnsupportedOperationException();
        }

        @Override
        public IComplex multiply(double factor) {
            throw new UnsupportedOperationException();
        }

        @Override
        public IComplex multiply(IComplex other) {
            throw new UnsupportedOperationException();
        }

        @Override
        public IComplex divide(IComplex other) {
            throw new UnsupportedOperationException();
        }

        @Override
        public IComplex pow(int exponent) {
            throw new UnsupportedOperationException();
        }

    }

}