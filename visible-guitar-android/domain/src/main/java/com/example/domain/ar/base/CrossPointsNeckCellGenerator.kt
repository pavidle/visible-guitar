package com.example.domain.ar.base

import com.example.domain.ar.model.LabelNote
import org.opencv.core.Mat
import org.opencv.core.Point

interface CrossPointsNeckCellGenerator :
    StringNeckLinesGenerator,
    FretNeckLinesGenerator
{
    fun findCross(frame: Mat, labelNote: LabelNote): Point
    fun findAllCrosses(frame: Mat): List<Point>
}