package com.example.data.mapper

import com.example.data.model.events.WebSocketEvent
import com.example.data.utils.createReceiveDataDTO
import com.example.domain.model.ImageResponseEntity
import junitparams.JUnitParamsRunner
import junitparams.Parameters
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(JUnitParamsRunner::class)
class ImageResponseEntityMapperTest {

    private val imageResponseEntityMapper = ImageResponseEntityMapper()

    fun getArrayOfInputAndOutputData() = arrayOf(
        arrayOf(
            WebSocketEvent.ConnectionOpened,
            ImageResponseEntity.Success(base64 = "base64")
        ),
        arrayOf(
            WebSocketEvent.Received,
            ImageResponseEntity.Success(base64 = "base64")

        ),
        arrayOf(
            WebSocketEvent.ConnectionClosed(reason = "Closed"),
            ImageResponseEntity.Closed(base64 = "base64", message = "Closed")

        ),
        arrayOf(
            WebSocketEvent.ConnectionClosing(reason = "Closing"),
            ImageResponseEntity.Closed(base64 = "base64", message = "Closing")
        ),
        arrayOf(
            WebSocketEvent.ConnectionFailed(error = Throwable("Closing")),
            ImageResponseEntity.Error(base64 = "base64", message = "Closing")
        )
    )


    @Test
    @Parameters(method = "getArrayOfInputAndOutputData")
    fun `when mapping to response entity then the correct data is returned`(
        event: WebSocketEvent,
        expected: ImageResponseEntity
    ) {
        val receiveDTO = createReceiveDataDTO()
        val actual = imageResponseEntityMapper.convert(event, receiveDTO)
        assertEquals(expected, actual)

    }

    @Test
    fun `when mapping to response entity and socket event is connection failed then the correct data is returned`() {
        val actual = imageResponseEntityMapper.convert(
            WebSocketEvent.ConnectionFailed(error = Throwable("Some error")),
            createReceiveDataDTO()
        ) as ImageResponseEntity.Inactive

        val expected = ImageResponseEntity.Error(base64 = "base64", message = "Some error")
        assertEquals(expected.message, actual.message)
    }
}