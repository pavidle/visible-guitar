package com.example.data.remote.service

import com.example.data.model.ChordDTO
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface ChordApiService {
    @GET("/api/v1/chords/")
    suspend fun getChords(): List<ChordDTO>

    @GET("api/v1/chords/{id}/")
    suspend fun getChordById(
        @Path("id") id: Int
    ): ChordDTO

    @PATCH("api/v1/addUserChord/{id}/")
    suspend fun addChordForCurrentUser(
        @Path("id") id: Int
    )
}