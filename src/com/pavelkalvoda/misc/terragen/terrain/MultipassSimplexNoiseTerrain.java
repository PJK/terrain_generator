package com.pavelkalvoda.misc.terragen.terrain;

import com.jme3.terrain.heightmap.HeightMap;
import com.pavelkalvoda.misc.terragen.GridDisplacer;
import java.util.logging.Logger;

/**
 *
 * @author pjk
 */
public class MultipassSimplexNoiseTerrain implements HeightMap {
    private static final Logger logger = Logger.getLogger(MultipassSimplexNoiseTerrain.class.getName());
    
    // Similar to http://libnoise.sourceforge.net/glossary/#octave
    class Octave {
        public float freq, amp;

        public Octave(float freq, float amp) {
            this.freq = freq;
            this.amp = amp;
        }
    }
    
    Octave[] octs;
    int size;
    SimpleSimplexNoise noisegen;
    GridDisplacer displacer;
    float map[];

    public MultipassSimplexNoiseTerrain(int size, GridDisplacer displacer, SimpleSimplexNoise noisegen) {
        this.noisegen = noisegen;
        this.size = size;
        this.displacer = displacer;
        // These are arbitrary, but pretty hard to balance. Modify with caution
        octs = new Octave[] { 
            new Octave(.05f, 10f),
            new Octave(.6f, 4f),
            new Octave(2, 1f),
            new Octave(6, .5f),
            new Octave(10, .2f),
                new Octave(20, .1f) 
        };
    }

    
    private final double featureQuant = 720;
    
    public float height(float x, float y) {
        return (float)(noisegen.noise(x / featureQuant, y / featureQuant) + .5) * 32;
    }
    
    
    public float[] getHeightMap() {
        load();
        return map;
    }

    public float[] getScaledHeightMap() {
        return getHeightMap();
    }

    public float getInterpolatedHeight(float x, float z) {
        return getScaledHeightAtPoint((int)x, (int)z);
    }

    public float getScaledHeightAtPoint(int x, int z) {
        load();
        return map[z * size + x];
    }

    public int getSize() {
       return size;
    }

    public float getTrueHeightAtPoint(int x, int z) {
        return getScaledHeightAtPoint(x, z);
    }

    public boolean load() {
        if (map == null) {
            map = new float[size * size];
            for (int x = 0; x < size; x++)
                for (int y = 0; y < size; y++) {
                    map[y * size + x] = 0f;
                    for (Octave oct : octs)
                        map[y * size + x] += oct.amp * height(
                                    oct.freq * displacer.displaceX(x), 
                                    oct.freq * displacer.displaceY(y)
                                );
                }
            return true;
        } else {
            return false;
        }
    }

    public void setHeightAtPoint(float height, int x, int z) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setHeightScale(float scale) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setMagnificationFilter(float filter) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setSize(int size) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void unloadHeightMap() {
        map = null;
    }
}
