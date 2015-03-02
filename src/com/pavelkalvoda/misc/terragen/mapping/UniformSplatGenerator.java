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
import com.pavelkalvoda.misc.terragen.ImageTools;

/**
 *
 * @author pjk
 */
public class UniformSplatGenerator implements SplatGenerator {

    AssetManager assetManager;

    public UniformSplatGenerator(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public Material splatForTile(HeightMap provider, Vector3f location) {
        Texture alpha = new Texture2D();
        alpha.setImage(generateImage(provider.getSize(), provider));
        ImageTools.saveImage(generateImage(provider.getSize(), provider), "out" + location + ".png");
        

        //        mat_terrain.setTexture("Alpha", assetManager.loadTexture(
        //            "Textures/alphamap.png"));
        Material mat = buildBaseMaterial();
        //mat.setTexture("Alpha", alpha);
        return mat;

    }

    protected Material buildBaseMaterial() {
        Material baseMaterial = new Material(assetManager, "Common/MatDefs/Terrain/HeightBasedTerrain.j3md");
        //Material baseMaterial = new Material(assetManager, "Common/MatDefs/Terrain/HeightBasedTerrain.j3md");
        //baseMaterial.setBoolean("useTriPlanarMapping", false);
        Texture grass = assetManager.loadTexture("Textures/Grass.jpg");
        grass.setWrap(Texture.WrapMode.Repeat);
        baseMaterial.setTexture("region1ColorMap", grass);
        baseMaterial.setVector3("region1", new Vector3f(0, 60, 32f));
        baseMaterial.setFloat("slopeTileFactor", 32);
        baseMaterial.setFloat("terrainSize", 513);
        //baseMaterial.setFloat("Tex1Scale", 32f);
        Texture grassy_rock = assetManager.loadTexture("Textures/Pond.jpg");
        grassy_rock.setWrap(Texture.WrapMode.Repeat);
        baseMaterial.setTexture("region3ColorMap", grassy_rock);
        baseMaterial.setVector3("region3", new Vector3f(-30, -10, 32f));
        //baseMaterial.setTexture("Tex2", grassy_rock);
        //baseMaterial.setFloat("Tex2Scale", 32f);
        Texture rock = assetManager.loadTexture("Textures/Rock.jpg");
        rock.setWrap(Texture.WrapMode.Repeat);
        baseMaterial.setTexture("slopeColorMap", rock);
        //baseMaterial.setTexture("Tex3", rock);
        //baseMaterial.setFloat("Tex3Scale", 32f);
        return baseMaterial;
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
