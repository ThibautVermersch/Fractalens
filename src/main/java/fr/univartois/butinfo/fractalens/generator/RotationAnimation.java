package fr.univartois.butinfo.fractalens.generator;

import fr.univartois.butinfo.fractalens.complex.Complex;
import fr.univartois.butinfo.fractalens.complex.IComplex;
import fr.univartois.butinfo.fractalens.image.IFractalImage;

import javax.imageio.*;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * @author Pispisa Matheo
 */
public class RotationAnimation {

    /**
     * Private constructor to hide the implicit public one
     */
    private RotationAnimation() {

    }

    /**
     * Rotates a complex number around the origin, i.e., multiplies it by cos(θ) + i sin(θ).
     * @param c The complex number to rotate.
     * @param theta The angle of the rotation, in radians.
     * @return The rotated complex number.
     */
    public static IComplex rotate(IComplex c, double theta) {
        Objects.requireNonNull(c,"'c' cannot be null");
        return c.multiply(new Complex(Math.cos(theta), Math.sin(theta)));
    }

    /**
     * Generates a list of frames for a given complex number.
     * @param c The complex number to rotate.
     * @param nbFrames The number of frames to generate.
     * @param generatorFactory The factory function to create the generators.
     * @return The list of frames.
     */
    public static List<BufferedImage> generateFrames(IComplex c, int nbFrames,
            Function<IComplex, IFractalGenerator> generatorFactory) {
        Objects.requireNonNull(generatorFactory, "The generator factory cannot be null");
        if (nbFrames <= 0) {
            throw new IllegalArgumentException("The number of frames must be strictly positive: " + nbFrames);
        }

        double step = 2 * Math.PI / nbFrames;
        List<BufferedImage> frames = new ArrayList<>(nbFrames);
        for (int i = 0; i < nbFrames; i++) {
            IFractalImage image = generatorFactory.apply(rotate(c, i * step)).generateImage();
            BufferedImage frame = image.asBufferedImage();
            if (frame == null) {
                throw new IllegalArgumentException("The generators must draw on an image convertible to a BufferedImage");
            }
            frames.add(frame);
        }
        return frames;
    }

    /**
     * Saves the frames as a GIF file.
     * @param frames The list of frames to save.
     * @param path The path where to save the GIF file.
     * @param delay The delay between frames, in milliseconds.
     */
    public static void safeAsGif(List<BufferedImage> frames, String path, int delay) throws IOException {
        if (frames == null || frames.isEmpty()) {
            throw new IllegalArgumentException("Frames list cannot be empty");
        }
        // Create a GIF writer
        ImageWriter writer = ImageIO.getImageWritersByFormatName("gif").next();

        try (ImageOutputStream output = ImageIO.createImageOutputStream(new File(path))) {
            writer.setOutput(output);
            writer.prepareWriteSequence(null);

            // Write each frame
            for (int i = 0; i < frames.size(); i++) {
                BufferedImage frame = frames.get(i);
                ImageWriteParam param = writer.getDefaultWriteParam();
                IIOMetadata metadata = writer.getDefaultImageMetadata(ImageTypeSpecifier.createFromRenderedImage(frame), param);
                configureMetadata(metadata, delay, i == 0);
                writer.writeToSequence(new IIOImage(frame, null, metadata), param);
            }
            writer.endWriteSequence();
        } finally {
            writer.dispose();
        }
    }

    /**
     * Configure the metadata of the GIF file.
     * @param metadata The metadata of the GIF file.
     * @param delay The delay between frames, in milliseconds.
     * @param first Whether this is the first frame.
     * @throws IOException If an I/O error occurs.
     */
    private static void configureMetadata(IIOMetadata metadata, int delay, boolean first) throws IOException {
        String format = metadata.getNativeMetadataFormatName();
        IIOMetadataNode root = (IIOMetadataNode) metadata.getAsTree(format);

        // Set the delay time and loop count.
        IIOMetadataNode control = getOrCreateNode(root, "GraphicControlExtension");
        control.setAttribute("disposalMethod", "none");
        control.setAttribute("userInputFlag", "FALSE");
        control.setAttribute("transparentColorFlag", "FALSE");
        control.setAttribute("delayTime", Integer.toString(delay / 10));
        control.setAttribute("transparentColorIndex", "0");

        // Transparency index is 0 because we don't use transparency.'
        if (first) {
            IIOMetadataNode extension = new IIOMetadataNode("ApplicationExtension");
            extension.setAttribute("applicationID", "NETSCAPE");
            extension.setAttribute("authenticationCode", "2.0");
            // Loop indefinitely.
            extension.setUserObject(new byte[] { 1, 0, 0 });
            getOrCreateNode(root, "ApplicationExtensions").appendChild(extension);
        }

        metadata.setFromTree(format, root);
    }

    /**
     * Get or create a node with the given name.
     * @param root The root node.
     * @param name The name of the node.
     * @return The node.
     */
    private static IIOMetadataNode getOrCreateNode(IIOMetadataNode root, String name) {
        // Check if the node already exists.
        for (int i = 0; i < root.getLength(); i++) {
            if (root.item(i).getNodeName().equalsIgnoreCase(name)) {
                return (IIOMetadataNode) root.item(i);
            }
        }

        // Create a new node if it doesn't exist.'
        IIOMetadataNode node = new IIOMetadataNode(name);
        root.appendChild(node);
        return node;
    }

}

