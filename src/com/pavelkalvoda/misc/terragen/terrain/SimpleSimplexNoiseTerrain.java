package com.pavelkalvoda.misc.terragen.terrain;

import com.jme3.terrain.heightmap.HeightMap;
import com.pavelkalvoda.misc.terragen.GridDisplacer;
import java.util.logging.Logger;

public class SimpleSimplexNoiseTerrain implements HeightMap {
    private static final Logger logger = Logger.getLogger(SimpleSimplexNoiseTerrain.class.getName());
    int size;
    SimpleSimplexNoise noisegen;
    GridDisplacer displacer;
    float map[];

    public SimpleSimplexNoiseTerrain(int size, GridDisplacer displacer, SimpleSimplexNoise noisegen) {
        this.noisegen = noisegen;
        this.size = size;
        this.displacer = displacer;
    }

    
    private final double featureQuant = 512;
    
    public float height(float x, float y) {
        return (float)(noisegen.noise(x / featureQuant, y / featureQuant) + 1) * 64 + 32;
    }
    
    
    public float[] getHeightMap() {
        load();
        return map;
    }

    public float[] getScaledHeightMap() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public float getInterpolatedHeight(float x, float z) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public float getScaledHeightAtPoint(int x, int z) {
        load();
        return map[z * size + x];
    }

    public int getSize() {
       return size;
    }

    public float getTrueHeightAtPoint(int x, int z) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean load() {
        if (map == null) {
            map = new float[size * size];
            for (int x = 0; x < size; x++)
                for (int y = 0; y < size; y++)
                    map[y * size + x] = height(displacer.displaceX(x), displacer.displaceY(y));
            return true;
        } else {
            return false;
        }
    }

    public void setHeightAtPoint(float height, int x, int z) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setHeightScale(float scale) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setMagnificationFilter(float filter) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setSize(int size) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void unloadHeightMap() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
