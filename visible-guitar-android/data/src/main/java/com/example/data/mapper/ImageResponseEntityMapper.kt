package com.example.data.mapper

import android.util.Log
import com.example.data.model.ReceiveDataDTO
import com.example.data.model.events.WebSocketEvent
import com.example.domain.model.ImageResponseEntity
import javax.inject.Inject

class ImageResponseEntityMapper @Inject constructor() {
    fun convert(
        type: WebSocketEvent,
        data: ReceiveDataDTO
    ): ImageResponseEntity = when(type) {
        is WebSocketEvent.Received, WebSocketEvent.ConnectionOpened -> {
            ImageResponseEntity.Success(data.base64)
        }
        is WebSocketEvent.ConnectionClosed -> {
            Log.e("CLOSED", "CONNECTION CLOSED")
            ImageResponseEntity.Closed(data.base64, type.reason)
        }
        is WebSocketEvent.ConnectionClosing -> {
            Log.e("CLOSING", "CONNECTION CLOSING")
            ImageResponseEntity.Closed(data.base64, type.reason)
            }
        is WebSocketEvent.ConnectionFailed -> {
            Log.e("FAILED", "CONNECTION FAILED")
            ImageResponseEntity.Error(data.base64, type.error.message)
            }
    }
}