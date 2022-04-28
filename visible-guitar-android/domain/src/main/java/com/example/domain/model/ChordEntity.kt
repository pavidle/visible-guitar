package com.example.domain.model

data class ChordEntity(
    override val id: Int,
    val instrument: InstrumentEntity,
    val name: String,
    val notes: List<NoteEntity>
): Entity