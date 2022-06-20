package com.example.domain.model.auth

import com.example.domain.model.ChordEntity
import com.example.domain.model.MelodyEntity

data class UserEntity(
    val id: Int,
    val email: String,
    val username: String,
    val chords: List<ChordEntity>,
    val melodies: List<MelodyEntity>
)
