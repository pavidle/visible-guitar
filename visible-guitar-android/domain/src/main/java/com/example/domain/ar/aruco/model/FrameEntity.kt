package com.example.domain.ar.aruco.model

import org.opencv.core.Mat

data class FrameEntity (
    val matrixRgba: Mat,
    val matrixGray: Mat
)