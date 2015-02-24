/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pavelkalvoda.misc.smoothvox.terrain;

/**
 *
 * @author pjk
 */
public interface TerrainProvider {
    int getXLoBound();
    int getXHiBound();
    int getYLoBound();
    int getYHiBound();
    int  height(int x, int y);
    int getHeightBound();
}
