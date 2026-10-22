# FractaLens - A Fractal Generator Library Developed in Lens.

This project provides a *Java* library for generating fractal images.
It allows users to choose from a variety of complex sequences, including the
Julia set, Mandelbrot set, and other generalized sequences, to create
static or animated fractal images using different color palettes and
transformations.

## Overview

This library is composed of the following classes:

```plantuml
hide empty members

' --------------- '
' Complex Numbers '
' --------------- '

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
    - a : double
    - b : double
    + Complex(a : double, b : double)
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
    - final height : int
    - final width : int
    + ComplexPlane(height : int, width : int)
    + asComplex(row : int, column : int) : IComplex
}

class ZoomComplexPlane implements IComplexPlane {
    - final decorated : IComplexPlane
    ~ scale : double
    + ZoomComplexPlane(decorated : IComplexPlane, scale : double)
    + asComplex(row : int, column : int) : IComplex
}

class TranslatedComplexPlane implements IComplexPlane {
    - final decorated : IComplexPlane
    - final center : IComplex
    + TranslatedComplexPlane(decorated : IComplexPlane, center : IComplex)
    + asComplex(row : int, column : int) : IComplex
}

ZoomComplexPlane o--> IComplexPlane : << decorates >>
TranslatedComplexPlane o--> IComplexPlane : << decorates >>
TranslatedComplexPlane o--> IComplex : center
IComplexPlane ..> IComplex : << creates >>

' ----------------- '
' Complex Sequences '
' ----------------- '

interface IComplexSequence extends Iterable<IComplex> {
    + {abstract} computeNext(previous : IComplex) : IComplex
}

abstract class AbstractComplexSequence implements IComplexSequence {
    # final initial : IComplex
    # final maxIterations : int
    # AbstractComplexSequence(initial : IComplex, maxIterations : int)
    + iterator() : Iterator<IComplex>
    + getInitial() : IComplex
    + {abstract} computeNext(previous : IComplex) : IComplex
}

class JuliaSequence extends AbstractComplexSequence {
    - final c : IComplex
    + JuliaSequence(initial : IComplex, c : IComplex, maxIterations : int)
    + computeNext(previous : IComplex) : IComplex
    + getC() : IComplex
}

class MandelbrotSequence extends AbstractComplexSequence {
    + MandelbrotSequence(initial : IComplex, maxIterations : int)
    + computeNext(previous : IComplex) : IComplex
}

class MultibrotSequence extends AbstractComplexSequence {
    - {static} final MIN_POWER : int
    - final power : int
    + MultibrotSequence(initial : IComplex, power : int, maxIterations : int)
    + computeNext(previous : IComplex) : IComplex
    + getPower() : int
}

class MultijuliaSequence extends AbstractComplexSequence {
    - final constant : IComplex
    - final power : int
    + MultijuliaSequence(initial : IComplex, constant : IComplex, power : int, maxIterations : int)
    + computeNext(previous : IComplex) : IComplex
}

class TricornSequence extends AbstractComplexSequence {
    - final c : IComplex
    + TricornSequence(initial : IComplex, c : IComplex, maxIterations : int)
    + computeNext(previous : IComplex) : IComplex
    + getC() : IComplex
}

class GeneralizedTricornSequence extends AbstractComplexSequence {
    - {static} final MIN_DEGREES : int
    - final c : IComplex
    - final degree : int
    + GeneralizedTricornSequence(initial : IComplex, c : IComplex, maxIterations : int, degree : int)
    + computeNext(previous : IComplex) : IComplex
    + getC() : IComplex
    + getDegree() : int
}

class BurningShipSequence extends AbstractComplexSequence {
    - final z0 : IComplex
    + BurningShipSequence(z0 : IComplex, maxIterations : int)
    + computeNext(previous : IComplex) : IComplex
}

class GenericComplexSequence extends AbstractComplexSequence {
    - final operator : BinaryOperator<IComplex>
    + GenericComplexSequence(initial : IComplex, maxIteration : int, operator : BinaryOperator<IComplex>)
    + computeNext(previous : IComplex) : IComplex
    + {static} createCubicLambdaSequence(z0 : IComplex, maxIterations : int) : IComplexSequence
}

class ComplexIterator implements Iterator<IComplex> {
    - {static} final DIVERGENCE_THRESHOLD : double
    - final sequence : IComplexSequence
    - final maxIterations : int
    - nbIterations : int
    - current : IComplex
    + ComplexIterator(sequence : IComplexSequence, first : IComplex, maxIterations : int)
    + hasNext() : boolean
    + next() : IComplex
}

enum ComplexSequenceType {
    MANDELBROT
    JULIA
    JULIA_SPIRAL
    MULTIBROT_CUBIC
    MULTIJULIA_CUBIC
    TRICORN
    GENERALIZED_TRICORN
    BURNING_SHIP
    CUBIC_LAMBDA
    - final factory : BiFunction<IComplex, Integer, IComplexSequence>
    + getFactory() : BiFunction<IComplex, Integer, IComplexSequence>
}

AbstractComplexSequence ..> ComplexIterator : << instantiates >>
ComplexIterator --> IComplexSequence : << iterates >>
IComplexSequence ..> IComplex : << generates >>
AbstractComplexSequence o--> IComplex : initial
ComplexSequenceType ..> IComplexSequence : << creates via factory >>
ComplexSequenceType ..> MandelbrotSequence
ComplexSequenceType ..> JuliaSequence
ComplexSequenceType ..> MultibrotSequence
ComplexSequenceType ..> MultijuliaSequence
ComplexSequenceType ..> TricornSequence
ComplexSequenceType ..> GeneralizedTricornSequence
ComplexSequenceType ..> BurningShipSequence
ComplexSequenceType ..> GenericComplexSequence

' ---------------- '
' Color Strategies '
' ---------------- '

interface IColorStrategy {
    + {abstract} getColor(iterations : int) : Color
}

enum ColorPalette {
    RAINBOW
    FIRE
    ICE
    SUNSET
    FOREST
    NEON
    CYBER
    - final colors : Color[]
    + getColor(iterations : int, maxIterations : int) : Color
}

class GrayScaleStrategy implements IColorStrategy {
    - maxIterations : int
    + GrayScaleStrategy(maxIterations : int)
    + getColor(iterations : int) : Color
}

class FixedColorStrategy implements IColorStrategy {
    - final maxIterations : int
    - final palette : ColorPalette
    - final customColors : Color[]
    + FixedColorStrategy(maxIterations : int)
    + FixedColorStrategy(maxIterations : int, palette : ColorPalette)
    + FixedColorStrategy(maxIterations : int, customColors : Color[])
    + getColor(iterations : int) : Color
}

class GradientColorStrategy implements IColorStrategy {
    - final maxIterations : int
    - final startColor : Color
    - final endColor : Color
    + GradientColorStrategy(maxIterations : int, startColor : Color, endColor : Color)
    + getColor(iterations : int) : Color
}

class InvertedColorStrategy implements IColorStrategy {
    - {static} final MAX_COMPONENT : int
    - final decorated : IColorStrategy
    + InvertedColorStrategy(decorated : IColorStrategy)
    + getColor(iterations : int) : Color
}

class ColorMaskStrategy implements IColorStrategy {
    - final decorated : IColorStrategy
    - final redCoefficient : double
    - final greenCoefficient : double
    - final blueCoefficient : double
    + ColorMaskStrategy(decorated : IColorStrategy, keepRed : boolean, keepGreen : boolean, keepBlue : boolean)
    + ColorMaskStrategy(decorated : IColorStrategy, redCoefficient : double, greenCoefficient : double, blueCoefficient : double)
    + getColor(iterations : int) : Color
}

enum ColorStrategyType {
    CYBER
    FIRE
    ICE
    RAINBOW
    SUNSET
    FOREST
    NEON
    GRAYSCALE
    GRADIENT_BLUE_YELLOW
    GRADIENT_RED_CYAN
    GRADIENT_PURPLE_GREEN
    GRADIENT_ORANGE_PURPLE
    GRADIENT_MAGENTA_LIME
    GRADIENT_FIRE_ICE
    INVERTED_CYBER
    MASK_RED_ONLY
    - final factory : Function<Integer, IColorStrategy>
    + createStrategy(maxIterations : int) : IColorStrategy
    + getFactory() : Function<Integer, IColorStrategy>
    + {static} createInverted(maxIterations : int, palette : ColorPalette) : IColorStrategy
    + {static} createInverted(strategy : IColorStrategy) : IColorStrategy
    + {static} createMasked(maxIterations : int, palette : ColorPalette, r : double, g : double, b : double) : IColorStrategy
}

InvertedColorStrategy o--> IColorStrategy : << decorates >>
ColorMaskStrategy o--> IColorStrategy : << decorates >>
FixedColorStrategy o--> ColorPalette : palette
ColorPalette ..> Color : << provides >>
IColorStrategy ..> Color : << returns >>
ColorStrategyType ..> IColorStrategy : << creates via factory >>
ColorStrategyType ..> ColorPalette
ColorStrategyType ..> FixedColorStrategy
ColorStrategyType ..> GrayScaleStrategy
ColorStrategyType ..> GradientColorStrategy
ColorStrategyType ..> InvertedColorStrategy
ColorStrategyType ..> ColorMaskStrategy

' -------------- '
' Image Handling '
' -------------- '

interface IFractalImage {
    + {abstract} getHeight() : int
    + {abstract} getWidth() : int
    + {abstract} setColor(row : int, column : int, color : Color) : void
    + {abstract} saveAs(path : String) : void
    + default asBufferedImage() : BufferedImage
}

class BufferedImageAdaptater implements IFractalImage {
    - final image : BufferedImage
    + BufferedImageAdaptater(image : BufferedImage)
    + BufferedImageAdaptater(width : int, height : int)
    + getHeight() : int
    + getWidth() : int
    + setColor(row : int, column : int, color : Color) : void
    + saveAs(path : String) : void
    + asBufferedImage() : BufferedImage
    + getBufferedImage() : BufferedImage
}

' ------------------ '
' Fractal Generation '
' ------------------ '

interface IFractalGenerator {
    + {abstract} generateImage() : IFractalImage
    + {abstract} computePixelColor(row : int, column : int) : Color
    + {abstract} saveImage() : void
}

class FractalGenerator implements IFractalGenerator {
    - final sequenceFactory : BiFunction<IComplex, Integer, IComplexSequence>
    - final maxIterations : int
    - final path : String
    + FractalGenerator(sequenceFactory : BiFunction<IComplex, Integer, IComplexSequence>, image : IFractalImage, plane : IComplexPlane, maxIterations : int, colorStrategy : IColorStrategy, path : String)
    + generateImage() : IFractalImage
    + computePixelColor(row : int, column : int) : Color
    + saveImage() : void
    + getImage() : IFractalImage
}

class AnimatedFractalGenerator implements IFractalGenerator {
    - final generators : List<IFractalGenerator>
    - final frames : List<IFractalImage>
    - final outputPath : String
    - final delayMs : int
    + AnimatedFractalGenerator(generators : List<IFractalGenerator>, outputPath : String, delayMs : int)
    + generateImage() : IFractalImage
    + computePixelColor(row : int, column : int) : Color
    + saveImage() : void
    + getGenerators() : List<IFractalGenerator>
}

class FractalGeneratorBuilder {
    - width : int
    - height : int
    - maxIterations : int
    - zoom : Double
    - center : IComplex
    - outputFile : String
    - sequenceFactory : BiFunction<IComplex, Integer, IComplexSequence>
    - colorStrategyFactory : IntFunction<IColorStrategy>
    + FractalGeneratorBuilder()
    + withDimensions(width : int, height : int) : FractalGeneratorBuilder
    + withMaxIterations(maxIterations : int) : FractalGeneratorBuilder
    + withZoom(zoom : double) : FractalGeneratorBuilder
    + withCenter(center : IComplex) : FractalGeneratorBuilder
    + withOutputFile(outputFile : String) : FractalGeneratorBuilder
    + withSequence(type : ComplexSequenceType) : FractalGeneratorBuilder
    + withSequence(factory : BiFunction<IComplex, Integer, IComplexSequence>) : FractalGeneratorBuilder
    + withColorStrategy(type : ColorStrategyType) : FractalGeneratorBuilder
    + withColorStrategy(strategy : IColorStrategy) : FractalGeneratorBuilder
    + withColorStrategyFactory(factory : IntFunction<IColorStrategy>) : FractalGeneratorBuilder
    + build() : IFractalGenerator
    + buildZoomAnimation(frame : int, zoomFactor : double, delay : int) : IFractalGenerator
    + buildJuliaRotationAnimation(frameCount : int, initialC : IComplex, delayMs : int) : IFractalGenerator
}

class Fractalens {
    - Fractalens()
    + {static} main(args : String[]) : void
    - {static} generate(generator : IFractalGenerator) : void
}

AnimatedFractalGenerator o--> IFractalGenerator : << composites >>
AnimatedFractalGenerator o--> IFractalImage : frames
FractalGenerator o--> IFractalImage : image
FractalGenerator o--> IComplexPlane : plane
FractalGenerator o--> IColorStrategy : colorStrategy
FractalGenerator ..> IComplexSequence : << creates via factory >>
IFractalGenerator ..> IFractalImage : << produces >>

FractalGeneratorBuilder ..> FractalGenerator : << creates >>
FractalGeneratorBuilder ..> AnimatedFractalGenerator : << creates >>
FractalGeneratorBuilder ..> ComplexPlane : << creates >>
FractalGeneratorBuilder ..> ZoomComplexPlane : << creates >>
FractalGeneratorBuilder ..> TranslatedComplexPlane : << creates >>
FractalGeneratorBuilder ..> BufferedImageAdaptater : << creates >>
FractalGeneratorBuilder ..> JuliaSequence : << creates >>
FractalGeneratorBuilder ..> ComplexSequenceType : << uses >>
FractalGeneratorBuilder ..> ColorStrategyType : << uses >>

Fractalens ..> FractalGeneratorBuilder : << uses >>
Fractalens ..> IFractalGenerator : << runs >>
```

## Feature list

| Features                                               | Design Pattern(s) (?)       | Author(s) |
|--------------------------------------------------------|-----------------------------|-----------|
| Complex number                                         |                             |           |
| Default complex plane                                  |                             |           |
| Scaled complex plane                                   |                             |           |
| Translated complex plane                               |                             |           |
| Sequence of complex numbers                            |                             |           |
| Iterator over a sequence of complex numbers            |         Iterator                    |    Yeleen       |
| Julia sequence                                         |                             |           |
| Mandelbrot sequence                                    |                             |           |
| Multijulia sequence                                    |                             |           |
| Multibrot sequence                                     |                             |           |
| Generic sequences                                      |                             |           |
| Color palette                                          |                             |           |
| Grey scale palette                                     |                             |           |
| Red/Green/Blue/... scale palette                       |                             |           |
| Inverted color palette                                 |                             |           |
| Gradient-based color palette                           |    Strategy                       |     Yeleen      |
| Predefined color palette                               |                             |           |
| `BufferedImage` used as an `IFractalImage`             |                             |           |
| Static image generation                                |                             |        Yeleen   |
| Scale-based animated image generation                  |                             |           |
| Rotation-based animated image generation               |                             |           |
| Configuration of the fractal generator                 |                             |           |
| Command-line interface                                 |                             |           |

## Team

This project has been developed by:

- Vermersch Thibaut
- Pispisa Mathéo
- Brichard Lucas
- Cividino Yeleen