/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pavelkalvoda.misc.terragen.images;

import com.jme3.texture.Image;

// Used when image writing feature is disabled
public class DummyImageTool implements ImageWriter {
    public void saveImage(Image img, String path) {}
    public void saveHeightmapToImage(float[] map, int size, String path) {}
}
