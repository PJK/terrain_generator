/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pavelkalvoda.misc.terragen;

import com.jme3.math.ColorRGBA;
import com.jme3.system.JmeSystem;
import com.jme3.texture.Image;
import com.jme3.texture.image.ImageRaster;
import com.jme3.util.BufferUtils;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author pjk
 */
public class ImageTools {
    public static void saveImage(Image img, String path) {
        try {
            // retrieve image
            JmeSystem.writeImageFile(new FileOutputStream(path, false), "png", img.getData(0), img.getWidth(), img.getHeight());
        } catch (IOException e) {}
    }
     
    public static void saveHeightmapToImage(float[] map, int size, String path) {
        Image testImage = new Image(Image.Format.RGBA8, size, size, BufferUtils.createByteBuffer(4 * size * size));
        ImageRaster io = ImageRaster.create(testImage);
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {
                float color = map[j * size + i] / 256;
                ColorRGBA c = new ColorRGBA(color, color, color, 1f);
                io.setPixel(j, i, c);
            }
        saveImage(testImage, path);
    }
    
}
