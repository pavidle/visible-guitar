package com.example.domain.interactor

import app.cash.turbine.test
import com.example.domain.model.ImageResponseEntity
import com.example.domain.repository.WebSocketImageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.stub

class ReceiveImageUseCaseTest {

    private val webSocketImageRepository = mock<WebSocketImageRepository>()
    private lateinit var receiveImageUseCase: UseCase<Unit, Flow<ImageResponseEntity>>

    @Before
    fun setUp() {
        receiveImageUseCase = ReceiveImageUseCase(webSocketImageRepository)
    }

    @Test
    fun `when response is requested then the correct data is returned`() = runBlocking {
        webSocketImageRepository.stub {
            onBlocking { receive() } doReturn flowOf(
                ImageResponseEntity.Error(base64 = "base64", message = "Some error"),
                ImageResponseEntity.Success(base64 = "base64")
            )
        }
        val responseEntities = mutableListOf<ImageResponseEntity>()
        val actual = listOf(
            ImageResponseEntity.Error(base64 = "base64", message = "Some error"),
            ImageResponseEntity.Success(base64 = "base64")
        )
        receiveImageUseCase(Unit).collect { responseEntity ->
            responseEntities += responseEntity
        }

        assertEquals(responseEntities, actual)
    }

    @Test
    fun `when data state is requested then the correct data is returned`() = runBlocking {
        webSocketImageRepository.stub {
            onBlocking { receive() } doReturn flowOf(
                ImageResponseEntity.Error(base64 = "base64", message = "Some error"),
                ImageResponseEntity.Success(base64 = "base64")
            )
        }

        receiveImageUseCase(Unit).test {
            assertEquals(
                ImageResponseEntity.Error(base64 = "base64", message = "Some error"), awaitItem()
            )
            assertEquals(ImageResponseEntity.Success(base64 = "base64"), awaitItem())
            awaitComplete()
        }
    }
}