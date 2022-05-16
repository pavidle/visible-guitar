package com.example.domain.interactor

import com.example.domain.model.SubscribeDataEntity
import com.example.domain.repository.WebSocketImageRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.stub
import org.mockito.kotlin.verify

class SendImageUseCaseTest {

    private val webSocketImageRepository = mock<WebSocketImageRepository>()
    private lateinit var sendImageUseCase: UseCase<SubscribeDataEntity, Unit>

    @Before
    fun setUp() {
        sendImageUseCase = SendImageUseCase(webSocketImageRepository)
    }

    @Test
    fun `when use case is called then the repository is subscribed too`() = runBlocking {
        val subscribeDataEntity = SubscribeDataEntity(mock())
        sendImageUseCase(subscribeDataEntity)
        verify(webSocketImageRepository).subscribe(subscribeDataEntity)
    }
}