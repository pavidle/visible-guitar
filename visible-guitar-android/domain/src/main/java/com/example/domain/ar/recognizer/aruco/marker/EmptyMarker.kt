package com.example.domain.ar.recognizer.aruco.marker

import com.example.domain.ar.base.Marker
import org.opencv.core.Point

class EmptyMarker(
    corners: List<Point>,
//    arucoId: Int
) : Marker(corners) {
    override fun getInterestPoints(): List<Point> =
        listOf()
}