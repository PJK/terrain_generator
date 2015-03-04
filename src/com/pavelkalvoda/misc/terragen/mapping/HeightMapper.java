package com.pavelkalvoda.misc.terragen.mapping;

import com.jme3.material.Material;


public interface HeightMapper {
    Material getMaterial(int height);
}
