package com.example.domain.ar.aruco.detector

import com.example.domain.ar.aruco.model.ArucoDetectionResult
import com.example.domain.ar.base.Marker
import com.example.domain.ar.aruco.factory.MarkerFactory
import org.opencv.core.Mat
import org.opencv.core.Point
import org.opencv.utils.Converters

class MarkerResolver(
    private val markerFactories: List<MarkerFactory>,
    private val detector: ArucoDetector
) {

    private fun getArucoIdsByDetectionResult(
        detectionResult: ArucoDetectionResult
    ): List<Int> {
        val arucoIds = mutableListOf<Int>()
        Converters.Mat_to_vector_int(detectionResult.arucoIds, arucoIds)
        return arucoIds
    }

    private fun getPointsOfCornersByMat(matOfCorners: Mat): MutableList<Point> {
        val corners = mutableListOf<Point>()
        for (i in 0 until matOfCorners.width()) {
            val x = matOfCorners[0, i][0]
            val y = matOfCorners[0, i][1]
            corners.add(Point(x, y))
        }
        return corners
    }


    fun findMarkersOnFrame(frame: Mat): List<Marker> {
        val detectionResult = detector.detect(frame)
        val markers = mutableListOf<Marker>()
        if (detectionResult.corners.isEmpty())
            return markers
        val arucoIds = getArucoIdsByDetectionResult(detectionResult)
        markerFactories.forEach { factory ->
            detectionResult.corners.zip(arucoIds) { matOfCorners, arucoId ->
                val corners = getPointsOfCornersByMat(matOfCorners)
                val marker = factory.create(corners, arucoId)
                corners.clear()
                markers.add(marker)
            }
        }
        return markers
    }
}