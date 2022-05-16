package com.example.domain.interactor

import app.cash.turbine.test
import com.example.data.utils.createChordEntity
import com.example.domain.common.Resource
import com.example.domain.model.ChordEntity
import com.example.domain.repository.ChordRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.stub

class GetChordByIdUseCaseTest {

    private val chordRepository = mock<ChordRepository>()
    private lateinit var getChordByIdUseCase: UseCase<Int, Flow<Resource<ChordEntity>>>

    @Before
    fun setUp() {
        getChordByIdUseCase = GetChordByIdUseCase(chordRepository)
    }

    @Test
    fun `when response is requested then the correct data is returned`() = runBlocking {
        val chordEntity = createChordEntity(id = 1)
        chordRepository.stub {
            onBlocking { getChordById(1) } doReturn flowOf(
                Resource.Success(data = chordEntity)
            )
        }

        var actual: ChordEntity? = null
        getChordByIdUseCase(1).collect { resource ->
            resource.data?.let { data -> actual = data}
        }

        assertEquals(chordEntity, actual)
    }

    @Test
    fun `when data state is requested then the correct data is returned`() = runBlocking {
        val chordEntity = createChordEntity()
        chordRepository.stub {
            onBlocking { getChordById(1) } doReturn flowOf(
                Resource.Success(data = chordEntity),
                Resource.Error(message = "Some error", data = chordEntity)
            )
        }
        getChordByIdUseCase(1).test {
            Assert.assertEquals(Resource.Success(data = chordEntity).data, awaitItem().data)
            Assert.assertEquals(
                Resource.Error(message = "Some error", data = chordEntity).message,
                awaitItem().message
            )
            awaitComplete()
        }
    }

}