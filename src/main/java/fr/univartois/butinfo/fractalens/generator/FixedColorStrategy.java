package fr.univartois.butinfo.fractalens.generator;

import java.awt.*;

public class FixedColorStrategy implements IColorStrategy{

    private int maxIterations;

    public FixedColorStrategy(int maxIterations) {
        this.maxIterations = maxIterations;
    }

    private Color[] rainbow = {
            new Color(200, 0, 0),
            new Color(200, 100, 0),
            new Color(200, 150, 0),
            new Color(200, 200, 0),
            new Color(100, 200, 0),
            new Color(0, 180, 0),
            new Color(0, 180, 180),
            new Color(0, 140, 200),
            new Color(0, 80, 200),
            new Color(0, 0, 200)
    };

    @Override
    public Color getColor(int iterations) {
        if (iterations >= maxIterations) {
            return Color.BLACK;
        }
        return rainbow[(int)((iterations/(float)maxIterations)*10)];
    }
}
