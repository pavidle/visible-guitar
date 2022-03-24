package com.example.data.model

data class ChordDTO(
    val id: Int,
    val instrument: InstrumentDTO,
    val name: String,
    val notes: List<NoteDTO>
)