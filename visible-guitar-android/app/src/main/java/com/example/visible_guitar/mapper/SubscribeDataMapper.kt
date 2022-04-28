package com.example.visible_guitar.mapper

import com.example.domain.mapper.Mapper
import com.example.domain.model.SubscribeDataEntity
import com.example.visible_guitar.common.util.Base64Service
import com.example.visible_guitar.model.SubscribeData
import javax.inject.Inject

class SubscribeDataMapper @Inject constructor(
    private val base64service: Base64Service
) : Mapper<SubscribeData, SubscribeDataEntity> {

    override fun convert(type: SubscribeData): SubscribeDataEntity =
        SubscribeDataEntity(base64service.encode(type.mat))
}