package com.example.visible_guitar.common.util.imagepreprocessor

import com.example.visible_guitar.common.util.imagepreprocessor.base.ProcessImageHandler
import org.opencv.core.Mat
import org.opencv.core.MatOfByte
import org.opencv.imgcodecs.Imgcodecs

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