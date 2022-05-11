package com.example.visible_guitar.common.util.imagepreprocessor.base

import org.opencv.core.Mat

interface ProcessImageHandler {
    fun handle(mat: Mat) : Mat
}