/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pavelkalvoda.misc.smoothvox.terrain;

/**
 *
 * @author pjk
 */
public class SimpleSimplexNoiseTerrain implements TerrainProvider {
    int x, y;
    SimpleSimplexNoise noisegen;

    public SimpleSimplexNoiseTerrain(int x, int y, long seed) {
        noisegen = new SimpleSimplexNoise(seed);
        this.x = x;
        this.y = y;
    }
    
    public int getXLoBound() { return 0; }
    public int getXHiBound() { return x;}
    public int getYLoBound()  { return 0; }
    public int getYHiBound() {return y; }
    
    private final double featureQuant = 130;
    public int  height(int x, int y) {
        return (int)((noisegen.noise(x / featureQuant, y / featureQuant) + 1) * getHeightBound() / 2);
    }
    public int getHeightBound() {
        return 90;
    }
}
