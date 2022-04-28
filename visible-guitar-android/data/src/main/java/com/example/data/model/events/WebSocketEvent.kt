package com.example.data.model.events

sealed interface WebSocketEvent {
    object ConnectionOpened: WebSocketEvent
    object Received: WebSocketEvent
    data class ConnectionClosing(val reason: String): WebSocketEvent
    data class ConnectionClosed(val reason: String): WebSocketEvent
    data class ConnectionFailed(val error: Throwable): WebSocketEvent
}