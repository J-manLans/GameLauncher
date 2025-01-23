package com.dt181g.laboration_3.view.common;

import java.io.IOException;

import java.awt.image.BufferedImage;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.dt181g.laboration_3.supportChange.AppConfigLab3;
import com.dt181g.laboration_3.supportChange.DebugLogger;

/**
 * A singleton that holds methods for managing various images across the
 * game launcher and its games. This enum is supposed to be used by the view
 * layer in the MVC pattern.
 *
 * @author Joel Lansgren
 */
public enum ImageManager {
    INSTANCE;

    /**
     * Loads and scales an image from a given path to fit as a game icon.
     * Optimized for quadratic images to maintain aspect ratio.
     *
     * @param path the file path to the image
     * @return an ImageIcon containing the scaled image, or null if an error occurs
     */
    public ImageIcon loadIcon(final String path) {
        try {
            // Load the image from the specified path as an input stream.
            // This is necessary to ensure the image can be included in the JAR file
            // and accessed properly when the application is packaged.
            final BufferedImage originalImage = ImageIO.read((getClass().getResourceAsStream(path)));

            // Create a down-scaled version of the image, optimized for quadratic images.
            final Image scaledImage = originalImage.getScaledInstance(
                AppConfigLab3.NUM_200,
                AppConfigLab3.NUM_200,
                Image.SCALE_SMOOTH
            );

            return new ImageIcon(scaledImage);
        } catch (final IOException e) {
            DebugLogger.INSTANCE.logWarning(e + "\nSomething went wrong while loading the picture to the game icons");
            return null;
        }
    }
}
