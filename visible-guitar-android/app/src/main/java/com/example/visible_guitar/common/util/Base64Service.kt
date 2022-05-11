package com.example.visible_guitar.common.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import org.opencv.android.Utils
import org.opencv.core.*
import javax.inject.Inject
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc
import java.io.ByteArrayOutputStream


class Base64Service @Inject constructor() {

    fun encode(mat: Mat): String {
        val matOfByte = MatOfByte()
//        val resizedSize = Size(300.0, 300.0)
//        val newSize = Mat()
//        Imgproc.resize(mat, newSize, resizedSize)
        Imgcodecs.imencode(".jpg", mat, matOfByte,
            MatOfInt(Imgcodecs.IMWRITE_JPEG_QUALITY, 55)
        )
        val byteArray = matOfByte.toArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    fun decode(base64: String): Bitmap {
        val array = Base64.decode(base64, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(array, 0, array.size)
    }

}