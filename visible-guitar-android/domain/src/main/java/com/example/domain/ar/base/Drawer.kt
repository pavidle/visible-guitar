package com.example.domain.ar.base

import com.example.domain.model.ChordEntity
import org.opencv.core.Mat

interface Drawer {
    fun draw(frame: Mat, chordEntity: ChordEntity): Mat
}