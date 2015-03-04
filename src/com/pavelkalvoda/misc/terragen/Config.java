package com.pavelkalvoda.misc.terragen;

import java.io.PrintStream;
import java.util.ArrayDeque;

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
    protected int patch = 513, tile = 2049;
    protected String bitmapsDir;
    protected boolean fullscreen = true;
    protected Vector2i resolution = new Vector2i(1920, 1200);
    
    // Signals errorneous configuration - we still want a clean shutdown
    public class HelpRunException extends RuntimeException {}
    
    public Config(String[] args) {
        ArrayDeque<String> opts = new ArrayDeque<>();
        
        for (String arg : args)
            opts.addLast(arg); //Thanks Java
        
        String last;
        while (!opts.isEmpty()) {
            switch(last = opts.pop()) {
                case "-h":
                    printUsage(System.out);
                    throw new HelpRunException();
                case "--no-water":
                    water = false;
                    break;
                case "--water":
                    water = true;
                    break;
                case "--seed":
                    seed = Long.parseLong(opts.pop());
                    break;
                case "--terrain":
                    switch(opts.pop()) {
                        case "multipass":
                            terrain = Terrain.Multipass;
                            break;
                        case "simple":
                            terrain = Terrain.Simple;
                            break;
                        default:
                            System.out.println("Unrecognized entry for --terrain");
                            printUsage(System.out);
                            throw new HelpRunException();
                    }
                    break;
                case "--mapping":
                    switch(opts.pop()) {
                        case "simple":
                            mapping = Mapping.Simple;
                            break;
                        case "uniform":
                            mapping = Mapping.Uniform;
                            break;
                        case "randomized":
                            mapping = Mapping.Randomized;
                            break;
                        default:
                            System.out.println("Unrecognized entry for --mapping");
                            printUsage(System.out);
                            throw new HelpRunException();
                    }
                    break;
                case "--size":
                    patch = Integer.parseInt(opts.pop());
                    tile = Integer.parseInt(opts.pop());
                    break;
                case "--write-bitmaps":
                    bitmapsDir = opts.pop();
                    break;
                case "--fullscreen":
                    fullscreen = true;
                    break;
                case "--resolution":
                    resolution = new Vector2i(Integer.parseInt(opts.pop()), Integer.parseInt(opts.pop()));
                    break;
                default:
                    System.out.println("Unrecognized option " + last);
                    printUsage(System.out);
                    throw new HelpRunException();
            }
        }
        
    }
    
    public void printUsage(PrintStream out) {
        // Heredoc is too cool for Java
        // So is built in CLI parser
        out.println("Usage:\tjava -jar path_to/terrain_generator.jar "
                + "-h "
                + "--[no-]water "
                + "--seed <seed>\n\t"
                + "--terrain multipass|simple "
                + "--mapping simple|uniform|randomized\n\t"
                + "--size <patch> <tile>"
                + "--write-bitmaps <dir>\n\t"
                + "--fullscreen "
                + "--resolution <w> <h>\n");
        
        out.println("All of the options are optional.\n");
        
        out.println("\t--[no-]water\t\tShow water");
        out.println("\t\t\t\tDefault: " + water + "\n");
        
        out.println("\t--seed\t\t\tPRNG seed");
        out.println("\t\t\t\tDefault: " + seed + "\n");
        
        out.println("\t--terrain\t\tmultipass - simplex noise terrain with 5 octaves (richer)");
        out.println("\t\t\t\tsimple - single layer of simplex noise (faster)");
        out.println("\t\t\t\tDefault: " + terrain + "\n");
        
        out.println("\t--mapping\t\tsimple\t\tuniform smooth height-based map with 3 texture and slope\n\t\t\t\t\t\tdetection (fastest)");
        out.println("\t\t\t\tunifrom\t\tuniform smooth height-based map with 3 textures,\n\t\t\t\t\t\toptionally outputs PNGs");
        out.println("\t\t\t\trandomized\trandomized smooth height-based map with 3 + 1 textures,\n\t\t\t\t\t\toptionally outputs PNGs (fanciest)");
        out.println("\t\t\t\tDefault: " + mapping + "\n");
         
        out.println("\t--size\t\t\tTerrain is loaded by tiles (controls viewing distance & performance)"
                + "\n\t\t\t\twhich are stored in Q trees by patches. Both must be N^2 + 1");
        out.println("\t\t\t\tDefault: " + patch + ", " + tile + "\n");
        
        out.println("\t--write-bitmaps\t\tOutput PNG heightmaps & texture splatting alpha maps to\n\t\t\t\tthe specified directory");
        out.println("\t\t\t\tDefault: " + bitmapsDir + "\n");
        
        out.println("\t--fullscreen\t\tUse fullscreen mode (your resolution must match system supported options)");
        out.println("\t\t\t\tDefault: " + fullscreen + "\n");
        
        out.println("\t--resolution\t\tResolution in px (availability is system-dependent)");
        out.println("\t\t\t\tDefault: " + resolution + "\n");
    }
    
    public boolean getWaterEnabled() {
        return water;
    }
    
    public long getSeed() {
        return seed;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public Mapping getMapping() {
        return mapping;
    }

    public int getPatch() {
        return patch;
    }

    public int getTile() {
        return tile;
    }

    public String getBitmapsDir() {
        return bitmapsDir;
    }
}
