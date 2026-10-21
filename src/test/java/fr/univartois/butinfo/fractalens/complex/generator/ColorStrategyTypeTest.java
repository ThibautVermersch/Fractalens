package fr.univartois.butinfo.fractalens.complex.generator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.awt.Color;
import java.util.function.Function;

import fr.univartois.butinfo.fractalens.generator.ColorPalette;
import fr.univartois.butinfo.fractalens.generator.ColorStrategyType;
import fr.univartois.butinfo.fractalens.generator.IColorStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class ColorStrategyTypeTest {

    @ParameterizedTest
    @EnumSource(ColorStrategyType.class)
    void testAllStrategiesCreationNotNull(ColorStrategyType strategyType) {
        // Vérifie que chaque constante de l'énumération crée bien une stratégie non nulle
        IColorStrategy strategy = strategyType.createStrategy(100);
        assertNotNull(strategy, "La stratégie créée ne doit pas être nulle pour " + strategyType);

        // Vérifie que la stratégie retourne bien une couleur valide au milieu
        assertNotNull(strategy.getColor(50), "La couleur renvoyée ne doit pas être nulle");
    }

    @Test
    void testGetFactory() {
        // Teste la méthode getFactory()
        Function<Integer, IColorStrategy> factory = ColorStrategyType.RAINBOW.getFactory();
        assertNotNull(factory, "La fabrique ne doit pas être nulle");

        IColorStrategy strategy = factory.apply(100);
        assertNotNull(strategy, "La stratégie produite par apply() ne doit pas être nulle");
        assertEquals(new Color(200, 0, 0), strategy.getColor(0));
    }

    @Test
    void testCreateInvertedWithPalette() {
        // Teste la méthode statique createInverted(maxIterations, palette)
        IColorStrategy invertedStrategy = ColorStrategyType.createInverted(100, ColorPalette.RAINBOW);
        assertNotNull(invertedStrategy);

        Color expectedInverted = new Color(55, 255, 255);
        assertEquals(expectedInverted, invertedStrategy.getColor(0));
    }

    @Test
    void testCreateInvertedWithStrategy() {
        // Teste la méthode statique createInverted(strategy)
        IColorStrategy baseStrategy = ColorStrategyType.CYBER.createStrategy(100);
        IColorStrategy invertedStrategy = ColorStrategyType.createInverted(baseStrategy);
        assertNotNull(invertedStrategy);
    }

    @Test
    void testCreateMasked() {
        // Teste la méthode statique createMasked()
        IColorStrategy maskedStrategy = ColorStrategyType.createMasked(100, ColorPalette.RAINBOW, 1.0, 0.0, 0.0);
        assertNotNull(maskedStrategy);
    }
}