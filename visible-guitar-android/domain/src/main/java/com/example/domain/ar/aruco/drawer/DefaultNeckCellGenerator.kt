package com.example.domain.ar.aruco.drawer

import android.util.Log
import com.example.domain.ar.aruco.model.Line
import com.example.domain.ar.base.CrossPointsNeckCellGenerator
import org.opencv.core.Mat
import org.opencv.core.Point
import kotlin.math.pow

class DefaultNeckCellGenerator(
    private val countOfStrings: Int = 6,
    private val countOfFrets: Int = 12
) : CrossPointsNeckCellGenerator {

    override fun findCross(frame: Mat): List<Point> {
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

    override fun generateFretLines(frame: Mat): List<Line> {
        val lines = mutableListOf<Line>()
        val lengthOfNeck = frame.width()
        val coefficient = 2.0.pow(countOfFrets / 12.toDouble())
        val scale = lengthOfNeck / (1 - 1 / coefficient)
        (1..countOfFrets).onEach { fretIndex ->
            val ln = scale / 2.0.pow(fretIndex / 12.toDouble())
            val x = scale - ln
            lines.add(
                Line(
                    Point(lengthOfNeck - x, 0.0),
                    Point(lengthOfNeck - x, frame.height().toDouble())
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
        (0 until countOfStrings).onEach {
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