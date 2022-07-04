package com.example.data.remote.service

import com.example.data.model.ChordDTO
import com.example.data.model.MelodyDTO
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface MelodyApiService {
    @GET("/api/v1/melodies/")
    suspend fun getMelodies(): List<MelodyDTO>

    @GET("api/v1/melodies/{id}")
    suspend fun getMelodyById(
        @Path("id") id: Int
    ): MelodyDTO

    @PATCH("api/v1/addUserMelody/{id}/")
    suspend fun addMelodyForCurrentUser(
        @Path("id") id: Int
    )
}