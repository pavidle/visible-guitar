package com.example.data.utils.com.example.domain.interactor

import app.cash.turbine.test
import com.example.data.utils.createChordEntity
import com.example.domain.common.Resource
import com.example.domain.interactor.GetChordsUseCase
import com.example.domain.interactor.UseCase
import com.example.domain.model.ChordEntity
import com.example.domain.repository.ChordRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.stub

class GetChordsUseCaseTest {

    private val chordRepository = mock<ChordRepository>()
    private lateinit var getChordsUseCase: UseCase<Unit, Flow<Resource<List<ChordEntity>>>>

    @Before
    fun setUp() {
        getChordsUseCase = GetChordsUseCase(chordRepository)
    }

    @Test
    fun `when response is requested then the correct data is returned`() = runBlocking {
        val chordEntities = listOf(
            createChordEntity(id = 2),
            createChordEntity(id = 4, name = "D major")
        )
        chordRepository.stub {
            onBlocking { getChords() } doReturn flowOf(
                Resource.Success(data = chordEntities)
            )
        }
        val actual = mutableListOf<List<ChordEntity>>()
        getChordsUseCase(Unit).collect { resource ->
           resource.data?.let { data -> actual.add(data) }
        }

        assertEquals(listOf(chordEntities), actual)
    }

    @Test
    fun `when data state is requested then the correct data is returned`() = runBlocking {
        val chordEntities = listOf(
            createChordEntity(),
            createChordEntity(id = 2, name = "C minor")
        )
        chordRepository.stub {
            onBlocking { getChords() } doReturn flowOf(
                Resource.Success(data = chordEntities),
                Resource.Error(message = "Some error", data = chordEntities)
            )
        }

        getChordsUseCase(Unit).test {
            assertEquals(Resource.Success(data = chordEntities).data, awaitItem().data)
            assertEquals(
                Resource.Error(message = "Some error", data = chordEntities).message,
                awaitItem().message
            )

            awaitComplete()
        }
    }
}