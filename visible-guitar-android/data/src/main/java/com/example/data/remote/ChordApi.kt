package com.example.data.remote

import com.example.data.model.ChordDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface ChordApi {
    @GET("/api/v1/chords")
    suspend fun getChords(): List<ChordDTO>

    @GET("api/v1/chords/{id}")
    suspend fun getChordById(
        @Path("id") id: Int
    ): ChordDTO
}