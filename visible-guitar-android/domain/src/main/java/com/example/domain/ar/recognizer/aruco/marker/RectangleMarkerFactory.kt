package com.example.domain.ar.recognizer.aruco.marker

import com.example.domain.ar.base.Marker
import com.example.domain.ar.recognizer.aruco.factory.MarkerFactory
import org.opencv.core.Point

class RectangleMarkerFactory : MarkerFactory {
    override fun create(corners: List<Point>, arucoId: Int): Marker {
        return when (arucoId) {
            0 -> TopRightSideMarker(corners)
            1 -> BottomRightSideMarker(corners)
            2 -> LeftSideMarker(corners)
            else -> EmptyMarker(corners)
        }
    }
}