package com.example.visible_guitar.model

import com.example.domain.model.Presentation

data class Note(
    val id: Int,
    val name: String,
    val string_number: Int,
    val fret_number: Int,
) : Presentation