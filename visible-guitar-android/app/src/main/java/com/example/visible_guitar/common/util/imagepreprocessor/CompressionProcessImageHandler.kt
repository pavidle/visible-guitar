package com.example.visible_guitar.common.util.imagepreprocessor

import com.example.visible_guitar.common.util.imagepreprocessor.base.ProcessImageHandler
import org.opencv.core.Mat
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc

class CompressionProcessImageHandler(
    private val compressionRatio: Double
) : ProcessImageHandler {

    override fun handle(mat: Mat): Mat {
        val compressedWidth = mat.width() * compressionRatio
        val compressedHeight = mat.height() * compressionRatio
        val resizedSize = Size(compressedWidth, compressedHeight)
        val newSize = Mat()
        Imgproc.resize(mat, newSize, resizedSize)
        return newSize
    }
}