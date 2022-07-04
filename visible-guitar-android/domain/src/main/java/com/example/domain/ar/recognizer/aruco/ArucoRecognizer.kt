package com.example.domain.ar.recognizer.aruco

import com.example.domain.ar.base.Drawer
import com.example.domain.ar.recognizer.aruco.detector.ArucoDetector
import com.example.domain.ar.recognizer.aruco.detector.MarkerFinder
import com.example.domain.ar.base.PointsSorter
import com.example.domain.ar.base.Recognizer
import com.example.domain.ar.recognizer.aruco.factory.MarkerFactory
import org.opencv.aruco.Aruco
import org.opencv.core.Mat
import org.opencv.core.Point

class ArucoRecognizer(
    private val finder: MarkerFinder,
    private val pointsSorter: PointsSorter
) : Recognizer {

    override fun findInterestPoints(frame: Mat): List<Point> {
        val markers = finder.findMarkersOnFrame(frame)
        val allPoints = mutableListOf<Point>()
        markers.forEach { marker ->
            val points = marker.getInterestPoints()
            allPoints += points
        }
        allPoints.sortBy { point -> pointsSorter.sort(point, allPoints) }
        return allPoints
    }


    class Builder : Recognizer.Builder {

        private companion object {
            const val DEFAULT_TYPE_OF_ARUCO = Aruco.DICT_6X6_250
            val DEFAULT_POINTS_SORTER = TrigonometryPointsSorter()
        }

        private var typeOfAruco: Int = DEFAULT_TYPE_OF_ARUCO
        private var pointsSorter: PointsSorter = DEFAULT_POINTS_SORTER
        private val markerFactories = mutableListOf<MarkerFactory>()

        private fun createMarkerFinder() =
            MarkerFinder(
                markerFactories,
                ArucoDetector(typeOfAruco)
            )

        fun setTypeOfAruco(type: Int) = apply {
            typeOfAruco = type
        }

        fun addMarkerFactory(factory: MarkerFactory) = apply {
            markerFactories.add(factory)
        }

        fun setPointsSorter(sorter: PointsSorter) = apply {
            pointsSorter = sorter
        }

        override fun build(): Recognizer {
            val finder = createMarkerFinder()
            return ArucoRecognizer(finder, pointsSorter)
        }

    }
}