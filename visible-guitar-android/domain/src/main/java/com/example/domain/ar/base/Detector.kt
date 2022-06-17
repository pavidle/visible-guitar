package com.example.domain.ar.base

import org.opencv.core.Mat

interface Detector<T> {
    fun detect(frame: Mat): T
}