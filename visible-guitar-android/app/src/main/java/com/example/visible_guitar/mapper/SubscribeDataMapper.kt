package com.example.visible_guitar.mapper

import com.example.domain.mapper.Mapper
import com.example.domain.model.SubscribeDataEntity
import com.example.visible_guitar.common.util.Base64Service
import com.example.visible_guitar.common.util.imagepreprocessor.ImagePreprocessorService
import com.example.visible_guitar.model.SubscribeData
import org.opencv.core.CvType
import org.opencv.core.MatOfByte
import javax.inject.Inject

class SubscribeDataMapper @Inject constructor(
    private val imagePreprocessorService: ImagePreprocessorService
) : Mapper<SubscribeData, SubscribeDataEntity> {
    override fun convert(type: SubscribeData): SubscribeDataEntity =
        SubscribeDataEntity(
            imagePreprocessorService.processFrom(type.mat)
        )
}