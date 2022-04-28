package com.example.data.mapper

import com.example.data.model.events.WebSocketEvent
import com.example.domain.mapper.Mapper
import com.tinder.scarlet.WebSocket
import javax.inject.Inject

class WebSocketEventMapper @Inject constructor()
    : Mapper<WebSocket.Event, WebSocketEvent> {

    override fun convert(type: WebSocket.Event): WebSocketEvent = when(type) {
        is WebSocket.Event.OnConnectionOpened<*> -> WebSocketEvent.ConnectionOpened
        is WebSocket.Event.OnMessageReceived -> WebSocketEvent.Received
        is WebSocket.Event.OnConnectionClosing -> WebSocketEvent.ConnectionClosing(reason = type.shutdownReason.reason)
        is WebSocket.Event.OnConnectionClosed -> WebSocketEvent.ConnectionClosed(reason = type.shutdownReason.reason)
        is WebSocket.Event.OnConnectionFailed -> WebSocketEvent.ConnectionFailed(error = type.throwable)
    }
}