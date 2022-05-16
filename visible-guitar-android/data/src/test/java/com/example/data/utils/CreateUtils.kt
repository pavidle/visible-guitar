package com.example.data.utils

import com.example.data.model.*

fun createInstrumentDTO(
    id: Int = 1,
    name: String = "Guitar"
) = InstrumentDTO(
    id = id,
    name = name
)

fun createNoteDTO(
    id: Int = 1,
    name: String = "A",
    stringNumber: Int = 1,
    fretNumber: Int = 1
) =
    NoteDTO(
        id = id,
        name = name,
        stringNumber = stringNumber,
        fretNumber = fretNumber
    )

fun createChordDTO(
    id: Int = 1,
    instrumentDTO: InstrumentDTO = createInstrumentDTO(),
    name: String = "A major",

    ) = ChordDTO(
    id = id,
    instrument = instrumentDTO,
    name = name,
    notes = listOf(createNoteDTO())
)

fun createReceiveDataDTO(
    base64: String = "base64"
) = ReceiveDataDTO(
    base64 = base64
)

fun createSubscribeDataDTO(
    base64: String = "base64"
) = SubscribeDataDTO(
    base64 = base64
)
