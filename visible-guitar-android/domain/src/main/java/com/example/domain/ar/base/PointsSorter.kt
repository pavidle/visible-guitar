package com.example.domain.ar.base

import org.opencv.core.Point

interface PointsSorter {
    fun sort(point: Point, all: List<Point>): Double
}