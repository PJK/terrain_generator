package com.pavelkalvoda.misc.terragen;

import com.jme3.asset.AssetManager;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.texture.Texture2D;
import com.jme3.water.WaterFilter;

public class WaterProvider {

    protected Node target;
    protected Vector3f lightDir;
    protected WaterFilter water;
    protected AssetManager assetManager;
    
    private float time = 0.0f;
    private float waterHeight = 0f;
    private float initialWaterHeight = 100f;
    
    public WaterProvider(Node target, Vector3f lightDir, AssetManager assetManager) {
        this.target = target;
        this.lightDir = lightDir;
        this.assetManager = assetManager;

        water = new WaterFilter(target, lightDir);
        water.setWaveScale(0.003f);
        water.setMaxAmplitude(2f);
        water.setFoamExistence(new Vector3f(1f, 4, 0.5f));
        water.setFoamTexture((Texture2D) assetManager.loadTexture("Common/MatDefs/Water/Textures/foam2.jpg"));
        water.setRefractionStrength(0.2f);
        water.setWaterHeight(initialWaterHeight);
    }
    
    public WaterFilter getFilter() {
        return water;
    }
    
    public void updateState(float deltaT) {
        time += deltaT;
        waterHeight = (float) Math.cos(((time * 0.6f) % FastMath.TWO_PI)) * 4f;
        water.setWaterHeight(initialWaterHeight + waterHeight);
    }
}
