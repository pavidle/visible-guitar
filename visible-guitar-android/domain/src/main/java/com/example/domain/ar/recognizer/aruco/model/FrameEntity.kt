package com.example.domain.ar.recognizer.aruco.model

import org.opencv.core.Mat

data class FrameEntity (
    val matrixRgba: Mat,
    val matrixGray: Mat
)