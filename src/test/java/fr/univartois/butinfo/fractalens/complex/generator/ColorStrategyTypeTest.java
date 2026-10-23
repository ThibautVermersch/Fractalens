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

    /**
     * test all strategies creation not null
     * @param strategyType  the strategy type
     */
    @ParameterizedTest
    @EnumSource(ColorStrategyType.class)
    void testAllStrategiesCreationNotNull(ColorStrategyType strategyType) {
        IColorStrategy strategy = strategyType.createStrategy(100);
        assertNotNull(strategy, "The created strategy must not be null for" + strategyType);
        assertNotNull(strategy.getColor(50), "The returned color must not be null");
    }

    /**
     * test getFactory
     */
    @Test
    void testGetFactory() {
        // Teste la méthode getFactory()
        Function<Integer, IColorStrategy> factory = ColorStrategyType.RAINBOW.getFactory();
        assertNotNull(factory, "The manufacturing process must not be null.");

        IColorStrategy strategy = factory.apply(100);
        assertNotNull(strategy, "The strategy produced by apply() must not be null");
        assertEquals(new Color(200, 0, 0), strategy.getColor(0));
    }

    /**
     * test createInverted
     */
    @Test
    void testCreateInvertedWithPalette() {
        IColorStrategy invertedStrategy = ColorStrategyType.createInverted(100, ColorPalette.RAINBOW);
        assertNotNull(invertedStrategy);

        Color expectedInverted = new Color(55, 255, 255);
        assertEquals(expectedInverted, invertedStrategy.getColor(0));
    }

    /**
     * test createInverted
     */
    @Test
    void testCreateInvertedWithStrategy() {
        IColorStrategy baseStrategy = ColorStrategyType.CYBER.createStrategy(100);
        IColorStrategy invertedStrategy = ColorStrategyType.createInverted(baseStrategy);
        assertNotNull(invertedStrategy);
    }

    /**
     * test createMasked
     */
    @Test
    void testCreateMasked() {
        IColorStrategy maskedStrategy = ColorStrategyType.createMasked(100, ColorPalette.RAINBOW, 1.0, 0.0, 0.0);
        assertNotNull(maskedStrategy);
    }
}
