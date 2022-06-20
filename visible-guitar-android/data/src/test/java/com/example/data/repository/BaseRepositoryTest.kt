package com.example.data.repository

import app.cash.turbine.test
import com.example.data.model.ChordDTO
import com.example.data.repository.base.BaseRepository
import com.example.data.utils.createChordDTO
import com.example.domain.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class BaseRepositoryTest {

    private class FakeRepository : BaseRepository() {
        public override fun <T: Any> makeRequest(request: suspend () -> T): Flow<Resource<T>> {
            return super.makeRequest(request)
        }
    }

    private val fakeRepository = FakeRepository()

    @Test
    fun `when make request via api success then response is received`() = runBlocking {
        val chordsDTO = listOf(
            createChordDTO(id = 1, name = "C minor"),
            createChordDTO(id = 2),
            createChordDTO(id = 15)
        )
        val request = fakeRepository.makeRequest { chordsDTO }
        request.test {
           assertEquals(awaitItem().data, Resource.Loading<ChordDTO>().data)
            assertEquals(awaitItem().data, Resource.Success(chordsDTO).data)
            awaitComplete()
        }
    }

    @Test
    fun `when make request via api http error then response is received`() = runBlocking {
        val request = fakeRepository.makeRequest {
            throw HttpException(
                Response.error<Any>(
                    500, "".toResponseBody("plain/text".toMediaTypeOrNull())
                )
            )
        }
        request.test {
            assertEquals(awaitItem().data, Resource.Loading<ChordDTO>().data)
            assertSame(awaitItem().message, Resource.Error(
                message = "Произошла непредвиденная ошибка",
                data = null).message
            )
            awaitComplete()
        }
    }

    @Test
    fun `when make request via api io error then response is received`() = runBlocking {
        val request = fakeRepository.makeRequest {
            throw IOException()
        }
        request.test {
            assertEquals(awaitItem().data, Resource.Loading<ChordDTO>().data)
            assertSame(awaitItem().message, Resource.Error(
                message = "Отсутствует подключение к Интернету",
                data = null).message
            )
            awaitComplete()
        }
    }
}