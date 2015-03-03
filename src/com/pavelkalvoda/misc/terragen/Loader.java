/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pavelkalvoda.misc.terragen;

import com.jme3.asset.AssetManager;
import com.jme3.terrain.heightmap.HeightMap;
import com.pavelkalvoda.misc.terragen.mapping.*;
import com.pavelkalvoda.misc.terragen.terrain.*;
/**
 *
 * @author pjk
 */
public class Loader {
    protected Config cfg;
    protected SplatGenerator splatter;
    protected AssetManager assetManager;
    protected SimpleSimplexNoise generator;

    public Loader(Config cfg, AssetManager assetManager) {
        this.cfg = cfg;
        this.assetManager = assetManager;
        this.generator = new SimpleSimplexNoise(cfg.seed);
        loadSplatter();
    }
    
    public HeightMap buildHeightMap(int size, GridDisplacer displacer, SimpleSimplexNoise noisegen) {
        switch (cfg.terrain) {
            case Simple:
                return new SimpleSimplexNoiseTerrain(size, displacer, noisegen);
            case Multipass:
                return new MultipassSimplexNoiseTerrain(size, displacer, noisegen);
            default:
                return null; // Java static analysis ballgag
        }
    }
    
    private void loadSplatter() {
        switch (cfg.mapping) {
            case Simple:
                splatter = new SimpleHeightmapSplatter(assetManager);
        }
    }
    
    public SplatGenerator getSplatter() {
        return splatter;
    }
    
    public SimpleSimplexNoise getGenerator() {
        return generator;
    }
}
