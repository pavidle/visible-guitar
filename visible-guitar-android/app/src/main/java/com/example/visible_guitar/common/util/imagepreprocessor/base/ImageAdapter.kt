package com.example.visible_guitar.common.util.imagepreprocessor.base

import org.opencv.core.Mat
import org.opencv.core.MatOfByte

interface ImageAdapter<T> {
    fun adaptFrom(mat: Mat): T
    fun adaptTo(type: T): Mat

    interface Factory {
        fun <T> create(): ImageAdapter<T>
    }
}