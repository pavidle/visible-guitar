package com.example.visible_guitar.model

import com.example.domain.model.Presentation

data class Chord(
    val id: Int,
    val instrument: Instrument,
    val name: String,
    val notes: List<Note>
) : Presentation