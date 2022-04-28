package com.example.domain.model

data class NoteEntity(
    override val id: Int,
    val name: String,
    val string_number: Int,
    val fret_number: Int,
): Entity