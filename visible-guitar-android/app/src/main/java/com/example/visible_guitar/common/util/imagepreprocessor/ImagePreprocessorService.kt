package com.example.visible_guitar.common.util.imagepreprocessor

import android.util.Log
import com.example.visible_guitar.common.util.imagepreprocessor.base.ImageAdapter
import org.opencv.core.Mat
import org.opencv.core.MatOfByte


class ImagePreprocessorService(
    private val imageAdapterFactory: ImageAdapter.Factory,
    private val processImageHandlerEngine: ImageProcessHandlerEngine
)  {

    fun <T> processFrom(mat: Mat) : T {
        val adapter = imageAdapterFactory.create<T>()
        val processedImage = processImageHandlerEngine.processAll(mat)
        return adapter.adaptFrom(processedImage)
    }

    fun <T> processTo(string: T) : Mat {
        val adapter = imageAdapterFactory.create<T>()
        return adapter.adaptTo(string)
    }

    class Builder {

        companion object {
            private const val DEFAULT_COMPRESSION_RATIO = 1.0
            private const val DEFAULT_JPEG_QUALITY = 50
            private val DEFAULT_IMAGE_ADAPTER = Base64ImageAdapter.Factory()
        }

        private var compressionRatio: Double = DEFAULT_COMPRESSION_RATIO
        private var imageQuality: Int = DEFAULT_JPEG_QUALITY
        private var imageAdapterFactory: ImageAdapter.Factory = DEFAULT_IMAGE_ADAPTER


        fun setCompressionRatio(ratio: Double) = apply {
            compressionRatio = ratio
        }

        fun setImageQuality(quality: Int) = apply {
            imageQuality = quality
        }

        fun setImageAdapterFactory(factory: ImageAdapter.Factory) = apply {
            imageAdapterFactory = factory
        }

        fun build(): ImagePreprocessorService =
            ImagePreprocessorService(
                imageAdapterFactory,
                ImageProcessHandlerEngine(
                    listOf(
                        CompressionProcessImageHandler(compressionRatio),
                        ChangeQualityProcessImageHandler(imageQuality)
                    )
                )
            )
    }
}
