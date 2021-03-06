package com.pavelkalvoda.misc.terragen.images;

import com.jme3.math.ColorRGBA;
import com.jme3.system.JmeSystem;
import com.jme3.texture.Image;
import com.jme3.texture.image.ImageRaster;
import com.jme3.util.BufferUtils;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageTool implements ImageWriter {
    protected String basePath;

    public ImageTool(String basePath) {
        this.basePath = basePath;
    }
    
    public void saveImage(Image img, String path) {
        try {
            // retrieve image
            JmeSystem.writeImageFile(new FileOutputStream(basePath + "/" + path, false), "png", img.getData(0), img.getWidth(), img.getHeight()); // This is fugly
        } catch (IOException e) {}
    }
     
    public void saveHeightmapToImage(float[] map, int size, String path) {
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
