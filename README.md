# Terrain Generator
Simple random terrains generated from simplex noise. 

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
                                unifrom         uniform smooth height-based map with <TODO> textures,
                                                optionally outputs PNGs
                                randomized      randomized smooth height-based map with <TODO> textures,
                                                optionally outputs PNGs (fanciest)
                                Default: Simple

        --size                  Terrain is loaded by tiles (controls viewing distance & performance)
                                which are stored in Q trees by patches. Both must be N^2 + 1
                                Default: 513, 2049

        --write-bitmaps         Output PNG heightmaps & texture splatting alpha maps to
                                the specified directory
                                Default: null

        --fullscreen            Use fullscreen mode (your resolution must match system supported options)
                                Default: false

        --resolution            Resolution in px (availability is system-dependent)
                                Default: (1280, 720)
```

## Dependencies

[JMonkey engine 3](http://jmonkeyengine.org/)

## License

GPL. See `LICENSE`. Pavel Kalvoda, 2015

Textures are courtesy of http://www.cgtextures.com/ and therefore subject to their license.`