package com.example.domain.ar

import android.util.Log
import com.example.domain.ar.aruco.ArucoRecognizer
import com.example.domain.ar.aruco.distorter.PerspectiveDistorter
import com.example.domain.ar.aruco.drawer.DefaultNeckCellGenerator
import com.example.domain.ar.aruco.drawer.LabelsDrawer
import com.example.domain.ar.base.CrossPointsNeckCellGenerator
import com.example.domain.ar.base.Drawer
import com.example.domain.ar.base.Recognizer
import org.opencv.android.CameraBridgeViewBase
import org.opencv.core.*
import org.opencv.imgproc.Imgproc

class AugmentedReality private constructor(
    private val recognizer: Recognizer,
    private val drawer: Drawer
) {

    fun handle(frame: Mat): Mat {
        val points = recognizer.findInterestPoints(frame)
        val distorter = PerspectiveDistorter(points)
        val empty = Mat.zeros(frame.size(), CvType.CV_8U)
        if (points.size != 4) {
            return frame
        }
        val drawnImg = drawer.draw(empty)
        val rev = distorter.warp(drawnImg)
        val result = Mat()
        Core.addWeighted(frame, 1.0, rev, 1.0, 0.0, result)
        return result
    }

    class Builder {

        private companion object {

            private fun createNeckCellGenerator(): CrossPointsNeckCellGenerator
                    = DefaultNeckCellGenerator()

            val neckCellGenerator = createNeckCellGenerator()
            val DEFAULT_RECOGNIZER = ArucoRecognizer.Builder()
                .buildDefault()
            val DEFAULT_DRAWER = LabelsDrawer(neckCellGenerator)
        }

        private var recognizer: Recognizer = DEFAULT_RECOGNIZER
        private var drawer: Drawer = DEFAULT_DRAWER

        fun setRecognizer(recognizer: Recognizer) = apply {
            this.recognizer = recognizer
        }

        fun setDrawer(drawer: Drawer) = apply {
            this.drawer = drawer
        }

        fun build(): AugmentedReality {
            return AugmentedReality(recognizer, drawer)
        }
    }
}