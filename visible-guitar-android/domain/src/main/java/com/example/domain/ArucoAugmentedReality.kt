package com.example.domain

import android.util.Log
import com.example.domain.ar.base.AugmentedReality
import com.example.domain.ar.base.Drawer
import com.example.domain.ar.base.Recognizer
import com.example.domain.ar.recognizer.aruco.distorter.PerspectiveDistorter
import com.example.domain.ar.recognizer.aruco.model.FrameEntity
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.core.Point
import org.opencv.core.Scalar
import org.opencv.imgproc.Imgproc

class ArucoAugmentedReality(
    private val mat: Mat,
    private val recognizer: Recognizer
) : AugmentedReality {
    override fun augment(frameEntity: FrameEntity): Mat {
        val points = recognizer.findInterestPoints(frameEntity.matrixGray)

        val distorter = PerspectiveDistorter(points)
//        val image = drawer.draw()
        points.forEach {
            Imgproc.circle(frameEntity.matrixRgba, it, 5, Scalar(255.0, 255.0, 0.0), -1)
        }
        if (points.size != 4) {
            Imgproc.putText(
                frameEntity.matrixRgba,
                "Маркеры не обнаружены",
                Point(frameEntity.matrixRgba.size().height / 2, frameEntity.matrixRgba.size().width / 2),
                Imgproc.FONT_HERSHEY_COMPLEX,
                1.0,
                Scalar(0.0, 255.0, 0.0),
                3
            )
            return frameEntity.matrixRgba
        }

        val distorted = distorter.warp(mat)
        val result = Mat()
        Core.addWeighted(frameEntity.matrixRgba, 0.8, distorted, 1.0, 0.0, result)
        return result
    }

}