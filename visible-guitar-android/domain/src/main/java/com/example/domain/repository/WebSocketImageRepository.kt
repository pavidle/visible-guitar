package com.example.domain.repository

import com.example.domain.common.Resource
import com.example.domain.model.ImageResponseEntity
import kotlinx.coroutines.flow.Flow

interface WebSocketImageRepository {
    suspend fun subscribe(data: String)
    suspend fun receive(): Flow<ImageResponseEntity>
}