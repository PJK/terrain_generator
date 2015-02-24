/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pavelkalvoda.misc.smoothvox.terrain;

/**
 *
 * @author pjk
 */
/*
 * OpenSimplex Noise in Java.
 * by Kurt Spencer
 * 
 * v1.1 (October 5, 2014)
 * - Added 2D and 4D implementations.
 * - Proper gradient sets for all dimensions, from a
 *   dimensionally-generalizable scheme with an actual
 *   rhyme and reason behind it.
 * - Removed default permutation array in favor of
 *   default seed.
 * - Changed seed-based constructor to be independent
 *   of any particular randomization library, so results
 *   will be the same when ported to other languages.
 */
 

public class OpenSimplexNoiseTerrain implements TerrainProvider {
    int x, y;
    OpenSimplexNoise noise = new OpenSimplexNoise();

    public OpenSimplexNoiseTerrain(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public int getXLoBound() { return 0; }
    public int getXHiBound() { return x; }
    public int getYLoBound() { return 0; }
    public int getYHiBound() { return y; }
    final int maxHeight = 128;
    
    public int getHeightBound() {
      return maxHeight;  
    }
    public int  height(int x, int y) {
        return (int)Math.floor((1 + noise.eval(x / 64.0, y / 64.0)) * maxHeight / 2);
    }
}
