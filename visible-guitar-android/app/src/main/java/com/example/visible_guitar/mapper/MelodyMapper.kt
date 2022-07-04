package com.example.visible_guitar.mapper

import com.example.domain.mapper.Mapper
import com.example.domain.model.*
import com.example.visible_guitar.model.*
import javax.inject.Inject

class MelodyMapper @Inject constructor() : Mapper<MelodyEntity, Melody> {
    override fun convert(type: MelodyEntity): Melody {
        return Melody(
            type.id!!,
            type.name,
            Author(type.author.id, type.author.name),
            type.chords.map { Chord(
                it.id,
                Instrument(it.instrument.id, it.instrument.name),
                it.name,
                it.notes.map { note -> Note(note.id, note.name, note.stringNumber, note.fretNumber) }
            )
            }
        )
    }
}