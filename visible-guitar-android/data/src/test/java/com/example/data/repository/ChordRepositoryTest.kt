package com.example.data.repository

import com.example.data.mapper.ChordDTOMapper
import com.example.data.remote.ChordApi
import com.example.data.utils.createChordDTO
import com.example.domain.repository.ChordRepository
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.stub

class ChordRepositoryTest {

    private val chordApi = mock<ChordApi>()
    private val chordDTOMapper = mock<ChordDTOMapper>()
    private lateinit var chordRepository: ChordRepository

    @Test
    fun setUp() {
        chordRepository = ChordRepositoryImpl(chordApi, chordDTOMapper)
    }

    @Test
    fun `when request all then correct data is returned`() = runBlocking {
        val chordsDTO = listOf(
            createChordDTO(id = 5),
            createChordDTO(id = 2, name = "B minor"),
            createChordDTO(id = 3)
        )
        chordApi.stub {
            onBlocking { getChords() } doReturn chordsDTO
        }
        val response = chordApi.getChords()
        assertEquals(response, chordsDTO)
    }

    @Test
    fun `when request by id then correct data is returned`() = runBlocking {
        val chordsDTO = listOf(
            createChordDTO(id = 1, name = "C minor"),
            createChordDTO(id = 2),
            createChordDTO(id = 15)
        )
        val firstChord = chordsDTO[1]
        chordApi.stub {
            onBlocking { getChordById(1) } doReturn firstChord
        }
        val response = chordApi.getChordById(1)
        assertEquals(response, firstChord)
    }
}