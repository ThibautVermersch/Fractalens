package fr.univartois.butinfo.fractalens.complex.generator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import fr.univartois.butinfo.fractalens.generator.GrayScaleStrategy;
import fr.univartois.butinfo.fractalens.generator.IColorStrategy;
import fr.univartois.butinfo.fractalens.generator.InvertedColorStrategy;
import org.junit.jupiter.api.Test;


class InvertedColorStrategyTest {


    @Test
    void testGetColorSubtractsEachComponentFrom255() {
        IColorStrategy strategy = new InvertedColorStrategy(i -> new Color(10, 100, 200));

        assertEquals(new Color(245, 155, 55), strategy.getColor(0));
    }


    @Test
    void testGetColorOnBlackAndWhite() {
        assertEquals(Color.WHITE,
                new InvertedColorStrategy(i -> Color.BLACK).getColor(0));
        assertEquals(Color.BLACK,
                new InvertedColorStrategy(i -> Color.WHITE).getColor(0));
    }


    @Test
    void testGetColorPassesIterationsToDecorated() {
        List<Integer> received = new ArrayList<>();
        IColorStrategy strategy = new InvertedColorStrategy(i -> {
            received.add(i);
            return Color.BLACK;
        });

        strategy.getColor(42);

        assertEquals(List.of(42), received);
    }

    @Test
    void testGetColorTwiceGivesOriginalColor() {
        IColorStrategy original = i -> new Color(10, 100, 200);
        IColorStrategy twice = new InvertedColorStrategy(
                new InvertedColorStrategy(original));

        assertEquals(original.getColor(0), twice.getColor(0));
    }


    @Test
    void testGetColorWithGrayScaleStrategy() {
        IColorStrategy strategy = new InvertedColorStrategy(new GrayScaleStrategy(50));

        assertEquals(Color.BLACK, strategy.getColor(0));
        assertEquals(Color.WHITE, strategy.getColor(50));
    }

}
