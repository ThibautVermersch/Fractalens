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
}

class Complex implements IComplex {
    - final realPart : double
    - final imaginaryPart : double
    + Complex(realPart : double, imaginaryPart : double)
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
}

interface IComplexPlane {
    + {abstract} asComplex(row : int, column : int) : IComplex
}

class ComplexPlane implements IComplexPlane {
    - final width : int
    - final height : int
    + ComplexPlane(width : int, height : int)
    + asComplex(row : int, column : int) : IComplex
}

class ZoomComplexPlane implements IComplexPlane {
    - final scale : double
    + ZoomComplexPlane(decorated : IComplexPlane, scale : double)
    + asComplex(row : int, column : int) : IComplex
}

ZoomComplexPlane o--> IComplexPlane : << decorates >>
IComplexPlane ..> IComplex : << creates >>

' ----------------- '
' Complex Sequences '
' ----------------- '

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
IComplexSequence ..> IComplex : << generates >>

' ---------------- '
' Color Strategies '
' ---------------- '

interface IColorStrategy {
    + {abstract} getColor(iterations : int, maxIterations : int) : Color
}

class GrayscaleStrategy implements IColorStrategy {
    + getColor(iterations : int, maxIterations : int) : Color
}

' -------------- '
' Image Handling '
' -------------- '

interface IFractalImage {
    + {abstract} getHeight() : int
    + {abstract} getWidth() : int
    + {abstract} setColor(row : int, column : int, color : Color) : void
    + {abstract} saveAs(path : String) : void
}

class BufferedImageAdapter implements IFractalImage {
    - final image : BufferedImage
    + BufferedImageAdapter(image : BufferedImage)
    + BufferedImageAdapter(width : int, height : int)
    + getHeight() : int
    + getWidth() : int
    + setColor(row : int, column : int, color : Color) : void
    + saveAs(path : String) : void
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
    - final maxIterations : int
    - final path : String
    + FractalGenerator(sequenceFactory : BiFunction<IComplex, Integer, IComplexSequence>, image : IFractalImage, plane : IComplexPlane, maxIterations : int, colorStrategy : IColorStrategy, path : String)
    + generateImage() : IFractalImage
    + computePixelColor(row : int, column : int) : Color
    + saveImage() : void
}

FractalGenerator o--> IFractalImage : image
FractalGenerator o--> IComplexPlane : plane
FractalGenerator o--> IColorStrategy : colorStrategy
FractalGenerator ..> IComplexSequence : creates via factory
IFractalGenerator ..> IFractalImage : << produces >>
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
