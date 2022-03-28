package com.example.visible_guitar.common

import android.content.res.Resources
import android.graphics.Color
import androidx.compose.ui.res.colorResource
import com.example.visible_guitar.R
import kotlin.random.Random


fun getRandomColorRGB(from: Int, to: Int) : Int {
    val color = (from..to)
    return Color.rgb(color.random(), color.random(), color.random())
}
