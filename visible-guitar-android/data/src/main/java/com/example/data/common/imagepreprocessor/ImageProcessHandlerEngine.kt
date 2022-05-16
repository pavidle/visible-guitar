package com.example.data.common.imagepreprocessor

import com.example.data.common.imagepreprocessor.base.ProcessImageHandler
import org.opencv.core.Mat

class ImageProcessHandlerEngine(
    private val imageProcessHandlers: List<ProcessImageHandler>
) {
    fun processAll(mat: Mat): Mat {
        var currentMat = mat
        imageProcessHandlers.onEach { handler ->
            currentMat = handler.handle(currentMat)
        }

        return currentMat
    }
}