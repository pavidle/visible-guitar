package com.example.domain.ar.base

import org.opencv.core.Mat
import org.opencv.core.Point

interface CrossPointsNeckCellGenerator :
    StringNeckLinesGenerator,
    FretNeckLinesGenerator
{
    fun findCross(frame: Mat): List<Point>
}