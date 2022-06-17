package com.example.domain.ar.aruco

import com.example.domain.ar.base.PointsSorter
import org.opencv.core.Point
import kotlin.math.atan2

class TrigonometryPointsSorter : PointsSorter {

    private fun getCenter(all: List<Point>): Point {
        val xCenter = all.sumOf { point -> point.x } / all.size
        val yCenter = all.sumOf { point -> point.y } / all.size
        return Point(xCenter, yCenter)
    }

    override fun sort(point: Point, all: List<Point>): Double {
        val center = getCenter(all)
        return ((1 / 2) * Math.PI +
                atan2(point.x - center.x, point.y - center.y)) % (2 * Math.PI)
    }
}