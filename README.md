# FractaLens - A Fractal Generator Library Developed in Lens.

This project provides a *Java* library for generating fractal images.
It allows users to choose from a variety of complex sequences, including the
Julia set, Mandelbrot set, and other generalized sequences, to create
static or animated fractal images using different color palettes and
transformations.

## Overview

```plantuml
hide empty members


    class Fractalens {
        - Fractalens()
        + {static} main(args : String[]) : void
    }

    class FractalensCommandCli {
        - boolean helpRequested
        - ComplexSequenceType fractalName
        - int nbIterations
        - int height
        - int width
        - double scale
        - double focusX
        - double focusY
        - ColorStrategyType paletteName
        - GenerationType type
        - int imageCount
        - String outputFile
        + FractalensCommandCli()
        + call() : Integer
    }

    enum GenerationType {
        STATIC
        ZOOM
        ROTATION
    }

    Fractalens ..> FractalensCommandCli : launches
    FractalensCommandCli +-- GenerationType


package complex {
}

package sequence {
}

package generator {
}

package image {
}

```
### Package complex
```plantuml
hide empty members
package complex {

    interface IComplex {
        + {abstract} getRealPart() : double
        + {abstract} getImaginaryPart() : double
        + {abstract} abs() : double
        + {abstract} negate() : IComplex
        + {abstract} conjugate() : IComplex
        + {abstract} add(other : IComplex) : IComplex
        + {abstract} subtract(other : IComplex) : IComplex
        + {abstract} multiply(value : double) : IComplex
        + {abstract} multiply(other : IComplex) : IComplex
        + {abstract} divide(other : IComplex) : IComplex
        + {abstract} pow(exponent : int) : IComplex
        + {abstract} squared() : IComplex
    }

    class Complex implements IComplex {
        - private double a
        - private double b
        + Complex(double a, double b)
        + getRealPart() : double
        + getImaginaryPart() : double
        + abs() : double
        + negate() : IComplex
        + conjugate() : IComplex
        + add(other : IComplex) : IComplex
        + subtract(other : IComplex) : IComplex
        + multiply(value : double) : IComplex
        + multiply(other : IComplex) : IComplex
        + divide(other : IComplex) : IComplex
        + pow(exponent : int) : IComplex
        + squared() : IComplex
        + toString() : String
        + equals(obj : Object) : boolean
        + hashCode() : int
    }

    interface IComplexPlane {
        + {abstract} asComplex(row : int, column : int) : IComplex
    }

    class ComplexPlane implements IComplexPlane {
        - final int height
        - final int width
        + ComplexPlane(int height, int width)
        + asComplex(row : int, column : int) : IComplex
    }

    class ZoomComplexPlane implements IComplexPlane {
        - final IComplexPlane decorated
        - double scale
        + ZoomComplexPlane(IComplexPlane decorated, double scale)
        + asComplex(row : int, column : int) : IComplex
    }

    class TranslatedComplexPlane implements IComplexPlane {
        - final IComplexPlane decorated
        - final IComplex center
        + TranslatedComplexPlane(IComplexPlane decorated, IComplex center)
        + asComplex(row : int, column : int) : IComplex
    }

    ZoomComplexPlane o--> IComplexPlane : << decorates >>
    TranslatedComplexPlane o--> IComplexPlane : << decorates >>
    TranslatedComplexPlane o--> IComplex : center
    IComplexPlane ..> IComplex : << creates >>
}
```

### Package sequence
```plantuml
hide empty members
package sequence {

    interface IComplexSequence extends Iterable<IComplex> {
        + {abstract} computeNext(previous : IComplex) : IComplex
        + {abstract} iterator() : Iterator<IComplex>
    }

    abstract class AbstractComplexSequence implements IComplexSequence {
        - final initial : IComplex
        - final maxIterations : int
        # AbstractComplexSequence(initial : IComplex, maxIterations : int)
        + iterator() : Iterator<IComplex>
        + {abstract} computeNext(previous : IComplex) : IComplex
    }

    class MandelbrotSequence extends AbstractComplexSequence {
        + MandelbrotSequence(first : IComplex, maxIterations : int)
        + computeNext(previous : IComplex) : IComplex
    }

    class JuliaSequence extends AbstractComplexSequence {
        - final c : IComplex
        + JuliaSequence(first : IComplex, c : IComplex, maxIterations : int)
        + computeNext(previous : IComplex) : IComplex
        + getC() : IComplex
    }

    class ComplexSequenceIterator implements Iterator<IComplex> {
        - final sequence : IComplexSequence
        - current : IComplex
        - final maxIterations : int
        - iterations : int
        + ComplexSequenceIterator(sequence : IComplexSequence, initial : IComplex, maxIterations : int)
        + hasNext() : boolean
        + next() : IComplex
    }

    AbstractComplexSequence ..> ComplexSequenceIterator : << instantiates >>
    ComplexSequenceIterator --> IComplexSequence : << iterates >>
    MandelbrotSequence --|> AbstractComplexSequence
}
```

### Package generator
```plantuml
hide empty members
package generator {

    interface IColorStrategy {
        + {abstract} getColor(iterations : int) : Color
    }

    class GrayScaleStrategy implements IColorStrategy {
        - private int maxIterations
        + GrayScaleStrategy(int maxIterations)
        + getColor(iterations : int) : Color
    }

    class GradientColorStrategy implements IColorStrategy {
        - private final int maxIterations
        - private final Color startColor
        - private final Color endColor
        + GradientColorStrategy(int maxIterations, Color startColor, Color endColor)
        + getColor(iterations : int) : Color
        - computeRatio(iterations : int) : double
        - {static} interpolate(int start, int end, double ratio) : int
    }

    class FixedColorStrategy implements IColorStrategy {
        - private final int maxIterations
        - private final ColorPalette palette
        - private final Color[] customColors
        + FixedColorStrategy(int maxIterations)
        + FixedColorStrategy(int maxIterations, ColorPalette palette)
        + FixedColorStrategy(int maxIterations, Color[] customColors)
        + getColor(iterations : int) : Color
    }

    class InvertedColorStrategy implements IColorStrategy {
        - private final IColorStrategy decorated
        - {static} final int MAX_COMPONENT
        + InvertedColorStrategy(IColorStrategy decorated)
        + getColor(iterations : int) : Color
    }

    class ColorMaskStrategy implements IColorStrategy {
        - private final IColorStrategy decorated
        - private final double redCoefficient
        - private final double greenCoefficient
        - private final double blueCoefficient
        + ColorMaskStrategy(IColorStrategy decorated, boolean keepRed, boolean keepGreen, boolean keepBlue)
        + ColorMaskStrategy(IColorStrategy decorated, double redCoefficient, double greenCoefficient, double blueCoefficient)
        - {static} checkCoefficient(double coefficient) : void
        + getColor(iterations : int) : Color
    }

    enum ColorPalette {
        RAINBOW, FIRE, ICE, SUNSET, FOREST, NEON, CYBER
        - private final Color[] colors
        + getColor(int iterations, int maxIterations) : Color
    }

    enum ColorStrategyType {
        CYBER, FIRE, ICE, RAINBOW, SUNSET, FOREST, NEON, GRAYSCALE, GRADIENT_BLUE_YELLOW, GRADIENT_RED_CYAN, GRADIENT_PURPLE_GREEN, GRADIENT_ORANGE_PURPLE, GRADIENT_MAGENTA_LIME, GRADIENT_FIRE_ICE, INVERTED_CYBER, MASK_RED_ONLY
        - private final Function<Integer, IColorStrategy> factory
        + {static} createInverted(int maxIterations, ColorPalette palette) : IColorStrategy
        + {static} createInverted(IColorStrategy strategy) : IColorStrategy
        + {static} createMasked(int maxIterations, ColorPalette palette, double r, double g, double b) : IColorStrategy
        + createStrategy(int maxIterations) : IColorStrategy
        + getFactory() : Function<Integer, IColorStrategy>
    }

    interface IFractalGenerator {
        + {abstract} generateImage() : IFractalImage
        + {abstract} computePixelColor(row : int, column : int) : Color
        + {abstract} saveImage() : void
    }

    class FractalGenerator implements IFractalGenerator {
        - private final BiFunction<IComplex, Integer, IComplexSequence> sequenceFactory
        - private final IFractalImage image
        - private final IComplexPlane plane
        - private final int maxIterations
        - private final IColorStrategy colorStrategy
        - private final String path
        + FractalGenerator(BiFunction sequenceFactory, IFractalImage image, IComplexPlane plane, int maxIterations, IColorStrategy colorStrategy, String path)
        + generateImage() : IFractalImage
        + computePixelColor(int row, int column) : Color
        + saveImage() : void
        + getImage() : IFractalImage
    }

    class AnimatedFractalGenerator implements IFractalGenerator {
        - private final List<IFractalGenerator> generators
        - private final String outputPath
        - private final List<IFractalImage> frames
        - private final int delayMs
        + AnimatedFractalGenerator(List<IFractalGenerator> generators, String outputPath, int delayMs)
        + getGenerators() : List<IFractalGenerator>
        + generateImage() : IFractalImage
        + computePixelColor(int row, int column) : Color
        + saveImage() : void
    }

    class FractalGeneratorBuilder {
        - private int width
        - private int height
        - private int maxIterations
        - private Double zoom
        - private IComplex center
        - private String outputFile
        - private BiFunction sequenceFactory
        - private IntFunction colorStrategyFactory
        + FractalGeneratorBuilder()
        + withDimensions(int width, int height) : FractalGeneratorBuilder
        + withMaxIterations(int maxIterations) : FractalGeneratorBuilder
        + withZoom(double zoom) : FractalGeneratorBuilder
        + withCenter(IComplex center) : FractalGeneratorBuilder
        + withOutputFile(String outputFile) : FractalGeneratorBuilder
        + withSequence(ComplexSequenceType type) : FractalGeneratorBuilder
        + withSequence(BiFunction factory) : FractalGeneratorBuilder
        + withColorStrategy(ColorStrategyType type) : FractalGeneratorBuilder
        + withColorStrategy(IColorStrategy strategy) : FractalGeneratorBuilder
        + withColorStrategyFactory(IntFunction factory) : FractalGeneratorBuilder
        + build() : IFractalGenerator
        + buildZoomAnimation(int frame, double zoomFactor, int delay) : IFractalGenerator
        + buildJuliaRotationAnimation(int frameCount, IComplex initialC, int delayMs) : IFractalGenerator
    }

    InvertedColorStrategy o--> IColorStrategy : decorated
    ColorMaskStrategy o--> IColorStrategy : decorated
    FixedColorStrategy o--> ColorPalette : palette
    FractalGeneratorBuilder ..> FractalGenerator : builds
    FractalGeneratorBuilder ..> AnimatedFractalGenerator : builds
}
```

### Package image
```plantuml
hide empty members
package image {

    interface IFractalImage {
        + {abstract} getHeight() : int
        + {abstract} getWidth() : int
        + {abstract} setColor(row : int, column : int, color : Color) : void
        + {abstract} saveAs(path : String) : void
        + {abstract} asBufferedImage() : BufferedImage
    }

    class BufferedImageAdaptater implements IFractalImage {
        - private final BufferedImage image
        + BufferedImageAdaptater(BufferedImage image)
        + BufferedImageAdaptater(int width, int height)
        + getHeight() : int
        + getWidth() : int
        + setColor(int row, int column, Color color) : void
        + saveAs(String path) : void
        + asBufferedImage() : BufferedImage
        + getBufferedImage() : BufferedImage
    }
}
```


## Usage Guide

You can run FractaLens via its Command Line Interface (CLI). Below are the main options and examples to get started.

### Command-Line Options

| Option                               | Description                                                                                                                                                                                                                                                                     |
|:-------------------------------------|:--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `-f, --fractal-name=<fractalName>`   | Name of the fractal to generate: `MANDELBROT`, `JULIA`, `JULIA_SPIRAL`, `MULTIBROT_CUBIC`, `MULTIJULIA_CUBIC`, `TRICORN`, `GENERALIZED_TRICORN`, `BURNING_SHIP`, `CUBIC_LAMBDA`                                                                                                 |
| `-h, --height=<height>`              | Image height in pixels.                                                                                                                                                                                                                                                         |
| `--help`                             | Display this help message and exit.                                                                                                                                                                                                                                             |
| `-i, --image-count=<imageCount>`     | Number of frames for animations.                                                                                                                                                                                                                                                |
| `-n, --nb-iterations=<nbIterations>` | Maximum number of iterations.                                                                                                                                                                                                                                                   |
| `-o, --output=<outputFile>`          | Output file (`.png` or `.gif`).                                                                                                                                                                                                                                                 |
| `-p, --palette-name=<paletteName>`   | Color palette name: `CYBER`, `FIRE`, `ICE`, `RAINBOW`, `SUNSET`, `FOREST`, `NEON`, `GRAYSCALE`, `GRADIENT_BLUE_YELLOW`, `GRADIENT_RED_CYAN`, `GRADIENT_PURPLE_GREEN`, `GRADIENT_ORANGE_PURPLE`, `GRADIENT_MAGENTA_LIME`, `GRADIENT_FIRE_ICE`, `INVERTED_CYBER`, `MASK_RED_ONLY` |
| `-s, --scale=<scale>`                | Scale / zoom to apply (Between 0 and 1).                                                                                                                                                                                                                                        |
| `-t, --type=<type>`                  | Type of image to generate: `STATIC`, `ZOOM`, `ROTATION`.                                                                                                                                                                                                                        |
| `-w, --width=<width>`                | Image width in pixels.                                                                                                                                                                                                                                                          |
| `-x, --focus-x=<focusX>`             | Real part of the central point.                                                                                                                                                                                                                                                 |
| `-y, --focus-y=<focusY>`             | Imaginary part of the central point.                                                                                                                                                                                                                                            |
when options are listed for the generator, the first one is default

### Examples


1. **Generate a Julia fractal with custom dimensions and iterations:**
   ```bash
   ./gradlew run --args="-f JULIA -w 1920 -h 1080 -i 250 -o julia.png"
   ```

2. **Both of these generate a standard Mandelbrot fractal with zoom:**
   ```bash
   ./gradlew run --args="MANDELBROT -t ZOOM -o zoom_animation.gif"
   ./gradlew run --args="-t ZOOM -o zoom_animation.gif"
   ```


## Feature list

| Features                                    | Design Pattern(s) (?)      | Author(s)         |
|---------------------------------------------|----------------------------|-------------------|
| Complex number                              |                            | Brichard Lucas    |
| Default complex plane                       | Decorator                  | Pispisa Mathéo    |
| Scaled complex plane                        | Decorator                  | Vermersch Thibaut |
| Translated complex plane                    | Decorator                  | Vermersch Thibaut |
| Sequence of complex numbers                 | Patron de méthode          | Vermersch Thibaut |
| Iterator over a sequence of complex numbers | Iterator                   | Cividino Yeleen   |
| Julia sequence                              |                            | Vermersch Thibaut |
| Burningship sequence                        |                            | Brichard Lucas    |
| Mandelbrot sequence                         |                            | Cividino Yeleen   |
| Multijulia sequence                         |                            | Vermersch Thibaut |
| Multibrot sequence                          |                            | Cividino Yeleen   |
| Generic sequences                           | Strategy / Binary Operator | Vermersch Thibaut |
| Fixed Color palettes                        | Strategy                   | Brichard Lucas    |
| Grey scale palette                          | Strategy                   | Brichard Lucas    |
| Red/Green/Blue/... scale palette            | Decorator                  | Pispisa Mathéo    |
| Inverted color palette                      | Decorator                  | Pispisa Mathéo    |
| Gradient-based color palette                | Strategy                   | Cividino Yeleen   |
| Predefined color palette                    | Strategy / factory         | Brichard Lucas    |
| `BufferedImage` used as an `IFractalImage`  | Adapter                    | Vermersch Thibaut |
| Static image generation                     | Strategy                   | Cividino Yeleen   |
| Scale-based animated image generation       | Composite / Builder        | Brichard Lucas    |
| Rotation-based animated image generation    | Composite / Builder        | Pispisa Mathéo    |
| Configuration of the fractal generator      | Builder / Factory          | Vermersch Thibaut |
| Command-line interface                      |                            | Vermersch Thibaut |

## Team

This project has been developed by:

- Vermersch Thibaut
- Pispisa Mathéo
- Brichard Lucas
- Cividino Yeleen
