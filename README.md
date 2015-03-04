# Terrain Generator
Simple random terrains generated from [simplex noise](http://en.wikipedia.org/wiki/Simplex_noise). 

### Features
- Simple and multipass smooth terrain generation
- 3 texture mapping modes
- Multithreaded terrain generation
- Fancy water

## Screenshots

#### Noise visualization (height & texture splatting)
![Splat map](https://raw.githubusercontent.com/PJK/terrain_generator/previews/out1.png)
![Height map](https://raw.githubusercontent.com/PJK/terrain_generator/previews/out2.png)

#### Actual screenshots
![Screnshot](https://raw.githubusercontent.com/PJK/terrain_generator/previews/s1.png)
![Screnshot](https://raw.githubusercontent.com/PJK/terrain_generator/previews/s2.png)
![Screnshot](https://raw.githubusercontent.com/PJK/terrain_generator/previews/s3.png)

## Build

Prerequisites: JDK 1.7, JME3, ant

```
ant
```

## Usage

Controls: WASD to move, mouse drag to rotate the camera

```
⇒  java -jar dist/terrain_generator.jar -h                                                            [0]
Usage:  java -jar path_to/terrain_generator.jar -h --[no-]water --seed <seed>
        --terrain multipass|simple --mapping simple|uniform|randomized
        --size <patch> <tile>--write-bitmaps <dir>
        --fullscreen --resolution <w> <h>

All of the options are optional.

        --[no-]water            Show water
                                Default: true

        --seed                  PRNG seed
                                Default: 0

        --terrain               multipass - simplex noise terrain with 5 octaves (richer)
                                simple - single layer of simplex noise (faster)
                                Default: Multipass

        --mapping               simple          uniform smooth height-based map with 3 texture and slope
                                                detection (fastest)
                                unifrom         uniform smooth height-based map with 3 textures,
                                                optionally outputs PNGs
                                randomized      randomized smooth height-based map with 3 + 1 textures,
                                                optionally outputs PNGs (fanciest)
                                Default: Simple

        --size                  Terrain is loaded by tiles (controls viewing distance & performance)
                                which are stored in Q trees by patches. Both must be N^2 + 1
                                Default: 513, 1025

        --write-bitmaps         Output PNG heightmaps & texture splatting alpha maps to
                                the specified directory
                                Default: null

        --fullscreen            Use fullscreen mode (your resolution must match system supported options)
                                Default: false

        --resolution            Resolution in px (availability is system-dependent)
                                Default: (1280, 720)
```

### Memory
JME3 uses direct buffer allocation to store terrain meshes. Depending on your system settings, you might need to increase the default JVM limit
using -XX:MaxDirectMemorySize=<num>(M|G)`. This number will depend on your system architecture, JVM version, and, more importantly, on terrain tile size.

Insufficient available memory will result in errors such as
```
java.lang.OutOfMemoryError: Direct buffer memory
	at java.nio.Bits.reserveMemory(Bits.java:658)
	at java.nio.DirectByteBuffer.<init>(DirectByteBuffer.java:123)
	at java.nio.ByteBuffer.allocateDirect(ByteBuffer.java:311)
        [...]
	at com.pavelkalvoda.misc.terragen.terrain.loading.DynamicTileQuadLoader.getTerrainQuadAt(DynamicTileQuadLoader.java:28)
        [...]
```
which will prevent chunks from loading (creating void holes in the terrain).

For OpenJDK 1.7 on 64bit linux and OS X with the default generator settings, `-Xmx512M -XX:MaxDirectMemorySize=512M` works just fine.

## Dependencies

[JMonkey engine 3](http://jmonkeyengine.org/)

## License

GPL. See `LICENSE`. Pavel Kalvoda, 2015

Textures are courtesy of http://www.cgtextures.com/ and therefore subject to their license.
