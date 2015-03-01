/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pavelkalvoda.misc.terragen;

/**
 *
 * @author pjk
 */
public class GridDisplacer {
    private int chunkSize;
    private Vector2i chunkLocation;

    public GridDisplacer(int chunkSize, Vector2i chunkLocation) {
        this.chunkSize = chunkSize;
        this.chunkLocation = chunkLocation;
//        if (chunkLocation.x > 0)
//            chunkLocation.x--;
//        if (chunkLocation.y > 0)
//            chunkLocation.y--;
    }
    
    public int displaceX(int coord) {
        return displaceWith(coord, chunkLocation.x);
    }
    
    public int displaceY(int coord) {
        return displaceWith(coord, chunkLocation.y);
    }
    
    protected int displaceWith(int coord, int globalOffset) {
        return globalOffset * chunkSize + coord;
    }
}
