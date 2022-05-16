package com.example.data.mapper

import com.example.data.common.imagepreprocessor.ImagePreprocessorService
import com.example.data.model.SubscribeDataDTO
import com.example.domain.mapper.Mapper
import com.example.domain.model.SubscribeDataEntity
import javax.inject.Inject

class SubscribeDataDTOMapper @Inject constructor(
    private val imagePreprocessorService: ImagePreprocessorService
) : Mapper<SubscribeDataEntity, SubscribeDataDTO> {
    override fun convert(type: SubscribeDataEntity): SubscribeDataDTO =
        SubscribeDataDTO(imagePreprocessorService.processFrom(type.mat))
}