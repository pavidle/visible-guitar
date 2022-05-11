package com.example.visible_guitar.common.util.imagepreprocessor

import com.example.visible_guitar.common.util.imagepreprocessor.base.ProcessImageHandler
import org.opencv.core.Mat
import org.opencv.core.MatOfByte
import org.opencv.core.MatOfInt
import org.opencv.imgcodecs.Imgcodecs

class ChangeQualityProcessImageHandler(
    private val quality: Int
) : ProcessImageHandler {

    override fun handle(mat: Mat): Mat {
        val newMat = MatOfByte()
        Imgcodecs.imencode(".jpg", mat, newMat,
            MatOfInt(Imgcodecs.IMWRITE_JPEG_QUALITY, quality)
        )
        return newMat
    }

}