/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pavelkalvoda.misc.terragen.mapping;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.texture.Texture;
import java.util.Random;

/**
 *
 * @author pjk
 */
//http://www.curse.com/texture-packs/minecraft/coterie-craft-v5s-36-16x-mc-1-8-1-7-10-updated-1-1
public class TextureMapper implements HeightMapper {
    class Level {
        int startsAt;
        Texture texture;

        public Level(int startsAt, String texture) {
            this.startsAt = startsAt;
            this.texture =  assetManager.loadTexture(texture);
        }
        
    }
    
    Level levels[];
    AssetManager assetManager;
    int maxHeight;
    Random rng = new Random(0);
    
    
    public TextureMapper(AssetManager assetManager, int maxHeight) {
        this.assetManager = assetManager;
        this.maxHeight = maxHeight;
        levels = new Level[5];
        levels[0] = new Level(-1, "Textures/Sand.jpg");
        levels[1] = new Level(maxHeight / 3, "Textures/Grass.jpg");
        levels[2] = new Level(maxHeight / 2, "Textures/GrassyRock.jpg");
        levels[3] = new Level(maxHeight * 2 / 3, "Textures/Rock.jpg");
        levels[4] = new Level(maxHeight * 7 / 8, "Textures/Snow.jpg");
    }
    
    public Material getMaterial(int height) {
        height += rng.nextGaussian() * 5 + 5;
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/UnshadedNodes.j3md");
        for (int i = levels.length - 1; i >= 0; i--) {
            if (levels[i].startsAt < height) {
                mat.setTexture("ColorMap", levels[i].texture);
                break;
            }
        }

        return mat;
    }
    
}
