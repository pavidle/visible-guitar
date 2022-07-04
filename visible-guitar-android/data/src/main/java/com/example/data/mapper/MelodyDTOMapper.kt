package com.example.data.mapper

import com.example.data.model.ChordDTO
import com.example.data.model.MelodyDTO
import com.example.domain.mapper.Mapper
import com.example.domain.model.*
import javax.inject.Inject

class MelodyDTOMapper @Inject constructor() : Mapper<MelodyDTO, MelodyEntity> {
    override fun convert(type: MelodyDTO): MelodyEntity {
        return MelodyEntity(
            type.id,
            type.name,
            AuthorEntity(type.author.id, type.author.name),
            type.chords.map { ChordEntity(
                    it.id,
                    InstrumentEntity(it.instrument.id, it.instrument.name),
                    it.name,
                    it.notes.map { note -> NoteEntity(note.id, note.name, note.stringNumber, note.fretNumber) }
                )
            }
        )
    }

}