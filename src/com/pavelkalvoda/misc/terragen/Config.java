/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pavelkalvoda.misc.terragen;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author pjk
 */
public class Config {
    public static enum Terrain {
        Multipass, Simple
    }
    
    public static enum Mapping {
        Simple, Uniform, Randomized
    }
    
    protected boolean water = true;
    protected long seed = 0;
    protected Terrain terrain = Terrain.Multipass;
    protected Mapping mapping = Mapping.Simple;
    protected int patch = 513, tile = 1025;
    
    public class HelpRunException extends RuntimeException {}
    
    public Config(String[] args) {
        List<String> opts = Arrays.asList(args);
        while (!opts.isEmpty()) {
            switch(opts.get(0)) {
                case "-h":
                    printUsage(System.out);
                    throw new HelpRunException();
            }
        }
    }
    
    public static void printUsage(PrintStream out) {
        out.println("Usage:\t-h --[no-]water --seed <seed>\n\t--terrain multipass|simple --mapping simple|uniform|randomized\n\t--size <patch> <tile> --write-bitmaps <dir>\n");
        out.println("All of the options are optional.\n");
        
        out.println("\t--[no-]water\t\tShow water\n");
        
        out.println("\t--seed\t\t\tPRNG seed\n");
        
        out.println("\t--terrain\t\tmultipass - simplex noise terrain with 5 octaves (richer)");
        out.println("\t\t\t\tsimple - single layer of simplex noise (faster)\n");
        
        out.println("\t--mapping\t\tsimple\t\tuniform smooth height-based map with 3 texture and slope\n\t\t\t\t\t\tdetection (fastest)");
        out.println("\t\t\t\tunifrom\t\tuniform smooth height-based map with <TODO> textures,\n\t\t\t\t\t\toptionally outputs PNGs");
        out.println("\t\t\t\trandomized\trandomized smooth height-based map with <TODO> textures,\n\t\t\t\t\t\toptionally outputs PNGs (fanciest)\n");
        
        out.println("\t--size\t\t\tTerrain is loaded by tiles (controls viewing distance & performance)\n\t\t\t\twhich are stored in Q trees by patches. Both must be N^2 + 1\n");
    
        out.println("\t--write-bitmaps\t\tOutput PNG heightmaps & texture splatting alpha maps to\n\t\t\t\tthe specified directory");
    }
    
    public boolean waterEnabled() {
        return water;
    }
    
}
