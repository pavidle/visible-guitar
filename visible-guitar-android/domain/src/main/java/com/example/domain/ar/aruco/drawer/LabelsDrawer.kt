package com.example.domain.ar.aruco.drawer

import android.util.Log
import com.example.domain.ar.aruco.model.Note
import com.example.domain.ar.base.CrossPointsNeckCellGenerator
import com.example.domain.ar.base.Drawer
import com.example.domain.model.ChordEntity
import com.example.domain.repository.ChordRepository
import org.opencv.core.Mat
import org.opencv.core.Scalar
import org.opencv.imgproc.Imgproc

class LabelsDrawer(
    private val neckCellGenerator: CrossPointsNeckCellGenerator
) : Drawer {

    override fun draw(frame: Mat, chordEntity: ChordEntity): Mat {
        val strings = neckCellGenerator.generateStringLines(frame)
        val frets = neckCellGenerator.generateFretLines(frame)

        strings.forEach { line ->
            Imgproc.line(frame, line.first, line.second, Scalar(255.0, 255.0, 0.0), 12)
        }

        frets.forEach { line ->
            Imgproc.line(frame, line.first, line.second, Scalar(255.0, 255.0, 0.0), 12)
        }

        val notes = chordEntity.notes
        notes.forEach {
            val point = neckCellGenerator.findCross(frame, Note(it.stringNumber, it.fretNumber))
            Imgproc.circle(frame, point, 32, Scalar(255.0, 0.0, 0.0), -1)
        }

        return frame
    }
}