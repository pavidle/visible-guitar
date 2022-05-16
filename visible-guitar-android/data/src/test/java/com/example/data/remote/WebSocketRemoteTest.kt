package com.example.data.remote

import app.cash.turbine.test
import com.example.data.model.events.WebSocketEvent
import com.example.data.utils.createReceiveDataDTO
import com.example.data.utils.createSubscribeDataDTO
import com.example.domain.mapper.Mapper
import com.tinder.scarlet.Message
import com.tinder.scarlet.WebSocket
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.stub
import org.mockito.kotlin.verify


class WebSocketRemoteTest {

    private val scarletWebSocketApi = mock<ScarletWebSocketApi>()
    private val webSocketEventMapper = mock<Mapper<WebSocket.Event, WebSocketEvent>>()
    private lateinit var webSocketRemote: WebSocketRemote

    @Before
    fun setUp() {
        webSocketRemote = WebSocketRemoteImpl(
            scarletWebSocketApi,
            webSocketEventMapper
        )
    }

    @Test
    fun `when events of socket are requested then the correct data is returned`() = runBlocking {
        webSocketEventMapper.stub {
            on {
                convert(
                    type = WebSocket.Event.OnMessageReceived(
                        message = Message.Text(value = "Important text")
                    )
                )
            } doReturn WebSocketEvent.Received
            on {
                convert(
                    type = WebSocket.Event.OnMessageReceived(
                        message = Message.Text(value = "Hello everyone!!!")
                    )
                )
            } doReturn WebSocketEvent.Received
        }

       scarletWebSocketApi.stub {
           on { events } doReturn flowOf(
               WebSocket.Event.OnMessageReceived(
                   message = Message.Text(value = "Important text")
               ),
               WebSocket.Event.OnMessageReceived(
                   message = Message.Text(value = "Hello everyone!!!")
               )
           )
       }

        val events = webSocketRemote.observeEvent()
        events.test {
            assertEquals(WebSocketEvent.Received, awaitItem())
            assertEquals(WebSocketEvent.Received, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `when data from socket is requested then the correct data is returned`() = runBlocking {
        scarletWebSocketApi.stub {
            on { receiveData } doReturn flowOf(
                createReceiveDataDTO(),
                createReceiveDataDTO(base64 = "base64_other")
            )
        }

        val receiveData = webSocketRemote.observe()

        receiveData.test {
            assertEquals(createReceiveDataDTO(), awaitItem())
            assertEquals(createReceiveDataDTO(base64 = "base64_other"), awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `when subscribe is called then the correct data is subscribed to socket`() {
        val subscribeDataDTO = createSubscribeDataDTO()
        webSocketRemote.subscribe(subscribeDataDTO)
        verify(scarletWebSocketApi).subscribe(subscribeDataDTO)
    }
}