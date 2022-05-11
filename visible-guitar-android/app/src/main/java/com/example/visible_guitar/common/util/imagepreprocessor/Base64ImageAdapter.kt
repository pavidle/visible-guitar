package com.example.visible_guitar.common.util.imagepreprocessor

import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import com.example.visible_guitar.common.util.imagepreprocessor.base.ImageAdapter
import org.opencv.core.Mat
import org.opencv.core.MatOfByte
import org.opencv.imgcodecs.Imgcodecs

class Base64ImageAdapter : ImageAdapter<String> {
    override fun adaptFrom(mat: Mat): String {
        val byteArray = MatOfByte(mat).toArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    override fun adaptTo(type: String): MatOfByte {
        val array = Base64.decode(type, Base64.DEFAULT)
        return MatOfByte(*array)
    }

    class Factory : ImageAdapter.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T> create(): ImageAdapter<T> {
            return Base64ImageAdapter() as ImageAdapter<T>
        }
    }
}