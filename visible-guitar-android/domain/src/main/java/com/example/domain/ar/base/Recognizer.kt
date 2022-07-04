package com.example.domain.ar.base

import com.example.domain.ar.recognizer.aruco.factory.MarkerFactory
import org.opencv.core.Mat
import org.opencv.core.Point

interface Recognizer {

    fun findInterestPoints(frame: Mat): List<Point>

    interface Builder {
        fun build(): Recognizer
    }
}