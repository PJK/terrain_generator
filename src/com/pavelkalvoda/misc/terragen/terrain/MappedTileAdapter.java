/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pavelkalvoda.misc.terragen.terrain;

import com.jme3.terrain.heightmap.HeightMap;

/**
 *
 * @author pjk
 */
public class MappedTileAdapter implements HeightMap {
    int size;
    float map[];
    
    public MappedTileAdapter(/*context*/) {
        
    }

    public float[] getHeightMap() {
        return map;
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
        return size;
    }

    public float getTrueHeightAtPoint(int x, int z) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean load() {
        map = new float[size * size];
        for (int i = 0; i < size * size; i++)
            map[i] = 0f;
        return true;
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
        if (size <= 0)
            throw new Exception("Size cannot be <= 0");
        this.size = size;
    }

    public void unloadHeightMap() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
