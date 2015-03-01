/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pavelkalvoda.misc.terragen;

import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.pavelkalvoda.misc.terragen.mapping.HeightMapper;
import com.pavelkalvoda.misc.terragen.terrain.TerrainProvider;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author pjk
 */
public class TerrainRenderer {
    HeightMapper mapper;
    TerrainProvider provider;
    Node targetNode;
   
    public TerrainRenderer(HeightMapper mapper, TerrainProvider provider, Node targetNode) {
        this.mapper = mapper;
        this.provider = provider;
        this.targetNode = targetNode;
    }
    
    public void render() {
       BufferedImage image = new BufferedImage(provider.getXHiBound(), provider.getYHiBound(), BufferedImage.TYPE_INT_RGB);
       File outputfile = new File("saved.png");
        for (int i = provider.getXLoBound(); i < provider.getXHiBound(); i++)
            for (int j = provider.getYLoBound(); j < provider.getYHiBound(); j++) {
                int height;
                Geometry box_g = new Geometry("Box",  new Box(.5f, 2, .5f));
                box_g.setLocalTranslation(new Vector3f(i, height = provider.height(i, j), j));
                image.setRGB(i, j, height | (height << 8) | (height << 16));
                box_g.setMaterial(mapper.getMaterial(height));
                targetNode.attachChild(box_g);
            }
        try { ImageIO.write(image, "png", outputfile); }  catch (IOException e) {}

    }
}
