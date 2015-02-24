/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pavelkalvoda.misc.smoothvox;

/**
 *
 * @author pjk
 */
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import com.pavelkalvoda.misc.smoothvox.terrain.OpenSimplexNoise;
 
public class OSTest
    {
    private static final int WIDTH = 512;
    private static final int HEIGHT = 512;
    private static final double FEATURE_SIZE = 24;

    public static void main(String[] args)
    throws IOException {
    OpenSimplexNoise noise = new OpenSimplexNoise();
    BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    for (int y = 0; y < HEIGHT; y++)
    {
    for (int x = 0; x < WIDTH; x++)
    {
    double value = noise.eval(x / FEATURE_SIZE, y / FEATURE_SIZE);
    int rgb = 0x010101 * (int)((value + 1) * 127.5);
    image.setRGB(x, y, rgb);
    }
    }
    ImageIO.write(image, "png", new File("noise.png"));
    }
} 
