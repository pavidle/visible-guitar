package com.example.domain.ar.generator

import com.example.domain.ar.model.Line

interface GuitarNeckMeshGenerator {

    fun <T> generate(input: T): Pair<List<Line>, List<Line>>

    interface Factory {
        fun create(): GuitarNeckMeshGenerator
    }
}