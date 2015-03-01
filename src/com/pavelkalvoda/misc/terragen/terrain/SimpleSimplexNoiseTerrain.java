/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pavelkalvoda.misc.terragen.terrain;

import com.jme3.terrain.geomipmap.TerrainGridTileLoader;
import com.jme3.terrain.heightmap.HeightMap;
import com.pavelkalvoda.misc.terragen.GridDisplacer;
import com.pavelkalvoda.misc.terragen.Vector2i;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pjk
 */
public class SimpleSimplexNoiseTerrain implements HeightMap {
    private static final Logger logger = Logger.getLogger(SimpleSimplexNoiseTerrain.class.getName());
    int size;
    SimpleSimplexNoise noisegen;
    GridDisplacer displacer;

    public SimpleSimplexNoiseTerrain(int size, GridDisplacer displacer, SimpleSimplexNoise noisegen) {
        this.noisegen = noisegen;
        this.size = size;
        this.displacer = displacer;
    }

    
    private final double featureQuant = 50;
    public int  height(int x, int y) {
        return (int)((noisegen.noise(x / featureQuant, y / featureQuant) + 1) * getHeightBound() / 2);
    }
    public int getHeightBound() {
        return 90;
    }
    
    public float[] getHeightMap() {
        float res[] = new float[size * size];
        for (int x = 0; x < size; x++)
            for (int y = 0; y < size; y++) {
                //logger.log(Level.WARNING, "Generating sample at ({0}, {1})", new Object[] { displacer.displaceX(x), displacer.displaceY(y) });
               // System.out.println(displacer.displaceX(x));
                res[y * size + x] = height(displacer.displaceX(x), displacer.displaceY(y));
                //res[x * size + y] = height(x, y);
            }
        return res;
    }

    public float[] getScaledHeightMap() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public float getInterpolatedHeight(float x, float z) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public float getScaledHeightAtPoint(int x, int z) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getSize() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public float getTrueHeightAtPoint(int x, int z) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean load() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
