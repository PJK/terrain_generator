/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pavelkalvoda.misc.smoothvox.terrain;

/**
 *
 * @author pjk
 */
public class FlatTerrain implements TerrainProvider {
    int x, y;

    public FlatTerrain(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getXLoBound() {
        return 0;
    }
    
    public int getXHiBound() {
        return x;
    }
    
    public int getYLoBound() {
        return 0;
    }
    public int getYHiBound() {
        return y;
    }
    
    public int height(int x, int y) {
        return 10;
    }
    
    final int maxHeight = 20;
    
    public int getHeightBound() {
      return maxHeight;  
    }
}
