package com.example.visible_guitar.model

import com.example.domain.model.ChordEntity

data class Melody(
    val name: String,
    val author: Author,
    val chords: List<Chord>
)
