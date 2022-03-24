package com.example.data.remote

import com.example.data.model.ChordDTO
import retrofit2.http.GET

interface ChordApi {
    @GET("/api/v1/chords")
    suspend fun getChords(): List<ChordDTO>

}