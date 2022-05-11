package com.example.visible_guitar.model

import android.graphics.Bitmap
import org.opencv.core.Mat

data class ReceiveData(
    val bitmap: Mat,
    val message: String? = null
)
