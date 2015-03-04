package com.pavelkalvoda.misc.terragen.mapping;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.terrain.heightmap.HeightMap;
import com.jme3.texture.Image;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture2D;
import com.pavelkalvoda.misc.terragen.Loader;
import com.pavelkalvoda.misc.terragen.terrain.MultipassSimplexNoiseTerrain;
import com.pavelkalvoda.misc.terragen.*;

// This is pure awesomeness - use a simplex noise heightmap to randomize
// texture splatting over our simplex noise heightmap made of many 
// simplex noise heightmaps
public class RandomizedHeightmapSplatter extends UniformHeightmapSplatter {
    public RandomizedHeightmapSplatter(AssetManager assetManager, Loader loader) {
        super(assetManager, loader);
    }
    
    protected MultipassSimplexNoiseTerrain heightMapper;
    
    @Override
    public Material splatForTile(HeightMap provider, Vector3f location) {
        Texture alpha = new Texture2D();
        heightMapper = new MultipassSimplexNoiseTerrain(
                provider.getSize(), 
                new GridDisplacer(
                    provider.getSize(), 
                    (new Vector2i(location)).add(new Vector2i(1, 0))),
                loader.getGenerator());
        
        Image img = generateImage(provider.getSize(), provider);
        alpha.setImage(img);

        loader.getImageWriter().saveImage(img, "out" + location + ".png");
        
        Material mat = buildBaseMaterial();
        mat.setTexture("Alpha", alpha);
        return mat;
    }
    
    // Hackety hack catch me if you can
    @Override
    protected float getModifier(int x, int y) {
        return heightMapper.getScaledHeightAtPoint(x, y) / 1.3f - 150;
    }
}
