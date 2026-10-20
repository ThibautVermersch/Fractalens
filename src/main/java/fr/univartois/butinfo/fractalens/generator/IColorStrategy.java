package fr.univartois.butinfo.fractalens.generator;

import java.awt.Color; // ou l'équivalent selon ton projet

public interface IColorStrategy {
    // Prend un nombre d'itérations et retourne une Couleur
    Color getColor(int iterations);
}