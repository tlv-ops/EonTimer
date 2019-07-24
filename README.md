# EonTimer

A Kotlin port of the Pokémon RNG Timer originally written by
[ToastPlusOne](https://bitbucket.org/ToastPlusOne/eontimer/downloads/).

![version](https://img.shields.io/badge/EonTimer-v2.0.1-blue.svg)
[![travis](https://travis-ci.org/dylmeadows/EonTimer.svg?branch=master)](https://travis-ci.org/dylmeadows/EonTimer)
[![codecov](https://codecov.io/gh/dylmeadows/EonTimer/branch/master/graph/badge.svg)](https://codecov.io/gh/dylmeadows/EonTimer)

## Releases
The latest release can be downloaded under the [releases](https://github.com/dylmeadows/EonTimer/releases) section of the EonTimer repository.

## Building from Source
EonTimer is built using [Java Development Kit 11](https://adoptopenjdk.net/). Allow other dependencies 
(JavaFX, Kotlin, etc.) are handled through the included gradle wrapper.

#### Windows
```bash
git clone https://github.com/dylmeadows/EonTimer
cd EonTimer
gradlew.bat clean build run
```

#### MacOS / Linux
```bash
git clone https://github.com/dylmeadows/EonTimer 
cd EonTimer
./gradlew clean build run
```

## Contributing
