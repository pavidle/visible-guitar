package com.example.data.model

import com.google.gson.annotations.SerializedName

data class NoteDTO(
    val id: Int,
    val name: String,
    @SerializedName("string_number")
    val stringNumber: Int,
    @SerializedName("fret_number")
    val fretNumber: Int,
)