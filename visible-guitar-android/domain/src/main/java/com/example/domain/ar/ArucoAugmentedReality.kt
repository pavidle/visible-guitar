package com.example.domain.ar

import com.example.domain.ar.aruco.ArucoRecognizer
import com.example.domain.ar.aruco.distorter.PerspectiveDistorter
import com.example.domain.ar.aruco.drawer.DefaultNeckCellGenerator
import com.example.domain.ar.aruco.drawer.LabelsDrawer
import com.example.domain.ar.aruco.model.FrameEntity
import com.example.domain.ar.base.AugmentedReality
import com.example.domain.ar.base.CrossPointsNeckCellGenerator
import com.example.domain.ar.base.Drawer
import com.example.domain.ar.base.Recognizer
import org.opencv.core.*

class ArucoAugmentedReality private constructor(
    private val recognizer: Recognizer,
    private val drawer: Drawer
) : AugmentedReality {

    override fun augment(frameEntity: FrameEntity): Mat {
        val points = recognizer.findInterestPoints(frameEntity.matrixGray)
        val distorter = PerspectiveDistorter(points)
//        val empty = distorter.warpB(frame)
        val empty = Mat.zeros(frameEntity.matrixRgba.size(), CvType.CV_8UC4)
        if (points.size != 4) {
            return frameEntity.matrixRgba
        }
        val drawnImg = drawer.draw(empty)
        val rev = distorter.warp(drawnImg)
        val result = Mat()
        Core.addWeighted(frameEntity.matrixRgba, 1.0, rev, 1.0, 0.0, result)
        return result
    }

    class Builder : AugmentedReality.Builder {

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

        override fun build(): AugmentedReality {
            return ArucoAugmentedReality(recognizer, drawer)
        }
    }
}