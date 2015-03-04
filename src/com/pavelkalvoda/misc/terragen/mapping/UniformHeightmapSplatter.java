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
import com.jme3.texture.Texture.WrapMode;
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

    protected Material buildBaseMaterial() {
        Material mat = new Material(assetManager, "Common/MatDefs/Terrain/Terrain.j3md");
        Texture grass = assetManager.loadTexture("Textures/Grass.jpg");
        grass.setWrap(WrapMode.Repeat);
        mat.setTexture("Tex1", grass);
        mat.setFloat("Tex1Scale", 32f);
        // load dirt texture
        Texture dirt = assetManager.loadTexture("Textures/Rock.jpg");
        dirt.setWrap(WrapMode.Repeat);
        mat.setTexture("Tex2", dirt);
        mat.setFloat("Tex2Scale", 32f);
        // load rock texture
        Texture rock = assetManager.loadTexture("Textures/Snow.jpg");
        rock.setWrap(WrapMode.Repeat);
        mat.setTexture("Tex3", rock);
        mat.setFloat("Tex3Scale", 32f);
        return mat;
    }

    public Material splatForTile(HeightMap provider, Vector3f location) {
        Texture alpha = new Texture2D();
        Image img = generateImage(provider.getSize(), provider);
        alpha.setImage(img);

        loader.getImageWriter().saveImage(img, "out" + location + ".png");
        
        Material mat = buildBaseMaterial();
        mat.setTexture("Alpha", alpha);
        return mat;
    }

    protected Image generateImage(int size, HeightMap provider) {
        Image testImage = new Image(Image.Format.RGBA8, size, size, BufferUtils.createByteBuffer(4 * size * size));
        ImageRaster io = ImageRaster.create(testImage);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                ColorRGBA c = new ColorRGBA();
                float height = provider.getScaledHeightAtPoint(i, size - 1 - j) + getModifier(i,  size - 1 - j);
                Vector3f tmp = new Vector3f(
                        bellFunction(height, 150, 100),
                        bellFunction(height, 240, 40),
                        bellFunction(height, 350, 100));
                tmp.normalize();
                c.r = tmp.x;
                c.g = tmp.y;
                c.b = tmp.z;
                io.setPixel(i, j, c);
            }
        }
        return testImage;

    }
    
    protected float getModifier(int x, int y) {
        return 0;
    }

    // Bell shaped function similar to normal PDF
    // http://www.bindichen.co.uk/post/Fundamentals/bell-shaped-function.html
    protected float bellFunction(float x, double c, double a) {
        return (float) (1 / (1 + Math.pow(Math.abs((x - c) / a), 2 * 3)));
    }
}
