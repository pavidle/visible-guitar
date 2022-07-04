package com.example.domain.ar.generator

import com.example.domain.ar.NeckLabelsDrawer
import com.example.domain.ar.core.resolver.GuitarNeckMeshGeneratorResolver
import com.example.domain.ar.model.Line
import org.opencv.core.Point
import org.opencv.core.Size

class NeckMesh private constructor(
    val strings: List<Line>,
    val frets: List<Line>
) {
    fun findCross(): List<Point> {
        val points = mutableListOf<Point>()
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

    fun findCross(string: Int, fret: Int): Point {
        val yString = strings[string].first.y
        val xFret = frets[fret].first.x
        return Point(xFret, yString)
    }

    class Builder {

        private companion object {
            val DEFAULT_SIZE = Size(500.0, 200.0)
        }

        private val guitarNeckMeshGeneratorFactories = mutableListOf<GuitarNeckMeshGenerator.Factory>()
        private var size = DEFAULT_SIZE

        fun addGuitarNeckGeneratorFactory(factory: GuitarNeckMeshGenerator.Factory) = apply {
            guitarNeckMeshGeneratorFactories.add(factory)
        }

        fun setSizeOfNeck(size: Size) = apply {
            this.size = size
        }

        private fun createNeckMeshGeneratorResolver() = GuitarNeckMeshGeneratorResolver(
            guitarNeckMeshGeneratorFactories.apply { add(DefaultGuitarNeckMeshGenerator.Factory()) }
        )

        fun build(): NeckMesh {
            val neckMeshGeneratorResolver = createNeckMeshGeneratorResolver()
            val generator = neckMeshGeneratorResolver.resolve()
            val pair = generator.generate(size)
            return NeckMesh(pair.first, pair.second)
        }

    }
}
