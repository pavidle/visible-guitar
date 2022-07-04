package com.example.domain.ar

import android.util.Log
import com.example.domain.ar.base.Drawer
import com.example.domain.ar.generator.NeckMesh
import org.opencv.core.*
import org.opencv.imgproc.Imgproc

class NeckMeshDrawerWrapper(
    wrappedDrawer: Drawer,
    private val neckMesh: NeckMesh
) : NeckDrawerWrapper(wrappedDrawer) {

    override fun draw(): Mat {
        val wrappedFrame = super.draw()

        val m = Mat(
            Size(wrappedFrame.cols().toDouble(), wrappedFrame.rows().toDouble()),
            CvType.CV_8UC4
        )
        neckMesh.strings.forEach { line ->
            Imgproc.line(m, line.first, line.second, Scalar(0.0, 255.0, 0.0), 10)
        }

        neckMesh.frets.forEach { line ->
            Imgproc.line(m, line.first, line.second, Scalar(0.0, 255.0, 0.0), 10)
        }
        val result = Mat()
        Core.addWeighted(m, 1.0, wrappedFrame, 1.0, 1.0, result)
        return result
    }
}