/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pavelkalvoda.misc.smoothvox.mapping;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;

/**
 *
 * @author pjk
 */
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
