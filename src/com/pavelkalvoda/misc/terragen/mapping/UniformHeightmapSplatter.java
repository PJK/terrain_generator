/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pavelkalvoda.misc.terragen.mapping;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.terrain.heightmap.HeightMap;
import com.jme3.texture.Image;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture2D;
import com.jme3.texture.image.ImageRaster;
import com.jme3.util.BufferUtils;
import com.pavelkalvoda.misc.terragen.Loader;
import com.pavelkalvoda.misc.terragen.images.ImageTool;

/**
 *
 * @author pjk
 */
public class UniformHeightmapSplatter implements SplatGenerator {
    protected AssetManager assetManager;
    protected Loader loader;

    public UniformHeightmapSplatter(AssetManager assetManager, Loader loader) {
        this.assetManager = assetManager;
        this.loader = loader;
    }

    public Material splatForTile(HeightMap provider, Vector3f location) {
        Texture alpha = new Texture2D();
        alpha.setImage(generateImage(provider.getSize(), provider));
        
        loader.getImageWriter().saveImage(generateImage(provider.getSize(), provider), "out" + location + ".png");

        Material mat = new Material();
        //mat.setTexture("Alpha", alpha);
        return mat;

    }
        protected Image generateImage(int size, HeightMap provider) {
        Image testImage = new Image(Image.Format.RGBA8, size, size, BufferUtils.createByteBuffer(4 * size * size));
        ImageRaster io = ImageRaster.create(testImage);
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {
                ColorRGBA c = new ColorRGBA();
                //System.out.println(bellFunction(provider.getScaledHeightAtPoint(j, i), 30));
                c.r =  1;
                c.g =  0;
                c.b =  provider.getScaledHeightAtPoint(j, i) / 256;
                io.setPixel(i, size -1 -j, c);
            }
        return testImage;

    }
    
    // Bell shaped function similar to normal PDF
    // http://www.bindichen.co.uk/post/Fundamentals/bell-shaped-function.html
    protected static float bellFunction(float x, double c) {
        float inter = (float)(1 / (1 + Math.pow((x - c) / 90, 2 * .8)));
        if (inter == Float.NaN)
            return 0.5f;
        else
            return inter;
    }
}
