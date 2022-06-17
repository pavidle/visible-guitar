package com.example.domain.model.auth

data class TokenResponseEntity(
    val accessToken: String,
    val refreshToken: String
)
