/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pavelkalvoda.misc.terragen.terrain.loading;

import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.math.Vector3f;
import com.jme3.terrain.geomipmap.TerrainGridTileLoader;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.terrain.heightmap.HeightMap;
import com.pavelkalvoda.misc.terragen.GridDisplacer;
import com.pavelkalvoda.misc.terragen.ImageTools;
import com.pavelkalvoda.misc.terragen.Vector2i;
import com.pavelkalvoda.misc.terragen.mapping.SimpleHeightmapSplatter;
import com.pavelkalvoda.misc.terragen.mapping.SplatGenerator;
import com.pavelkalvoda.misc.terragen.terrain.MultipassSimplexNoiseTerrain;
import com.pavelkalvoda.misc.terragen.terrain.SimpleSimplexNoise;
import com.pavelkalvoda.misc.terragen.terrain.SimpleSimplexNoiseTerrain;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pjk
 */
public class DynamicTileQuadLoader implements TerrainGridTileLoader {

    private static final Logger logger = Logger.getLogger(TerrainGridTileLoader.class.getName());
    private int patchSize;
    private int quadSize;
    SimpleSimplexNoise generator = new SimpleSimplexNoise(0);
    SplatGenerator splatter;
    
    public DynamicTileQuadLoader(SplatGenerator splatter) {
        this.splatter = splatter;
    }

    public TerrainQuad getTerrainQuadAt(Vector3f location) {
        HeightMap terrain = new MultipassSimplexNoiseTerrain(
                                                quadSize,
                                                new GridDisplacer(quadSize - 1, new Vector2i(location)),
                                                generator);
        
        TerrainQuad quad = new TerrainQuad(
                                "my terrain",
                                patchSize,
                                quadSize,
                                terrain.getHeightMap()
                            );
        ImageTools.saveHeightmapToImage(terrain.getHeightMap(), quadSize, "out_terrain" + location + ".png");
        quad.setMaterial(splatter.splatForTile(terrain, location));
        return quad;
    }

    public void setPatchSize(int patchSize) {
        this.patchSize = patchSize;
    }

    public void setQuadSize(int quadSize) {
        this.quadSize = quadSize;
    }

    public void write(JmeExporter ex) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void read(JmeImporter im) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
