package com.example.domain.model

data class MelodyEntity(
    override val id: Int?,
    val name: String,
    val author: AuthorEntity,
    val chords: List<ChordEntity>,
) : Entity
