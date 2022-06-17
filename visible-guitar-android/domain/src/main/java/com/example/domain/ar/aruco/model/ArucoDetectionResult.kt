package com.example.domain.ar.aruco.model

import org.opencv.core.Mat

data class ArucoDetectionResult(
    val corners: List<Mat>,
    val arucoIds: Mat
)
