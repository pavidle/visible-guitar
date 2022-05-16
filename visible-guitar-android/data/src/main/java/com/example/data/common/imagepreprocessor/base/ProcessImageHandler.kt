package com.example.data.common.imagepreprocessor.base

import org.opencv.core.Mat

interface ProcessImageHandler {
    fun handle(mat: Mat) : Mat
}