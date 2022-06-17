package com.example.domain.ar.aruco.drawer

import android.util.Log
import com.example.domain.ar.base.CrossPointsNeckCellGenerator
import com.example.domain.ar.base.Drawer
import com.example.domain.ar.base.StringNeckLinesGenerator
import org.opencv.core.Mat
import org.opencv.core.Scalar
import org.opencv.imgproc.Imgproc

class LabelsDrawer(
    private val neckCellGenerator: CrossPointsNeckCellGenerator
) : Drawer {

    override fun draw(frame: Mat): Mat {
//        val strings = neckCellGenerator.generateStringLines(frame)
//        val frets = neckCellGenerator.generateFretLines(frame)
        val points = neckCellGenerator.findCross(frame)
//        strings.forEach { line ->
//            Imgproc.line(frame, line.first, line.second, Scalar(255.0, 255.0, 0.0), 12)
//        }
//
//        frets.forEach { line ->
//            Imgproc.line(frame, line.first, line.second, Scalar(255.0, 255.0, 0.0), 12)
//        }


        points.forEach { point ->
            Imgproc.circle(frame, point, 12, Scalar(255.0, 0.0, 0.0), -1)
        }

        return frame
    }
}