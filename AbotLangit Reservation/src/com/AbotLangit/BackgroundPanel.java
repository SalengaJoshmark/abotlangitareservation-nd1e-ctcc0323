package com.AbotLangit;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/*
 * The class that is responsible for loading and showing the jpg from the resource folder
 * Allowing the Welcome, Reservation and Availabilty classes to achieved its custom background based on the given source path of the jpg
 * You need to somethings though the properties and java build path in order for the resource folder to work
 */
public class BackgroundPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private BufferedImage backgroundImage;

    public BackgroundPanel(String imagePath) {
        try {
            // Load the image
            InputStream inputStream = getClass().getResourceAsStream(imagePath);
            if (inputStream != null) {
                backgroundImage = ImageIO.read(inputStream);
            } else {
                throw new IllegalArgumentException("Image file not found: " + imagePath);
            }
        } catch (IOException | IllegalArgumentException e) {
            // Handle th exceptions
            e.printStackTrace();
            // Optionally, set a default background or handle the error in another way if theres some issue at the jpg's 
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the background image
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return backgroundImage == null ? super.getPreferredSize() : new Dimension(backgroundImage.getWidth(), backgroundImage.getHeight());
    }

    // Additional methods and panel functionality can be potentially added here
}