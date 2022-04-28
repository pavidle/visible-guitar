package com.example.visible_guitar.model

data class Chord(
    override val id: Int,
    val instrument: Instrument,
    val name: String,
    val notes: List<Note>
) : Model