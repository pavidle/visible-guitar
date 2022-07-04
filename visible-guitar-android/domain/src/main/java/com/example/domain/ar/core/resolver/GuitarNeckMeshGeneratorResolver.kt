package com.example.domain.ar.core.resolver

import android.util.Log
import com.example.domain.ar.generator.GuitarNeckMeshGenerator

internal class GuitarNeckMeshGeneratorResolver(
    private val guitarNeckMeshGeneratorFactories: List<GuitarNeckMeshGenerator.Factory>
) {

    fun resolve(): GuitarNeckMeshGenerator
    = getCorrectNeckMeshGenerator()

    private fun getCorrectNeckMeshGenerator(): GuitarNeckMeshGenerator {
        for (guitarNeckMeshGeneratorFactory in guitarNeckMeshGeneratorFactories) {
            try {
                return guitarNeckMeshGeneratorFactory.create()
            } catch (e: Throwable) {
                Log.e("GeneratorError", "Генератор сетки не сработал.")
                continue
            }
        }
        throw IllegalStateException("Ни один из генераторов сетки грифа гитары не сработал.")
    }

}