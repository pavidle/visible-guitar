package com.example.visible_guitar.ui.activity

import android.content.Context
import android.widget.TextView
import com.example.visible_guitar.R

internal class FpsMeter(
    private val context: Context
){
    private var fps: Int = 0
    private var startTime = 0
    private var currentTime = 1000

    fun setFps(textView: TextView) {
        if (currentTime - startTime >= 1000) {
            textView.text = context.getString(R.string.fps, fps.toString())
            fps = 0
            startTime = System.currentTimeMillis().toInt()
        }
        currentTime = System.currentTimeMillis().toInt()
        fps += 1
    }
}