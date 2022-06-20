package com.example.domain.ar.aruco.distorter

import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.core.Point
import org.opencv.imgproc.Imgproc
import org.opencv.utils.Converters

class PerspectiveDistorter(
    private val pointsOfInterest: List<Point>
) {
    private fun frameShapeToMat(frame: Mat): Mat {
        val secondPoints = listOf(
            Point(0.0, 0.0),
            Point(0.0, frame.height().toDouble()),
            Point(frame.width().toDouble(), frame.height().toDouble()),
            Point(frame.width().toDouble(), 0.0),
        )
        return Converters.vector_Point2f_to_Mat(secondPoints)
    }

    fun warpB(frame: Mat): Mat {
        if (pointsOfInterest.size != 4)
            return frame
        val first = Converters.vector_Point2f_to_Mat(pointsOfInterest)
        val second = frameShapeToMat(frame)
        val matrix = Imgproc.getPerspectiveTransform(first, second)
        val img = Mat()
        Imgproc.warpPerspective(frame, img, matrix, frame.size())
        return img
    }

    fun warp(frame: Mat): Mat {
        if (pointsOfInterest.size != 4)
            return frame
        val first = Converters.vector_Point2f_to_Mat(pointsOfInterest)
        val second = frameShapeToMat(frame)
        val matrix = Imgproc.getPerspectiveTransform(second, first)
        val img = Mat()
        Imgproc.warpPerspective(frame, img, matrix, frame.size())
        return img
    }
}