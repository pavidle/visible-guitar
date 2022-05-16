package com.example.data.mapper

import com.example.data.utils.createChordDTO
import com.example.domain.model.ChordEntity
import com.example.domain.model.InstrumentEntity
import com.example.domain.model.NoteEntity
import org.junit.Assert.*
import org.junit.Test

class ChordDTOMapperTest {

    private val chordDTOMapper = ChordDTOMapper()

    @Test
    fun `when mapping chord dto to entity then the correct data is returned`() {
        val chordDTO = createChordDTO()
        val expected = ChordEntity(
            id = 1,
            instrument = InstrumentEntity(
                id = 1,
                name = "Guitar"
            ),
            name = "A major",
            notes = listOf(
                NoteEntity(
                    id = 1,
                    name = "A",
                    stringNumber = 1,
                    fretNumber = 1
                )
            )
        )
        val actual = chordDTOMapper.convert(chordDTO)
        assertEquals(expected, actual)
    }
}