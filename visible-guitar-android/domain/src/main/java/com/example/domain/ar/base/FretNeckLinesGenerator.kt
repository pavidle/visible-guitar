package com.example.domain.ar.base

import com.example.domain.ar.aruco.model.Line
import org.opencv.core.Mat

interface FretNeckLinesGenerator {
    fun generateFretLines(frame: Mat): List<Line>
}