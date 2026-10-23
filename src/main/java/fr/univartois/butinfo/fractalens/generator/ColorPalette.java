package fr.univartois.butinfo.fractalens.generator;

import java.awt.Color;

public enum ColorPalette {
    RAINBOW(new Color[]{
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
    }),


    FIRE(new Color[]{
            new Color(177, 0, 0),
            new Color(202, 3, 3),
            new Color(230, 50, 0),
            new Color(253, 112, 0),
            new Color(233, 130, 14),
            new Color(213, 148, 28),
            new Color(226, 174, 18),
            new Color(241, 204, 23),
            new Color(255, 245, 162)
    }),

    ICE(new Color[]{
            new Color(0, 16, 48),
            new Color(0, 30, 80),
            new Color(0, 70, 140),
            new Color(0, 120, 200),
            new Color(0, 170, 240),
            new Color(100, 210, 255),
            new Color(180, 235, 255),
            new Color(217, 217, 241)
    }),
    SUNSET(new Color[]{
            new Color(40, 0, 60),
            new Color(100, 0, 100),
            new Color(180, 30, 90),
            new Color(240, 100, 50),
            new Color(255, 180, 50),
            new Color(255, 240, 150)
    }),

    FOREST(new Color[]{
            new Color(10, 30, 10),
            new Color(30, 70, 20),
            new Color(60, 120, 30),
            new Color(110, 160, 50),
            new Color(170, 210, 90),
            new Color(220, 245, 160)
    }),

    NEON(new Color[]{
            new Color(255, 0, 128),
            new Color(255, 0, 255),
            new Color(128, 0, 255),
            new Color(0, 128, 255),
            new Color(0, 255, 255),
            new Color(0, 255, 128)
    }),

    CYBER(new Color[]{
            new Color(10, 0, 20),
            new Color(30, 0, 50),
            new Color(90, 0, 120),
            new Color(180, 0, 120),
            new Color(255, 0, 85),
            new Color(255, 120, 0),
            new Color(255, 230, 0),
            new Color(0, 240, 255)
    });

    private final Color[] colors;

    ColorPalette(Color[] colors) {
        this.colors = colors;
    }

    public Color getColor(int iterations, int maxIterations) {
        if (iterations >= maxIterations) {
            return Color.BLACK;
        }
        int index = Math.min(colors.length - 1, (int) ((double) iterations / maxIterations * colors.length));
        return colors[index];
    }
}