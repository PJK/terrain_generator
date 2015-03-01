package com.pavelkalvoda.misc.terragen;

import com.pavelkalvoda.misc.terragen.terrain.SimpleSimplexNoiseTerrain;
import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.CartoonEdgeFilter;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.queue.RenderQueue.Bucket;
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
        settings.setResolution(640, 480);
        //settings.setFullscreen(true);
        settings.setFrequency(50);
        Main app = new Main();
        app.setSettings(settings);
        app.setShowSettings(false);
        app.start();
    }

    @Override
     public void simpleInitApp() {
                //create processor
//         SimpleWaterProcessor waterProcessor;
//         Spatial waterPlane;
//        waterProcessor = new SimpleWaterProcessor(assetManager);
//        waterProcessor.setReflectionScene(rootNode);
//        waterProcessor.setWaveSpeed(0.05f);
//        waterProcessor.setWaterTransparency(.5f);
//        viewPort.addProcessor(waterProcessor);
//
//        //create water quad
//        waterPlane = waterProcessor.createWaterGeometry(128, -128);
//        waterPlane.setQueueBucket(Bucket.Transparent);
//        waterPlane.setMaterial(waterProcessor.getMaterial());
//        waterPlane.setLocalScale(128);
//        waterPlane.setLocalTranslation(0, 20, 0);
//        rootNode.attachChild(waterPlane);
        
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
        
        Material mat_terrain = new Material(assetManager, "Common/MatDefs/Terrain/Terrain.j3md");
        //mat_terrain.setBoolean("useTriPlanarMapping", false);
        Texture alpha = new Texture2D();
        alpha.setImage( generateImage(2048, 2048));
        
//        mat_terrain.setTexture("Alpha", assetManager.loadTexture(
//            "Textures/alphamap.png"));
   
       mat_terrain.setTexture("Alpha", alpha);
   
         Texture grass = assetManager.loadTexture(
            "Textures/Grass.jpg");
    grass.setWrap(WrapMode.Repeat);
    mat_terrain.setTexture("Tex1", grass);
    mat_terrain.setFloat("Tex1Scale", 32f);
    
    terrain = new TerrainGrid("terrain", 33, 129, new DynamicTileQuadLoader());

        this.terrain.setMaterial(mat_terrain);
        this.terrain.setLocalTranslation(0, 0, 0);
        this.terrain.setLocalScale(2f, 1f, 2f);
        
        //terrain = 
        rootNode.attachChild(terrain);
        
         TerrainLodControl control = new TerrainGridLodControl(this.terrain, this.getCamera());
        control.setLodCalculator(new DistanceLodCalculator(33, 2.7f)); // patch size, and a multiplier
        this.terrain.addControl(control);

        rootNode.attachChild(SkyFactory.createSky(assetManager, "Textures/BrightSky.dds", false));
    }
    public static Image generateImage(int width, int height) {
Image testImage = new Image(Format.RGBA8, width, height, BufferUtils.createByteBuffer(4 *width * height));
        
        ImageRaster io = ImageRaster.create(testImage);
ByteBuffer bb = ByteBuffer.allocateDirect(width * height * 4);

for (int i = 0; i < width; i++)
    for (int j = 0; j < height; j++)
           io.setPixel(i, j, ColorRGBA.Red);
          
return testImage;

}


    private TerrainQuad terrain;
    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
