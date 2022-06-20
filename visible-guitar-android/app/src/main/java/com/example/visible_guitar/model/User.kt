package com.example.visible_guitar.model

import com.example.data.model.ChordDTO
import com.example.data.model.MelodyDTO

data class User(
    val id: Int,
    val email: String,
    val username: String,
    val chords: List<Chord>,
    val melodies: List<Melody>,
)
