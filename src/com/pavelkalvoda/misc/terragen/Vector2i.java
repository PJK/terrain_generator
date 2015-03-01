/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pavelkalvoda.misc.terragen;

import com.jme3.math.Vector3f;

/**
 *
 * @author pjk
 */
public class Vector2i {
        public int x, y;

        public Vector2i(int x, int y) {
            this.x = x;
            this.y = y;
        }
        
        public Vector2i(Vector3f vec) {
            this((int)vec.x, (int)vec.z);
        }
}
