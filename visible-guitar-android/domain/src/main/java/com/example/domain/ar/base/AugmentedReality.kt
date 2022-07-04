package com.example.domain.ar.base

import com.example.domain.ar.recognizer.aruco.model.FrameEntity
import com.example.domain.model.ChordEntity
import org.opencv.core.Mat

interface AugmentedReality {

    fun augment(frameEntity: FrameEntity): Mat

    interface Builder {
        fun build(): AugmentedReality
    }
}