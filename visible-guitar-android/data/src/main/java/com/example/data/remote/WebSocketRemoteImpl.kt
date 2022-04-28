package com.example.data.remote

import android.util.Log
import com.example.data.model.ReceiveDataDTO
import com.example.data.model.SubscribeDataDTO
import com.example.data.model.events.WebSocketEvent
import com.example.domain.mapper.Mapper
import com.example.domain.model.ImageResponseEntity
import com.tinder.scarlet.WebSocket
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WebSocketRemoteImpl @Inject constructor(
    private val scarletWebSocketApi: ScarletWebSocketApi,
    private val webSocketEventMapper: Mapper<WebSocket.Event, WebSocketEvent>
) : WebSocketRemote {
    override fun observeEvent(): Flow<WebSocketEvent> =
        scarletWebSocketApi.events.map(webSocketEventMapper::convert)

    override fun subscribe(data: SubscribeDataDTO) =
        scarletWebSocketApi.subscribe(data)

    override fun observe(): Flow<ReceiveDataDTO> =
        scarletWebSocketApi.receiveData

}