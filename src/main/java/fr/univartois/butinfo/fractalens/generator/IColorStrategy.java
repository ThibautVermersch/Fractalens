package fr.univartois.butinfo.fractalens.generator;

import java.awt.Color;

public interface IColorStrategy {

    Color getColor(int iterations);
}