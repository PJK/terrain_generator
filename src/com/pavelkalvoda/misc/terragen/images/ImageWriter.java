/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pavelkalvoda.misc.terragen.images;

import com.jme3.math.ColorRGBA;
import com.jme3.system.JmeSystem;
import com.jme3.texture.Image;
import com.jme3.texture.image.ImageRaster;
import com.jme3.util.BufferUtils;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author pjk
 */
public interface ImageWriter {
    void saveImage(Image img, String path);
    void saveHeightmapToImage(float[] map, int size, String path);
}
