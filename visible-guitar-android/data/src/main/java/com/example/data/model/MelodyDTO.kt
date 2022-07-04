package com.example.data.model

data class MelodyDTO(
    val id: Int?,
    val name: String,
    val author: AuthorDTO,
    val chords: List<ChordDTO>
)
