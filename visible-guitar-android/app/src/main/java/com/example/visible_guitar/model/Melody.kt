package com.example.visible_guitar.model

import com.example.domain.model.ChordEntity

data class Melody(
    override val id: Int,
    val name: String,
    val author: Author,
    val chords: List<Chord>
) : Model
