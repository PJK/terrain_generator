package com.pavelkalvoda.misc.terragen.mapping;

import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.terrain.heightmap.HeightMap;

public interface SplatGenerator {
    Material splatForTile(HeightMap provider, Vector3f location);
}
