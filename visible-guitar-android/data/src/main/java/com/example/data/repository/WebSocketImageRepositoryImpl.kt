package com.example.data.repository

import android.util.Log
import com.example.data.common.AsyncDispatcher
import com.example.data.mapper.ImageResponseEntityMapper
import com.example.data.model.SubscribeDataDTO
import com.example.data.model.events.WebSocketEvent
import com.example.data.remote.WebSocketRemote
import com.example.domain.model.ImageResponseEntity
import com.example.domain.repository.WebSocketImageRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WebSocketImageRepositoryImpl @Inject constructor(
    private val webSocketRemote: WebSocketRemote,
    private val imageResponseEntityMapper: ImageResponseEntityMapper,
    private val asyncDispatcher: AsyncDispatcher
) : WebSocketImageRepository {

    override suspend fun subscribe(data: String) {
        withContext(asyncDispatcher.io) {
            webSocketRemote.subscribe(data = SubscribeDataDTO(data))
        }
    }

    override suspend fun receive(): Flow<ImageResponseEntity>
    {
        return combine(
            webSocketRemote.observe().distinctUntilChanged(),
            webSocketRemote.observeEvent().distinctUntilChanged()
        ) { model, event ->
            return@combine imageResponseEntityMapper.convert(event, model)
        }
    }
}