package com.pavelkalvoda.misc.terragen.images;

import com.jme3.texture.Image;

public interface ImageWriter {
    void saveImage(Image img, String path);
    void saveHeightmapToImage(float[] map, int size, String path);
}
