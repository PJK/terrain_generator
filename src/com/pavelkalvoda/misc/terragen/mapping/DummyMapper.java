package com.pavelkalvoda.misc.terragen.mapping;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;

public class DummyMapper implements HeightMapper {
    AssetManager assetManager;
    int maxHeight;
    
    public DummyMapper(AssetManager assetManager, int maxHeight) {
        this.assetManager = assetManager;
        this.maxHeight = maxHeight;
    }
    
    public Material getMaterial(int height) {
        Material mat = new Material(assetManager, 
            "Common/MatDefs/Misc/UnshadedNodes.j3md");
        mat.setTexture("ColorMap", assetManager.loadTexture("Textures/Grass.jpg"));
        float brightness = height / (float)maxHeight;
        //System.out.printf("%d %d %f\n", height, maxHeight, brightness);
        return mat;
    }
}
