package com.example.domain.ar

import android.util.Log
import com.example.domain.ar.base.Drawer
import com.example.domain.ar.generator.NeckMesh
import com.example.domain.ar.model.LabelNote
import org.opencv.core.*
import org.opencv.imgproc.Imgproc

class NeckLabelsDrawer private constructor(
    private val frame: Mat,
    private val points: List<Point>,
) : Drawer {

    override fun draw(): Mat {
        points.forEach { point ->

            Imgproc.ellipse(
                frame,
                point,
                Size(18.0, 36.0),
                0.0,
                0.0,
                360.0,
                Scalar(255.0, 255.0, 255.0),
                -1
            )
            Imgproc.ellipse(
                frame,
                point,
                Size(12.0, 30.0),
                0.0,
                0.0,
                360.0,
                Scalar(255.0, 0.0, 0.0),
                -1
            )
        }

        return frame
    }


    class Builder {

        private var labelNotes = mutableListOf<LabelNote>()
        private var neckMesh: NeckMesh? = null
        private var firstFrame: Mat? = null

        fun setLabelNotes(labelNotes: List<LabelNote>) = apply {
            this.labelNotes += labelNotes
        }

        fun addLabelNote(labelNote: LabelNote) = apply {
            labelNotes.add(labelNote)
        }

        fun setNeckMesh(neckMesh: NeckMesh) = apply {
            this.neckMesh = neckMesh
        }

        fun setFirstFrame(firstFrame: Mat) = apply {
            this.firstFrame = firstFrame
        }

        fun deleteLabelNotes() = labelNotes.clear()

        fun build(): Drawer {
            Log.e("HERE", labelNotes.toString())
            return NeckLabelsDrawer(
                requireNotNull(firstFrame),
                labelNotes.map { label ->
                    requireNotNull(neckMesh).findCross(label.string, label.fret)
                }
            )
        }
    }
}