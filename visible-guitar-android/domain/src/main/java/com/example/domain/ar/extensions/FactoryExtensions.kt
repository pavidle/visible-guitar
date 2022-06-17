package com.example.domain.ar.extensions

import com.example.domain.ar.aruco.ArucoRecognizer
import com.example.domain.ar.base.Recognizer

enum class RecognizerType {
    ARUCO
}

//fun createRecognizer(type: RecognizerType): Recognizer {
//    return when (type) {
//        RecognizerType.ARUCO -> ArucoRecognizer.Builder()
//    }
//}