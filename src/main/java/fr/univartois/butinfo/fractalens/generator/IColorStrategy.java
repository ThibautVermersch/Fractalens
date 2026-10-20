package fr.univartois.butinfo.fractalens.generator;

import java.awt.Color;

public interface IColorStrategy {
    // Prend un nombre d'itérations et retourne une Couleur
    Color getColor(int iterations);
}
