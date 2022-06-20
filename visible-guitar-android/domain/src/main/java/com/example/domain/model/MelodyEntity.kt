package com.example.domain.model

data class MelodyEntity(
    val name: String,
    val author: AuthorEntity,
    val chords: List<ChordEntity>
)
