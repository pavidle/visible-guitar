package com.example.visible_guitar.model

import android.graphics.Bitmap

data class ReceiveData(
    val bitmap: Bitmap,
    val message: String? = null
)
