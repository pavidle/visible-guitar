package com.example.domain.ar

import com.example.domain.ar.base.Drawer
import com.example.domain.ar.generator.NeckMesh
import org.opencv.core.Mat

abstract class NeckDrawerWrapper(
    private val wrappedDrawer: Drawer
) : Drawer {

    override fun draw(): Mat {
        return wrappedDrawer.draw()
    }
}