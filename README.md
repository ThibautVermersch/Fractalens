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

interface IComplexPlane {
    + {abstract} asComplex(row : int, column : int) : IComplex
}

IComplexPlane --> IComplex : << creates >>

' ----------------- '
' Complex Sequences '
' ----------------- '

interface IComplexSequence extends Iterable<IComplex> {
    + {abstract} computeNext(previous : IComplex) : IComplex
    + {abstract} iterator() : Iterator<IComplex>
}

IComplexSequence --> IComplex : << generates >>

' -------------- '
' Image Handling '
' -------------- '

interface IFractalImage {
    + {abstract} getHeight() : int
    + {abstract} getWidth() : int
    + {abstract} setColor(row : int, column : int, color : Color) : void
    + {abstract} saveAs(path : String) : void
}

' ------------------ '
' Fractal Generation '
' ------------------ '

interface IFractalGenerator {
    + {abstract} generateImage() : IFractalImage
    + {abstract} computePixelColor(row : int, column : int) : Color
    + {abstract} saveImage() : void
}

abstract class AbstractComplexSequence implements IComplexSequence {
    - final initial : IComplex
    - final maxIterations : int
    * AbstractComplexSequence(initial : IComplex, maxIterations : int)
    + iterator Iterator<IComplex>
    + computeNext(previous : IComplex) : IComplex
}

Class JuliaSequence extends AbstractComplexSequence{
    - final c : IComplex
    + JuliaSequence(c : IComplex, maxIterations : int)
    + computeNext(previous : IComplex) : IComplex
    + getC() : IComplex
}



IFractalGenerator --> IFractalImage : << produces >>
IFractalGenerator --> IComplexSequence : << uses >>
```

## Feature list

| Features                                               | Design Pattern(s) (?)       | Author(s) |
|--------------------------------------------------------|-----------------------------|-----------|
| Complex number                                         |                             |           |
| Default complex plane                                  |                             |           |
| Scaled complex plane                                   |                             |           |
| Translated complex plane                               |                             |           |
| Sequence of complex numbers                            |                             |           |
| Iterator over a sequence of complex numbers            |                             |           |
| Julia sequence                                         |                             |           |
| Mandelbrot sequence                                    |                             |           |
| Multijulia sequence                                    |                             |           |
| Multibrot sequence                                     |                             |           |
| Generic sequences                                      |                             |           |
| Color palette                                          |                             |           |
| Grey scale palette                                     |                             |           |
| Red/Green/Blue/... scale palette                       |                             |           |
| Inverted color palette                                 |                             |           |
| Gradient-based color palette                           |                             |           |
| Predefined color palette                               |                             |           |
| `BufferedImage` used as an `IFractalImage`             |                             |           |
| Static image generation                                |                             |           |
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
