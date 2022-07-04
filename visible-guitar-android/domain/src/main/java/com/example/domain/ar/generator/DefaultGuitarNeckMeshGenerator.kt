package com.example.domain.ar.generator

import com.example.domain.ar.core.cache.Cache
import com.example.domain.ar.core.cache.DefaultCache
import com.example.domain.ar.model.Line
import org.opencv.core.Point
import org.opencv.core.Size
import kotlin.math.pow

internal class DefaultGuitarNeckMeshGenerator(
    private val cache: Cache<String, Pair<List<Line>, List<Line>>>,
    private val countOfStrings: Int,
    private val countOfFrets: Int
) : GuitarNeckMeshGenerator {

    private companion object {
        private fun calculateCoefficient(index: Int): Double = 2.0.pow(index.toDouble() / 7.0)
    }

    private fun generateStringLines(size: Size): List<Line> {
        val height = size.height
        val width = size.width
        val lines = mutableListOf<Line>()
        val heightBetweenString = height / countOfStrings
        val heightBetweenStringHalf = heightBetweenString / 2
        var yPosition = height
        for (stringIndex in 0 until countOfStrings + 1) {
            // distance between begin of neck and lasts strings is smaller
            if (stringIndex == 0) {
                yPosition -= heightBetweenStringHalf
                lines.add(
                    Line(
                        Point(0.0, yPosition),
                        Point(width, yPosition)
                    )
                )
                continue
            }
            yPosition -= heightBetweenString
            lines.add(
                Line(
                    Point(0.0, yPosition),
                    Point(width, yPosition)
                )
            )
        }
        return lines
    }

    private fun generateFretLines(size: Size): List<Line> {
        val lines = mutableListOf<Line>()
        val lengthOfNeck = size.width
        val scale = lengthOfNeck / (1.0 - 1.0 / calculateCoefficient(1).pow(countOfFrets.toDouble()))

        (0..countOfFrets).onEach { fretIndex ->
            val ln = scale / calculateCoefficient(fretIndex)
//            val nextLn = scale / 2.0.pow((fretIndex + 1) / 12.toDouble())
            val r = lengthOfNeck - scale + ln
//            val nextX = scale - nextLn
//            val half = (nextX - x) / 2
            lines.add(
                Line(
                    Point(r, 0.0),
                    Point(r, size.height)
                )
            )
        }
        return lines
    }

    override fun <T> generate(input: T): Pair<List<Line>, List<Line>> {
        val resultCache = cache.get("stringsAndFrets")
        if (resultCache != null) {
            return resultCache
        }
        val size = input as Size
        val strings = generateStringLines(size)
        val frets = generateFretLines(size)
        val result = Pair(strings, frets)
        cache.save("stringsAndFrets", result)
        return result
    }

    class Factory : GuitarNeckMeshGenerator.Factory {
        override fun create(): GuitarNeckMeshGenerator {
            val cache = DefaultCache<String, Pair<List<Line>, List<Line>>>()
            return DefaultGuitarNeckMeshGenerator(cache, 4, 12)
        }
    }
}