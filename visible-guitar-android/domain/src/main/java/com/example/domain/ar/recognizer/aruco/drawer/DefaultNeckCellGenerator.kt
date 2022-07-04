package com.example.domain.ar.recognizer.aruco.drawer

import com.example.domain.ar.model.Line
import com.example.domain.ar.model.LabelNote
import com.example.domain.ar.base.CrossPointsNeckCellGenerator
import org.opencv.core.Mat
import org.opencv.core.Point
import kotlin.math.pow

class DefaultNeckCellGenerator(
    private val countOfStrings: Int = 4,
    private val countOfFrets: Int = 15
) : CrossPointsNeckCellGenerator {

    override fun findAllCrosses(frame: Mat): List<Point> {
        val points = mutableListOf<Point>()
        val strings = generateStringLines(frame)
        val frets = generateFretLines(frame)
        strings.forEach { string ->
            val yString = string.first.y
            frets.forEach { fret ->
                val xFret = fret.first.x
                val intersection = Point(xFret, yString)
                points.add(intersection)
            }
        }
        return points
    }

    override fun findCross(frame: Mat, labelNote: LabelNote): Point {
        val strings = generateStringLines(frame)
        val frets = generateFretLines(frame)
        val yString = strings[labelNote.string].first.y
        val xFret = frets[labelNote.fret].first.x
        return Point(xFret, yString)
    }

    override fun generateFretLines(frame: Mat): List<Line> {
        val lines = mutableListOf<Line>()
        val lengthOfNeck = frame.width()
        val coefficient = 2.0.pow(1.0 / 12.0)
        val scale = lengthOfNeck / (1.0 - 1.0 / coefficient.pow(countOfFrets.toDouble()))

        (0..countOfFrets).onEach { fretIndex ->
            val ln = scale / 2.0.pow(fretIndex.toDouble() / 12.0)
//            val nextLn = scale / 2.0.pow((fretIndex + 1) / 12.toDouble())
            val r = lengthOfNeck - scale + ln
//            val nextX = scale - nextLn
//            val half = (nextX - x) / 2
            lines.add(
                Line(
                    Point(r, 0.0),
                    Point(r, frame.height().toDouble())
                )
            )
        }

        return lines
    }

    override fun generateStringLines(frame: Mat): List<Line> {
        val height = frame.height()
        val width = frame.width()
        val lines = mutableListOf<Line>()
        val heightBetweenString = height / (countOfStrings + 1)
        var yPosition = 0.0
        for (stringIndex in 0 until countOfStrings + 1) {
            // distance between begin of neck and lasts strings is smaller
            if (stringIndex == 0) {
                yPosition += heightBetweenString / 2
                lines.add(
                    Line(
                        Point(0.0, yPosition),
                        Point(width.toDouble(), yPosition)
                    )
                )
                continue
            }
            yPosition += heightBetweenString
            lines.add(
                Line(
                    Point(0.0, yPosition),
                    Point(width.toDouble(), yPosition)
                )
            )
        }
        return lines
    }
}