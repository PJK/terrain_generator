package com.pavelkalvoda.misc.terragen;

import com.pavelkalvoda.misc.terragen.terrain.SimpleSimplexNoiseTerrain;
import com.jme3.app.SimpleApplication;
import com.jme3.audio.AudioNode;
import com.jme3.audio.LowPassFilter;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.BloomFilter;
import com.jme3.post.filters.CartoonEdgeFilter;
import com.jme3.post.filters.DepthOfFieldFilter;
import com.jme3.post.filters.LightScatteringFilter;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import com.jme3.terrain.geomipmap.TerrainGrid;
import com.jme3.terrain.geomipmap.TerrainGridLodControl;
import com.jme3.terrain.geomipmap.TerrainLodControl;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.terrain.geomipmap.lodcalc.DistanceLodCalculator;
import com.jme3.texture.Image;
import com.jme3.texture.Image.Format;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;
import com.jme3.texture.Texture2D;
import com.jme3.texture.image.ImageRaster;
import com.jme3.util.BufferUtils;
import com.jme3.util.SkyFactory;
import com.jme3.water.SimpleWaterProcessor;
import com.jme3.water.WaterFilter;
import com.pavelkalvoda.misc.terragen.mapping.SimpleHeightmapSplatter;
import java.awt.Color;

import com.pavelkalvoda.misc.terragen.terrain.loading.DynamicTileQuadLoader;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import javax.imageio.ImageIO;


/**
 * test
 * @author normenhansen
 */
public class Main extends SimpleApplication {
    public static void main(String[] args) {
        AppSettings settings = new AppSettings(true);
        settings.setFrameRate(30);
        settings.setResolution(1280, 720);
        //settings.setFullscreen(true);
        settings.setFrequency(50);
        Main app = new Main();
        app.setSettings(settings);
        app.setShowSettings(false);
        app.start();
    }

    private Vector3f lightDir = new Vector3f(-4.9236743f, -1.27054665f, 5.896916f);
    private WaterFilter water;
    Material matRock;
        //This part is to emulate tides, slightly varrying the height of the water plane
    private float time = 0.0f;
    private float waterHeight = 0f;
    private float initialWaterHeight = 90f;//0.8f;
    private boolean uw = false;
    TerrainQuad terrain;
    
    
    @Override
     public void simpleInitApp() {
                

    terrain = new TerrainGrid("terrain", 513, 2049, new DynamicTileQuadLoader(new SimpleHeightmapSplatter(assetManager)));

        //terrain = 
        rootNode.attachChild(terrain);
        
         TerrainLodControl control = new TerrainGridLodControl(terrain, getCamera());
        control.setLodCalculator(new DistanceLodCalculator(513, 2.7f)); // patch size, and a multiplier
        terrain.addControl(control);

        rootNode.attachChild(SkyFactory.createSky(assetManager, "Textures/BrightSky.dds", false));
        
        
       water = new WaterFilter(rootNode, lightDir);

        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);

        fpp.addFilter(water);
//        BloomFilter bloom = new BloomFilter();
//        //bloom.getE
//        bloom.setExposurePower(55);
//        bloom.setBloomIntensity(1.0f);
//        fpp.addFilter(bloom);
//        LightScatteringFilter lsf = new LightScatteringFilter(lightDir.mult(-300));
//        lsf.setLightDensity(1.0f);
//        fpp.addFilter(lsf);
//        DepthOfFieldFilter dof = new DepthOfFieldFilter();
//        dof.setFocusDistance(0);
//        dof.setFocusRange(100);
//        fpp.addFilter(dof);
//        

        //   fpp.addFilter(new TranslucentBucketFilter());
        //       

        // fpp.setNumSamples(4);


        water.setWaveScale(0.003f);
        water.setMaxAmplitude(2f);
        water.setFoamExistence(new Vector3f(1f, 4, 0.5f));
        water.setFoamTexture((Texture2D) assetManager.loadTexture("Common/MatDefs/Water/Textures/foam2.jpg"));
        //water.setNormalScale(0.5f);

        //water.setRefractionConstant(0.25f);
        water.setRefractionStrength(0.2f);
        //water.setFoamHardness(0.6f);

        water.setWaterHeight(initialWaterHeight);
        water.setWaveScale(0.003f);
        water.setMaxAmplitude(2f);
        water.setFoamExistence(new Vector3f(1f, 4, 0.5f));
        water.setFoamTexture((Texture2D) assetManager.loadTexture("Common/MatDefs/Water/Textures/foam2.jpg"));
        //water.setNormalScale(0.5f);

        //water.setRefractionConstant(0.25f);
        water.setRefractionStrength(0.2f);
        //water.setFoamHardness(0.6f);

        water.setWaterHeight(initialWaterHeight);
        uw = cam.getLocation().y < waterHeight;

      
        //  
        viewPort.addProcessor(fpp);

        
        flyCam.setMoveSpeed(100);
        cam.setLocation(new Vector3f(0, 128, 0));
        Geometry origin = new Geometry("OriginAnchor", new Box(.2f, 256, .2f));

        Material mat = new Material(assetManager, "Common/MatDefs/Misc/UnshadedNodes.j3md");
        mat.setColor("Color", new ColorRGBA(1,0,0,0.5f));
        
       
        origin.setMaterial(mat);
        rootNode.attachChild(origin);
//        
//        TerrainProvider terrain = new SimpleSimplexNoiseTerrain(128, 128, 0);
//        (new TerrainRenderer(new TextureMapper(assetManager, terrain.getHeightBound()), terrain, rootNode)).render();
        // (new TerrainRenderer(new DummyMapper(assetManager), new OpenSimplexNoiseTerrain(256, 256), rootNode)).render();

//
//
//        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
//        fpp.addFilter(new CartoonEdgeFilter());
//        viewPort.addProcessor(fpp);

    }



    @Override
    public void simpleUpdate(float tpf) {
                super.simpleUpdate(tpf);
        //     box.updateGeometricState();
        time += tpf;
        waterHeight = (float) Math.cos(((time * 0.6f) % FastMath.TWO_PI)) * 1.5f;
        water.setWaterHeight(initialWaterHeight + waterHeight);
        if (water.isUnderWater() && !uw) {
            uw = true;
        }
        if (!water.isUnderWater() && uw) {
            uw = false;

        }
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
