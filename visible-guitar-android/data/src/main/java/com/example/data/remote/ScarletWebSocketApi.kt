package com.example.data.remote

import com.example.data.model.ReceiveDataDTO
import com.example.data.model.SubscribeDataDTO
import com.example.domain.model.ImageResponseEntity
import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import kotlinx.coroutines.flow.Flow


interface ScarletWebSocketApi {
    @get:Receive
    val events: Flow<WebSocket.Event>

    @Send
    fun subscribe(data: SubscribeDataDTO)

    @get:Receive
    val receiveData: Flow<ReceiveDataDTO>

}