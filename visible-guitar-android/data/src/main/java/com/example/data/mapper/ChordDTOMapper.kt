package com.example.data.mapper

import com.example.data.model.ChordDTO
import com.example.domain.mapper.Mapper
import com.example.domain.model.ChordEntity
import com.example.domain.model.InstrumentEntity
import com.example.domain.model.NoteEntity
import javax.inject.Inject

class ChordDTOMapper @Inject constructor() : Mapper<ChordDTO, ChordEntity> {
    override fun convert(type: ChordDTO): ChordEntity {
        val instrument = InstrumentEntity(
            type.instrument.id,
            type.instrument.name
        )
        return ChordEntity(
            type.id,
            instrument,
            type.name,
            type.notes.map {
                NoteEntity(it.id, it.name, it.string_number, it.fret_number)
            }
        )
    }
}