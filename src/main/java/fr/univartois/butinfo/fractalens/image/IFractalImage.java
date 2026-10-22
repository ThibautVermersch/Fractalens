/*
 * FractaLens - A Fractal Generator Library Developed in Lens.
 * Copyright (c) 2022-2026 Romain Wallon - Université d'Artois.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE
 * USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package fr.univartois.butinfo.fractalens.image;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * The IFractalImage interface defines how to handle the image on which the fractal is
 * drawn.
 *
 * @author Romain Wallon
 *
 * @version 0.1.0
 */
public interface IFractalImage {

    /**
     * Gives the height of this image, measured in pixels.
     *
     * @return The height of this image.
     */
    int getHeight();

    /**
     * Gives the width of this image, measured in pixels.
     *
     * @return The witdh of this image.
     */
    int getWidth();

    /**
     * Updates the color of the pixel at the given position in this image.
     *
     * @param row The row of the pixel to update.
     * @param column The column of the pixel to update.
     * @param color The new color for the pixel.
     */
    void setColor(int row, int column, Color color);

    /**
     * Saves this image in a file.
     *
     * @param path The path of the file in which to save this image.
     *
     * @throws IOException If an I/O error occurs when writing the file.
     */
    void saveAs(String path) throws IOException;

    /**
     * Converts this image to a BufferedImage.
     * @return The BufferedImage representation of this image.
     */
    default BufferedImage asBufferedImage(){
        return null;
    }
}
