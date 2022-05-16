package com.example.data.utils

import com.example.domain.model.ChordEntity
import com.example.domain.model.InstrumentEntity
import com.example.domain.model.NoteEntity
import com.example.domain.model.SubscribeDataEntity


fun createInstrumentEntity(
    id: Int = 1,
    name: String = "Guitar"
) =
    InstrumentEntity(
    id = id,
    name = name
)


fun createNoteEntity(
    id: Int = 1,
    name: String = "A",
    stringNumber: Int = 1,
    fretNumber: Int = 1
) =
    NoteEntity(
        id = id,
        name = name,
        stringNumber = stringNumber,
        fretNumber = fretNumber
    )

fun createChordEntity(
    id: Int = 1,
    instrumentDTO: InstrumentEntity = createInstrumentEntity(),
    name: String = "A major",
    ) = ChordEntity(
    id = id,
    instrument = instrumentDTO,
    name = name,
    notes = listOf(createNoteEntity())
)
