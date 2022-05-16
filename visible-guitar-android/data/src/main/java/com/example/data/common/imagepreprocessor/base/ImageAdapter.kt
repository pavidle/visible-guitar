package com.example.data.common.imagepreprocessor.base

import org.opencv.core.Mat

interface ImageAdapter<T> {
    fun adaptFrom(mat: Mat): T
    fun adaptTo(type: T): Mat

    interface Factory {
        fun <T> create(): ImageAdapter<T>
    }
}