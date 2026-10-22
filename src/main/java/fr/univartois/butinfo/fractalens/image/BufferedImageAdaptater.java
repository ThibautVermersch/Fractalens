package fr.univartois.butinfo.fractalens.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BufferedImageAdaptater implements IFractalImage{

    /**
     * The image to adapt
     */
    private final BufferedImage image;

    /**
     * Constructor of the adaptater
     * @param image The image to adapt
     */
    public BufferedImageAdaptater(BufferedImage image) {
        this.image = image;
    }

    public BufferedImageAdaptater(int width, int height) {
        this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    /**
     * Returns the height of the adapted image
     * @return The height of the adapted image
     */
    @Override
    public int getHeight() {
        return this.image.getHeight();
    }

    /**
     * Returns the width of the adapted image
     * @return The width of the adapted image
     */
    @Override
    public int getWidth() {
        return this.image.getWidth();
    }

    /**
     * Returns the color of a pixel in the adapted image
     * @param row The row of the pixel to update.
     * @param column The column of the pixel to update.
     * @param color The new color for the pixel.
     */
    @Override
    public void setColor(int row, int column, Color color) {
        this.image.setRGB(column, row, color.getRGB());
    }

    /**
     * Saves this image in a file
     * @param path The path of the file in which to save this image.
     *
     * @throws IOException If an I/O error occurs when writing the file.
     */
    @Override
    public void saveAs(String path) throws IOException {
        File file = new File(path);
        String format = "png";
        int lastDotIndex = path.lastIndexOf('.');
        if (lastDotIndex > 0 && lastDotIndex < path.length() - 1) {
            format = path.substring(lastDotIndex + 1);
        }

        boolean success = ImageIO.write(this.image, format, file);
        if (!success) {
            throw new IOException("Aucun écrivain d'image trouvé pour le format : " + format);
        }
    }

    @Override
    public BufferedImage asBufferedImage() {

        return this.image;
    }

    /**
     * Returns the underlying BufferedImage
     * @return The underlying BufferedImage
     */
    public BufferedImage getBufferedImage() {
        return this.image;
    }

}
