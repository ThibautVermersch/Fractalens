package fr.univartois.butinfo.fractalens.complex.generator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.univartois.butinfo.fractalens.generator.*;
import org.junit.jupiter.api.Test;

import fr.univartois.butinfo.fractalens.complex.Complex;
import fr.univartois.butinfo.fractalens.complex.ComplexPlane;
import fr.univartois.butinfo.fractalens.complex.IComplexPlane;
import fr.univartois.butinfo.fractalens.complex.sequence.JuliaSequence;
import fr.univartois.butinfo.fractalens.image.BufferedImageAdaptater;
import fr.univartois.butinfo.fractalens.image.IFractalImage;

/**
 * Tests unitaires pour {@link AnimatedFractalGenerator}.
 *
 * @author Thibaut Vermersch
 * @version 0.1.0
 */
class AnimatedFractalGeneratorTest {

    private IFractalGenerator createDummyGenerator(int width, int height, int maxIterations) {
        IComplexPlane plane = new ComplexPlane(height, width);
        BufferedImageAdaptater image = new BufferedImageAdaptater(width, height);
        IColorStrategy strategy = ColorStrategyType.GRAYSCALE.createStrategy(maxIterations);

        return new FractalGenerator(
                (z0, max) -> new JuliaSequence(z0, new Complex(-0.4, 0.6), max),
                image,
                plane,
                maxIterations,
                strategy,
                "dummy.png"
        );
    }

    @Test
    void testSaveImageCreatesGifFile() {
        List<IFractalGenerator> generators = new ArrayList<>();
        int width = 50;
        int height = 50;
        int maxIterations = 20;

        for (int i = 0; i < 3; i++) {
            generators.add(createDummyGenerator(width, height, maxIterations));
        }

        String testOutputPath = "test_animation.gif";
        File file = new File(testOutputPath);
        if (file.exists()) {
            file.delete();
        }

        AnimatedFractalGenerator animatedGenerator = new AnimatedFractalGenerator(generators, testOutputPath, 100);

        assertDoesNotThrow(animatedGenerator::saveImage, "La sauvegarde du GIF ne doit pas lever d'exception.");
        assertTrue(file.exists(), "Le fichier GIF doit avoir été créé sur le disque.");
        assertTrue(file.length() > 0, "Le fichier GIF ne doit pas être vide.");

        file.delete();
    }

    @Test
    void testGenerateImageReturnsFirstImage() {
        List<IFractalGenerator> generators = List.of(
                createDummyGenerator(20, 20, 10),
                createDummyGenerator(20, 20, 10)
        );

        AnimatedFractalGenerator animatedGenerator = new AnimatedFractalGenerator(generators, "dummy.gif", 100);
        IFractalImage first = animatedGenerator.generateImage();

        assertNotNull(first, "generateImage doit retourner la première image générée.");
        assertEquals(20, first.getWidth());
        assertEquals(20, first.getHeight());
    }

    @Test
    void testComputePixelColorDelegation() {
        List<IFractalGenerator> generators = List.of(createDummyGenerator(30, 30, 15));
        AnimatedFractalGenerator animatedGenerator = new AnimatedFractalGenerator(generators, "dummy.gif", 50);

        Color color = animatedGenerator.computePixelColor(0, 0);
        assertNotNull(color);

        // Cas liste vide
        AnimatedFractalGenerator emptyGenerator = new AnimatedFractalGenerator(Collections.emptyList(), "empty.gif", 50);
        assertEquals(Color.BLACK, emptyGenerator.computePixelColor(0, 0));
    }

    @Test
    void testGetGenerators() {
        List<IFractalGenerator> generators = List.of(createDummyGenerator(10, 10, 5));
        AnimatedFractalGenerator animatedGenerator = new AnimatedFractalGenerator(generators, "dummy.gif", 50);

        assertEquals(1, animatedGenerator.getGenerators().size());
        List<IFractalGenerator> unmodifiableGenerators = animatedGenerator.getGenerators();
        IFractalGenerator extraGenerator = generators.get(0);
        assertThrows(UnsupportedOperationException.class, () -> unmodifiableGenerators.add(extraGenerator));
    }

    @Test
    void testConstructorInvalidArguments() {
        List<IFractalGenerator> list = List.of(createDummyGenerator(10, 10, 5));

        assertThrows(NullPointerException.class, () -> new AnimatedFractalGenerator(null, "out.gif", 100));
        assertThrows(NullPointerException.class, () -> new AnimatedFractalGenerator(list, null, 100));
        assertThrows(IllegalArgumentException.class, () -> new AnimatedFractalGenerator(list, "out.gif", 0));
        assertThrows(IllegalArgumentException.class, () -> new AnimatedFractalGenerator(list, "out.gif", -10));
    }

    @Test
    void testSaveImageEmptyListThrowsException() {
        AnimatedFractalGenerator emptyGenerator = new AnimatedFractalGenerator(Collections.emptyList(), "empty.gif", 50);
        assertThrows(IllegalStateException.class, emptyGenerator::saveImage);
    }
}
