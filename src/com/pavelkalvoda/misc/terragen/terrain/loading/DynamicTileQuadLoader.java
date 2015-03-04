package com.pavelkalvoda.misc.terragen.terrain.loading;

import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.math.Vector3f;
import com.jme3.terrain.geomipmap.*;
import com.jme3.terrain.heightmap.HeightMap;
import com.pavelkalvoda.misc.terragen.*;
import java.io.IOException;
import java.util.logging.Logger;

public class DynamicTileQuadLoader implements TerrainGridTileLoader {

    private static final Logger logger = Logger.getLogger(TerrainGridTileLoader.class.getName());
    private int patchSize;
    private int quadSize;
    Loader loader;

    public DynamicTileQuadLoader(Loader loader) {
        this.loader = loader;
    }

    public TerrainQuad getTerrainQuadAt(Vector3f location) {
        HeightMap terrain = loader.buildHeightMap(quadSize,
                                                new GridDisplacer(quadSize - 1, new Vector2i(location)),
                                                loader.getGenerator());
        
        TerrainQuad quad = new TerrainQuad(
                                "my terrain",
                                patchSize,
                                quadSize,
                                terrain.getHeightMap()
                            );
        
        loader.getImageWriter().saveHeightmapToImage(terrain.getHeightMap(), quadSize, "out_terrain" + location + ".png");
        
        quad.setMaterial(loader.getSplatter().splatForTile(terrain, location));
        
        return quad;
    }

    public void setPatchSize(int patchSize) {
        this.patchSize = patchSize;
    }

    public void setQuadSize(int quadSize) {
        this.quadSize = quadSize;
    }

    public void write(JmeExporter ex) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void read(JmeImporter im) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
