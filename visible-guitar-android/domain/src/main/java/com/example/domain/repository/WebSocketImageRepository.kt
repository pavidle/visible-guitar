package com.example.domain.repository

import com.example.domain.common.Resource
import com.example.domain.model.ImageResponseEntity
import com.example.domain.model.SubscribeDataEntity
import kotlinx.coroutines.flow.Flow

interface WebSocketImageRepository {
    suspend fun subscribe(data: SubscribeDataEntity)
    suspend fun receive(): Flow<ImageResponseEntity>
}