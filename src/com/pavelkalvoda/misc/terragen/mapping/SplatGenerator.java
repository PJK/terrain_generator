/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pavelkalvoda.misc.terragen.mapping;

import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.terrain.heightmap.HeightMap;

/**
 *
 * @author pjk
 */
public interface SplatGenerator {
    Material splatForTile(HeightMap provider, Vector3f location);
}
