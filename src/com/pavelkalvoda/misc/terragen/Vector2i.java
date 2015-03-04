package com.pavelkalvoda.misc.terragen;

import com.jme3.math.Vector3f;


public class Vector2i {
        public int x, y;

        public Vector2i(int x, int y) {
            this.x = x;
            this.y = y;
        }
        
        public Vector2i(Vector3f vec) {
            this((int)vec.x, (int)vec.z);
        }
        
        @Override
        public String toString() {
            return String.format("(%d, %d)", x, y);
        }
        
        public Vector2i add(Vector2i other) {
            return new Vector2i(x + other.x, y + other.y);
        }
}
