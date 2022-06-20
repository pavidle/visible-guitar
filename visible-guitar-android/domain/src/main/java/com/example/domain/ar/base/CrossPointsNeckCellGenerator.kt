package com.example.domain.ar.base

import com.example.domain.ar.aruco.model.Note
import org.opencv.core.Mat
import org.opencv.core.Point

interface CrossPointsNeckCellGenerator :
    StringNeckLinesGenerator,
    FretNeckLinesGenerator
{
    fun findCross(frame: Mat, note: Note): Point
    fun findAllCrosses(frame: Mat): List<Point>
}