/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pavelkalvoda.misc.smoothvox.terrain;

import java.util.Random;

/**
 *
 * @author pjk
 */
// Based on http://staffwww.itn.liu.se/~stegu/simplexnoise/simplexnoise.pdf
// http://www.csee.umbc.edu/~olano/s2002c36/ch02.pdf
// http://mrl.nyu.edu/~perlin/doc/oscar.html
//  + simplified 
public class SimpleSimplexNoise {

    static class Gradient {

        public int x, y;

        public Gradient(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    private Random rng;
    // Random lookup table to easily access the same value relative to an intermediate
    // value & the seed - this is the sole source of randomness in the rest of the algorithm
    // The size is arbitrary, anything between 64 and 512 works nicely
    // Ideally, these would be permutations of 1..size, but it doesn't really matter and this
    // is fair bit cheaper
    private int randLookup[] = new int[128];

    private int randLookup(int index) {
        return randLookup[index & 127];
    }

    public SimpleSimplexNoise(long seed) {
        rng = new Random(seed);

        for (int i = 0; i < randLookup.length; i++) {
            randLookup[i] = rng.nextInt(randLookup.length);
        }

    }
    // Suitable combination gradients - bounding square vertices
    // This is arbitrary, but this particular set produces nice results
    // (original paper suggests hypercube edges' midpoints)
    // (anything works as long as it's 'ballanced')
    // (bigger sets tend to produce richer terrain features)
    private static Gradient gradients[] = {
        new Gradient(1, 1), new Gradient(-1, 1),
        new Gradient(1, -1), new Gradient(-1, -1),
        new Gradient(0, 1), new Gradient(0, -1)
    };

    // Performance trick
    private static int floor(double x) {
        return x > 0 ? (int) x : (int) x - 1;
    }

    private double vertexContrib(double x, double y, int gradientIndex) {
        double cmpAxis = 0.5 - x * x - y * y;
        if (cmpAxis < 0.0) {
            return 0.0;
        } else {
            // cmp^4 + (x, y).g (inner product)
            return Math.pow(cmpAxis, 4) * (gradients[gradientIndex].x * x + gradients[gradientIndex].y * y);
        }
    }
    // Offsets / skew factors for triangles
    private final double F = 0.5 * (Math.sqrt(3.0) - 1.0);
    private final double G = (3.0 - Math.sqrt(3.0)) / 6.0;

    public double noise(double x, double y) {
        // This looks scary and is mostly word-to-word translation of the
        // math from the paper.
        // Conceptually, it's pretty simple - chop the world up into 3-simplices,
        // come up with random values at their vertices, combine them using appropriate
        // gradients.

        double s = (x + y) * F;
        int unskewV0X = floor(x + s),
                unskewV0Y = floor(y + s);
        double t = (unskewV0X + unskewV0Y) * G;

        // 'First' vertex 'real' coordinates
        double x0 = x - (unskewV0X - t),
                y0 = y - (unskewV0Y - t);

        // Figure out the current triangle (Gustavson pp6)
        // Second corner offset relative to (unskewV0X, unskewV0Y)
        int v1XOff, v1YOff;
        if (x0 > y0) {
            // Upper
            v1XOff = 1;
            v1YOff = 0;
        } else {
            // Lower
            v1XOff = 0;
            v1YOff = 1;
        }

        // Unskew the remaining vertices
        double x1 = x0 - v1XOff + G,
                y1 = y0 - v1YOff + G,
                x2 = x0 - 1.0 + 2.0 * G,
                y2 = y0 - 1.0 + 2.0 * G;

        // Substitutes the hashing scheme presented in the original article and guess what - it's fast
        return 70.0 * (vertexContrib(x0, y0, randLookup(unskewV0X + randLookup(unskewV0Y)) % gradients.length)
                + vertexContrib(x1, y1, randLookup(unskewV0X + v1XOff + randLookup(unskewV0Y + v1YOff)) % gradients.length)
                + vertexContrib(x2, y2, randLookup(unskewV0X + 1 + randLookup(unskewV0Y + 1)) % gradients.length));
    }
}
