package com.example.visible_guitar.mapper

import com.example.domain.mapper.Mapper
import com.example.domain.model.ChordEntity
import com.example.visible_guitar.model.Chord
import com.example.visible_guitar.model.Instrument
import com.example.visible_guitar.model.Note
import javax.inject.Inject

class ChordMapper @Inject constructor() : Mapper<ChordEntity, Chord> {
    override fun from(type: ChordEntity): Chord {
        val instrument = Instrument(
            type.id,
            type.name
        )
        return Chord(
            type.id,
            instrument,
            type.name,
            type.notes.map {
                Note(it.id, it.name, it.string_number, it.fret_number)
            }
        )
    }
}