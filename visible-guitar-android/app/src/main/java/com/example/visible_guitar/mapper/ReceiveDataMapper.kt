package com.example.visible_guitar.mapper

import com.example.domain.mapper.Mapper
import com.example.domain.model.ImageResponseEntity
import com.example.visible_guitar.common.util.Base64Service
import com.example.visible_guitar.model.ReceiveData
import javax.inject.Inject

class ReceiveDataMapper @Inject constructor(
    private val base64Service: Base64Service
) : Mapper<ImageResponseEntity, ReceiveData> {
    override fun convert(type: ImageResponseEntity): ReceiveData = when(type) {
        is ImageResponseEntity.Success -> ReceiveData(base64Service.decode(type.base64))
        is ImageResponseEntity.Inactive -> ReceiveData(base64Service.decode(type.base64), type.message)
    }
}