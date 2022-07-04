package com.example.domain.ar.recognizer.aruco

import com.example.domain.ar.base.Recognizer
import com.example.domain.ar.recognizer.aruco.marker.RectangleMarkerFactory
import org.opencv.aruco.Aruco

class Aruco(
    private val builder: ArucoRecognizer.Builder
) {
    fun createDefault(): Recognizer {
        return builder.setTypeOfAruco(Aruco.DICT_6X6_250)
            .addMarkerFactory(RectangleMarkerFactory())
            .setPointsSorter(TrigonometryPointsSorter())
            .build()
    }
}