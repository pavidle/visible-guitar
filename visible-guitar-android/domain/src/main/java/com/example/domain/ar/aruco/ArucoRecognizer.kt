package com.example.domain.ar.aruco

import com.example.domain.ar.aruco.detector.ArucoDetector
import com.example.domain.ar.aruco.detector.MarkerResolver
import com.example.domain.ar.aruco.marker.RectangleMarkerFactory
import com.example.domain.ar.base.PointsSorter
import com.example.domain.ar.base.Recognizer
import com.example.domain.ar.aruco.factory.MarkerFactory
import org.opencv.aruco.Aruco
import org.opencv.core.Mat
import org.opencv.core.Point

class ArucoRecognizer(
    private val resolver: MarkerResolver,
    private val pointsSorter: PointsSorter
) : Recognizer {

    override fun findInterestPoints(frame: Mat): List<Point> {
        val markers = resolver.findMarkersOnFrame(frame)
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
        private val markerFactories: MutableList<MarkerFactory> = mutableListOf()

        private fun createMarkerResolver() =
            MarkerResolver(
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

        fun buildDefault() =
            this
                .setTypeOfAruco(Aruco.DICT_6X6_250)
                .addMarkerFactory(RectangleMarkerFactory())
                .setPointsSorter(TrigonometryPointsSorter())
                .build()

        override fun build(): Recognizer {
            val resolver = createMarkerResolver()
            return ArucoRecognizer(resolver, pointsSorter)
        }

    }
}