package com.example.data.model.auth

import com.google.gson.annotations.SerializedName
import retrofit2.http.POST

data class TokenResponseDTO(
    @SerializedName("access")
    val accessToken: String,

    @SerializedName("refresh")
    val refreshToken: String
)
