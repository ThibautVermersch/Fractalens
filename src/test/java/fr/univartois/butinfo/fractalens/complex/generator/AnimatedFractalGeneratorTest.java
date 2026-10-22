package fr.univartois.butinfo.fractalens.complex.generator;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import fr.univartois.butinfo.fractalens.generator.*;
import org.junit.jupiter.api.Test;

import fr.univartois.butinfo.fractalens.complex.Complex;
import fr.univartois.butinfo.fractalens.complex.ComplexPlane;
import fr.univartois.butinfo.fractalens.complex.IComplexPlane;
import fr.univartois.butinfo.fractalens.complex.sequence.JuliaSequence;
import fr.univartois.butinfo.fractalens.image.BufferedImageAdaptater;


class AnimatedFractalGeneratorTest {

    /**
     * Tests that the saveImage method successfully generates and creates an animated GIF file.
     */
    @Test
    void testSaveImageCreatesGifFile() {

        List<IFractalGenerator> generators = new ArrayList<>();
        int width = 100;
        int height = 100;
        int maxIterations = 50;


        IColorStrategy palette = ColorStrategyType.GRAYSCALE.createStrategy(maxIterations);

        for (int i = 0; i < 3; i++) {
            IComplexPlane plane = new ComplexPlane(height, width);
            BufferedImageAdaptater image = new BufferedImageAdaptater(width, height);

            IFractalGenerator gen = new FractalGenerator(
                    (z0, max) -> new JuliaSequence(z0, new Complex(-0.4, 0.6), max),
                    image,
                    plane,
                    maxIterations,
                    palette,
                    "dummy_frame_" + i + ".png"
            );
            generators.add(gen);
        }


        String testOutputPath = "test_animation.gif";


        File file = new File(testOutputPath);
        if (file.exists()) {
            file.delete();
        }


        AnimatedFractalGenerator animatedGenerator = new AnimatedFractalGenerator(generators, testOutputPath, 100);


        assertDoesNotThrow(() -> animatedGenerator.saveImage(), "GIF generation should not throw an exception");


        assertTrue(file.exists(), "The animated GIF file must have been created on disk");
        assertTrue(file.length() > 0, "The GIF file must not be empty");


        file.delete();
    }
}