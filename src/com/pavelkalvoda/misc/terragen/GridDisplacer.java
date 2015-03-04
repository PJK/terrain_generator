package com.pavelkalvoda.misc.terragen;

public class GridDisplacer {
    private int chunkSize;
    private Vector2i chunkLocation;

    public GridDisplacer(int chunkSize, Vector2i chunkLocation) {
        this.chunkSize = chunkSize;
        this.chunkLocation = chunkLocation;
    }
    
    public float displaceX(float coord) {
        return displaceWith(coord, chunkLocation.x);
    }
    
    public float displaceY(float coord) {
        return displaceWith(coord, chunkLocation.y);
    }
    
    protected float displaceWith(float coord, float globalOffset) {
        return globalOffset * chunkSize + coord;
    }
}
