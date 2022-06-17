package com.example.domain.ar.aruco.detector

import com.example.domain.ar.aruco.model.ArucoDetectionResult
import com.example.domain.ar.base.Detector
import org.opencv.aruco.Aruco
import org.opencv.aruco.Dictionary
import org.opencv.core.Mat

class ArucoDetector(
    private val typeOfAruco: Int,
    private val dictionary: Dictionary = Aruco.getPredefinedDictionary(typeOfAruco)
) : Detector<ArucoDetectionResult> {

    override fun detect(frame: Mat): ArucoDetectionResult {
        val corners = mutableListOf<Mat>()
        val arucoIds = Mat()
        Aruco.detectMarkers(
            frame,
            dictionary,
            corners,
            arucoIds
        )
        return ArucoDetectionResult(corners, arucoIds)
    }

}