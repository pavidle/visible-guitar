package com.example.visible_guitar.common.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import org.opencv.core.*
import javax.inject.Inject
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc


class Base64Service @Inject constructor() {

//    companion object {
//        private val PARAMS = MatOfInt(Imgcodecs.IMWRITE_JPEG_QUALITY, 95)
//    }

    fun encode(mat: Mat): String {
        val matOfByte = MatOfByte()
        val resizedSize = Size(500.0, 300.0)
        val newSize = Mat()
        Imgproc.resize(mat, newSize, resizedSize)
        Imgcodecs.imencode(".jpg", newSize, matOfByte)
        val byteArray = matOfByte.toArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    fun decode(base64: String): Bitmap {
        val array = Base64.decode(base64, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(array, 0, array.size)
    }

}