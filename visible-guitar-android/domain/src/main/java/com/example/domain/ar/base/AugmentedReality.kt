package com.example.domain.ar.base

import com.example.domain.ar.aruco.model.FrameEntity
import org.opencv.core.Mat

interface AugmentedReality {

    fun augment(frameEntity: FrameEntity): Mat

    interface Builder {
        fun build(): AugmentedReality
    }
}