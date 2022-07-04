package com.example.domain.ar.base

import com.example.domain.ar.model.Line
import org.opencv.core.Mat

interface StringNeckLinesGenerator {
    fun generateStringLines(frame: Mat): List<Line>
}