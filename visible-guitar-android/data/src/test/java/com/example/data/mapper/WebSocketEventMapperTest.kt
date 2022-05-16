package com.example.data.mapper

import com.example.data.model.events.WebSocketEvent
import com.tinder.scarlet.Message
import com.tinder.scarlet.ShutdownReason
import com.tinder.scarlet.WebSocket
import junitparams.JUnitParamsRunner
import junitparams.Parameters
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock


@RunWith(JUnitParamsRunner::class)
class WebSocketEventMapperTest {

    private val webSocketEventMapper = WebSocketEventMapper()

    fun getArrayOfInputAndOutputData() = arrayOf(
        arrayOf(
            WebSocket.Event.OnConnectionOpened(mock()),
            WebSocketEvent.ConnectionOpened
        ),
        arrayOf(
            WebSocket.Event.OnMessageReceived(
                message = Message.Text(value = "Important text"),
            ),
            WebSocketEvent.Received
        ),
        arrayOf(
            WebSocket.Event.OnMessageReceived(
                message = Message.Bytes(value = "Important text".toByteArray())
            ),
            WebSocketEvent.Received
        ),
        arrayOf(
            WebSocket.Event.OnConnectionClosing(
                ShutdownReason(code = 11, reason = "Closing")
            ),
            WebSocketEvent.ConnectionClosing(reason = "Closing")
        ),
        arrayOf(
            WebSocket.Event.OnConnectionClosed(
                ShutdownReason(code = 11, reason = "Closed")
            ),
            WebSocketEvent.ConnectionClosed(reason = "Closed")
        )
    )

    @Test
    @Parameters(method = "getArrayOfInputAndOutputData")
    fun `when socket event is mapped then the correct data is returned`(
        event: WebSocket.Event,
        expected: WebSocketEvent
    ) {
        val actual = webSocketEventMapper.convert(event)
        assertEquals(actual, expected)
    }

    @Test
    fun `when socket event is mapped and connection failed then the correct data is returned`() {
        val actual = webSocketEventMapper.convert(
            WebSocket.Event.OnConnectionFailed(Throwable("Some error"))
        ) as WebSocketEvent.ConnectionFailed
        val expected = WebSocketEvent.ConnectionFailed(error = Throwable("Some error"))
        assertEquals(expected.error.message, actual.error.message)
    }

}