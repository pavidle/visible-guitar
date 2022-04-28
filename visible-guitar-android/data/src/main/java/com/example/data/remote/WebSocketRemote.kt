package com.example.data.remote

import com.example.data.model.ReceiveDataDTO
import com.example.data.model.SubscribeDataDTO
import com.example.data.model.events.WebSocketEvent
import com.example.domain.model.ImageResponseEntity
import kotlinx.coroutines.flow.Flow

interface WebSocketRemote {
    fun observeEvent(): Flow<WebSocketEvent>
    fun subscribe(data: SubscribeDataDTO)
    fun observe(): Flow<ReceiveDataDTO>
}