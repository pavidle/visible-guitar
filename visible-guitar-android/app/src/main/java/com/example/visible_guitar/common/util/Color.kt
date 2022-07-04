package com.example.visible_guitar.common.util

import android.graphics.Color


fun getRandomColorRGB(from: Int, to: Int) : Int {
    val color = (from..to)
    return Color.argb(170, color.random(), color.random(), color.random())
}
