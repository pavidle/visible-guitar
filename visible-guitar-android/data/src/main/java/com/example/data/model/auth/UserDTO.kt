package com.example.data.model.auth

import com.example.data.model.ChordDTO
import com.example.data.model.MelodyDTO

data class UserDTO(
    val id: Int,
    val email: String,
    val username: String,
    val chords: List<ChordDTO>,
    val melodies: List<MelodyDTO>,
)