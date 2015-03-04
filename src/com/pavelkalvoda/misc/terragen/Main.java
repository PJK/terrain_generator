package com.pavelkalvoda.misc.terragen;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.*;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import com.jme3.terrain.geomipmap.*;
import com.jme3.terrain.geomipmap.lodcalc.DistanceLodCalculator;
import com.jme3.util.SkyFactory;

import com.pavelkalvoda.misc.terragen.terrain.loading.DynamicTileQuadLoader;


public class Main extends SimpleApplication {
    Config cfg;
    FilterPostProcessor fpp;
    Loader loader;
    
    public static void main(String[] args) {
        Main app = new Main();
        try {
            app.cfg = new Config(args);
        } catch (Config.HelpRunException e) {
            return;
        }
        
        app.initConfig();
        app.start();
    }
    
    private void initConfig() {   
        AppSettings settings = new AppSettings(true);
        settings.setFrameRate(30);
        settings.setFrequency(50);
        settings.setResolution(cfg.resolution.x, cfg.resolution.y);
        settings.setFullscreen(cfg.fullscreen);
        setSettings(settings);
        setShowSettings(false);
    }
    
    private void initFilters() {
        BloomFilter bloom = new BloomFilter();
        bloom.setExposurePower(60);
        bloom.setBloomIntensity(.8f);
        fpp.addFilter(bloom);
        
        LightScatteringFilter lsf = new LightScatteringFilter(lightDir.mult(30));
        lsf.setLightDensity(.7f);
        fpp.addFilter(lsf);
        
        DepthOfFieldFilter dof = new DepthOfFieldFilter();
        dof.setBlurScale(.8f);
        dof.setFocusDistance(20);
        dof.setFocusRange(150);
        fpp.addFilter(dof);
    }
    
    private void initSkyline() {
        rootNode.attachChild(SkyFactory.createSky(assetManager, "Textures/BrightSky.dds", false));
    }
    
    private void initLODControl() {
        TerrainLodControl control = new TerrainGridLodControl(terrain, getCamera());
        control.setLodCalculator(new DistanceLodCalculator(cfg.patch, 2.7f));
        terrain.addControl(control);
    }

    private void addOriginMarker() {
        Geometry origin = new Geometry("OriginAnchor", new Box(.2f, 256, .2f));
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/UnshadedNodes.j3md");
        mat.setColor("Color", new ColorRGBA(1,0,0,0.5f));
       
        origin.setMaterial(mat);
        rootNode.attachChild(origin);
    }
    
    private Vector3f lightDir = new Vector3f(2f, 10f, 6f);
    private TerrainQuad terrain;
    private WaterProvider waterProvider;
    
    @Override
    public void simpleInitApp() {
        fpp = new FilterPostProcessor(assetManager);
        initFilters();
        initSkyline();

        loader = new Loader(cfg, assetManager);
        
        terrain = new TerrainGrid("terrain", cfg.patch, cfg.tile, new DynamicTileQuadLoader(loader));
        rootNode.attachChild(terrain);
        initLODControl();
        
        if (cfg.getWaterEnabled()) {
            waterProvider = new WaterProvider(rootNode, lightDir, assetManager);
            fpp.addFilter(waterProvider.getFilter());
        }


        viewPort.addProcessor(fpp);

        addOriginMarker();
        
        flyCam.setMoveSpeed(100);
        cam.setLocation(new Vector3f(0, 200, 0));
    }

    @Override
    public void simpleUpdate(float diff) {
        super.simpleUpdate(diff);
        if (cfg.getWaterEnabled())
            waterProvider.updateState(diff);
    }
}
