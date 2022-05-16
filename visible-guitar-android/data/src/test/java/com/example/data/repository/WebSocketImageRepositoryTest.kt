package com.example.data.repository

import app.cash.turbine.test
import com.example.data.mapper.ImageResponseEntityMapper
import com.example.data.model.SubscribeDataDTO
import com.example.data.model.events.WebSocketEvent
import com.example.data.remote.WebSocketRemote
import com.example.data.utils.createReceiveDataDTO
import com.example.data.utils.createSubscribeDataDTO
import com.example.domain.mapper.Mapper
import com.example.domain.model.ImageResponseEntity
import com.example.domain.model.SubscribeDataEntity
import com.example.domain.repository.WebSocketImageRepository
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.stub
import org.mockito.kotlin.verify


class WebSocketImageRepositoryTest {

    private val webSocketRemote = mock<WebSocketRemote>()
    private val imageResponseEntityMapper = mock<ImageResponseEntityMapper>()
    private val subscribeDataDTOMapper = mock<Mapper<SubscribeDataEntity, SubscribeDataDTO>>()
    private lateinit var webSocketImageRepository: WebSocketImageRepository

    @Before
    fun setUp() {
        webSocketImageRepository = WebSocketImageRepositoryImpl(
            webSocketRemote,
            imageResponseEntityMapper,
            subscribeDataDTOMapper
        )
    }

    @Test
    fun `when response is received then the correct data is returned`() = runBlocking {
        webSocketRemote.stub {
            on { observe() } doReturn flowOf(
                createReceiveDataDTO()
            )
            on { observeEvent() } doReturn flowOf(
                WebSocketEvent.ConnectionOpened
            )
        }
        imageResponseEntityMapper.stub {
            on {
                convert(
                    event = WebSocketEvent.ConnectionOpened,
                    data = createReceiveDataDTO()
                )
            } doReturn ImageResponseEntity.Success("base64")
        }
        val response = webSocketImageRepository.receive()
        response.test {
            assertEquals(ImageResponseEntity.Success("base64"), awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `when subscribe is called then the data is subscribed to socket correctly`() = runBlocking {

        val subscribeDataEntity = SubscribeDataEntity(mock())
        val subscribeDataDTO = createSubscribeDataDTO()
        subscribeDataDTOMapper.stub {
            on { convert(subscribeDataEntity) } doReturn subscribeDataDTO
        }
        webSocketImageRepository.subscribe(subscribeDataEntity)
        verify(webSocketRemote).subscribe(subscribeDataDTO)
    }
}