package com.example.domain.ar.aruco.factory

import com.example.domain.ar.base.Marker
import org.opencv.core.Mat
import org.opencv.core.Point

interface MarkerFactory {
    fun create(corners: List<Point>, arucoId: Int): Marker
}