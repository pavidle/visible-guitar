package com.example.data.mapper

import com.example.data.model.ReceiveDataDTO
import com.example.data.model.events.WebSocketEvent
import com.example.domain.model.ImageResponseEntity
import javax.inject.Inject

class ImageResponseEntityMapper @Inject constructor() {
    fun convert(
        event: WebSocketEvent,
        data: ReceiveDataDTO
    ): ImageResponseEntity = when(event) {
        is WebSocketEvent.Received, WebSocketEvent.ConnectionOpened ->
            ImageResponseEntity.Success(data.base64)
        is WebSocketEvent.ConnectionClosed -> ImageResponseEntity.Closed(data.base64, event.reason)
        is WebSocketEvent.ConnectionClosing -> ImageResponseEntity.Closed(data.base64, event.reason)
        is WebSocketEvent.ConnectionFailed -> ImageResponseEntity.Error(data.base64, event.error.message)
    }
}