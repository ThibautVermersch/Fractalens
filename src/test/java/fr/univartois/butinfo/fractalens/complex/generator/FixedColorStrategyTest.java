package fr.univartois.butinfo.fractalens.complex.generator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.awt.Color;

import fr.univartois.butinfo.fractalens.generator.ColorPalette;
import fr.univartois.butinfo.fractalens.generator.FixedColorStrategy;
import org.junit.jupiter.api.Test;

/**
 * Tests unitaires pour {@link FixedColorStrategy}.
 */
class FixedColorStrategyTest {

    @Test
    void testGetColorMaxIterationsReturnsBlack() {
        FixedColorStrategy strategy = new FixedColorStrategy(100);
        assertEquals(Color.BLACK, strategy.getColor(100));
        assertEquals(Color.BLACK, strategy.getColor(150));
    }

    @Test
    void testGetColorWithRainbowPalette() {
        FixedColorStrategy strategy = new FixedColorStrategy(100, ColorPalette.RAINBOW);

        // Début de l'arc-en-ciel
        assertEquals(new Color(200, 0, 0), strategy.getColor(0));

        // Milieu de l'arc-en-ciel
        assertEquals(new Color(0, 180, 0), strategy.getColor(50));

        // Fin de l'arc-en-ciel (avant divergence)
        assertEquals(new Color(0, 0, 200), strategy.getColor(90));
    }

    @Test
    void testGetColorBoundaryValuesRainbow() {
        FixedColorStrategy strategy = new FixedColorStrategy(10, ColorPalette.RAINBOW);

        assertEquals(new Color(200, 0, 0), strategy.getColor(0));
        assertEquals(new Color(0, 0, 200), strategy.getColor(9));
        assertEquals(Color.BLACK, strategy.getColor(10));
    }

    @Test
    void testDefaultConstructorUsesPalette() {
        FixedColorStrategy strategy = new FixedColorStrategy(100);
        // Vérifie que le constructeur par défaut renvoie bien la couleur de sa palette par défaut (CYBER)
        Color color = strategy.getColor(0);
        assertNotNull(color);
        assertEquals(ColorPalette.CYBER.getColor(0, 100), color);
    }

    @Test
    void testGetColorWithCustomColors() {
        Color[] custom = new Color[] { Color.RED, Color.GREEN, Color.BLUE };
        FixedColorStrategy strategy = new FixedColorStrategy(30, custom);

        // Index 0 : rouge (0 à 9)
        assertEquals(Color.RED, strategy.getColor(0));
        assertEquals(Color.RED, strategy.getColor(5));

        // Index 1 : vert (10 à 19)
        assertEquals(Color.GREEN, strategy.getColor(15));

        // Index 2 : bleu (20 à 29)
        assertEquals(Color.BLUE, strategy.getColor(25));
        assertEquals(Color.BLUE, strategy.getColor(29));

        // >= maxIterations : noir
        assertEquals(Color.BLACK, strategy.getColor(30));
    }

    @Test
    void testCustomColorsEmptyFallback() {
        FixedColorStrategy strategy = new FixedColorStrategy(50, new Color[0]);
        assertEquals(Color.BLACK, strategy.getColor(10));
    }
}
