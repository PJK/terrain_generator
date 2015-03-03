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
public class SimpleHeightmapSplatter implements SplatGenerator {

    protected AssetManager assetManager;
    // We build the same one over and over again - cache 
    private static Material cachedMaterial;

    public SimpleHeightmapSplatter(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public Material splatForTile(HeightMap provider, Vector3f location) {
        if (cachedMaterial == null)
            return cachedMaterial = buildMaterial();
        else
            return cachedMaterial;
    }
    
    protected Material buildMaterial() {
          Material baseMaterial = new Material(assetManager, "Common/MatDefs/Terrain/HeightBasedTerrain.j3md");
        //Material baseMaterial = new Material(assetManager, "Common/MatDefs/Terrain/HeightBasedTerrain.j3md");
        //baseMaterial.setBoolean("useTriPlanarMapping", false);
        Texture grass = assetManager.loadTexture("Textures/Grass.jpg");
        grass.setWrap(Texture.WrapMode.Repeat);
        baseMaterial.setTexture("region1ColorMap", grass);
        baseMaterial.setVector3("region1", new Vector3f(15, 90, 32f));
        baseMaterial.setFloat("slopeTileFactor", 32);
        baseMaterial.setFloat("terrainSize", 513);
        //baseMaterial.setFloat("Tex1Scale", 32f);
        Texture grassy_rock = assetManager.loadTexture("Textures/Pond.jpg");
        grassy_rock.setWrap(Texture.WrapMode.Repeat);
        baseMaterial.setTexture("region3ColorMap", grassy_rock);
        baseMaterial.setVector3("region3", new Vector3f(0, 15, 32f));
        //baseMaterial.setTexture("Tex2", grassy_rock);
        //baseMaterial.setFloat("Tex2Scale", 32f);
        Texture rock = assetManager.loadTexture("Textures/Rock.jpg");
        rock.setWrap(Texture.WrapMode.Repeat);
        baseMaterial.setTexture("slopeColorMap", rock);
        //baseMaterial.setTexture("Tex3", rock);
        //baseMaterial.setFloat("Tex3Scale", 32f);
        return baseMaterial;
    }
}
