package com.example.visible_guitar.model.states

import com.example.visible_guitar.model.Chord

data class ChordState(
    val items: List<Chord>? = null,
    val isLoading: Boolean = false
)