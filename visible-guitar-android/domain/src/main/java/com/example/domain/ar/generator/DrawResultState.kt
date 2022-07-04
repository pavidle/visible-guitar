package com.example.domain.ar.generator

import org.opencv.core.Mat

data class DrawResultState(
    val mat: Mat,
    val neckMesh: NeckMesh
)