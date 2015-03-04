package com.pavelkalvoda.misc.terragen.mapping;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.terrain.heightmap.HeightMap;
import com.jme3.texture.Texture;

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
        baseMaterial.setFloat("slopeTileFactor", 32);
        baseMaterial.setFloat("terrainSize", 513);
        
        Texture sand = assetManager.loadTexture("Textures/Sand.jpg");
        sand.setWrap(Texture.WrapMode.Repeat);
        baseMaterial.setTexture("region1ColorMap", sand);
        baseMaterial.setVector3("region1", new Vector3f(0, 70, 32f));
        
        Texture grass = assetManager.loadTexture("Textures/Grass.jpg");
        grass.setWrap(Texture.WrapMode.Repeat);
        baseMaterial.setTexture("region2ColorMap", grass);
        baseMaterial.setVector3("region2", new Vector3f(70, 170, 32f));
        
        
        Texture snow = assetManager.loadTexture("Textures/Snow.jpg");
        snow.setWrap(Texture.WrapMode.Repeat);
        baseMaterial.setTexture("region3ColorMap", snow);
        baseMaterial.setVector3("region3", new Vector3f(170, 500, 32f));
        
        Texture rock = assetManager.loadTexture("Textures/Rock.jpg");
        rock.setWrap(Texture.WrapMode.Repeat);
        baseMaterial.setTexture("slopeColorMap", rock);

        return baseMaterial;
    }
}
