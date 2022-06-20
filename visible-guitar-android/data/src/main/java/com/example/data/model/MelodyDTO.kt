package com.example.data.model

data class MelodyDTO(
    val name: String,
    val author: AuthorDTO,
    val chords: List<ChordDTO>
)
