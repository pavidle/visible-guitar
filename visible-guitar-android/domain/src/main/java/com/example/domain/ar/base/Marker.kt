package com.example.domain.ar.base

import org.opencv.core.Mat
import org.opencv.core.Point

abstract class Marker(
    private val corners: List<Point>
) {
    protected val topLeft = Point(corners[0].x, corners[0].y)
    protected val topRight = Point(corners[1].x, corners[1].y)
    protected val bottomRight = Point(corners[2].x, corners[2].y)
    protected val bottomLeft = Point(corners[3].x, corners[3].y)

    abstract fun getInterestPoints(): List<Point>
}