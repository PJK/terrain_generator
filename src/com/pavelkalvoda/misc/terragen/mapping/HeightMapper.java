/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pavelkalvoda.misc.terragen.mapping;

import com.jme3.material.Material;

/**
 *
 * @author pjk
 */
public interface HeightMapper {
    Material getMaterial(int height);
}
