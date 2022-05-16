package com.example.domain.model

data class NoteEntity(
    override val id: Int,
    val name: String,
    val stringNumber: Int,
    val fretNumber: Int,
): Entity