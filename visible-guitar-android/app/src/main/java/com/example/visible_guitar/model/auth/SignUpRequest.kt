package com.example.visible_guitar.model.auth

data class SignUpRequest(
    val username: String,
    val email: String,
    val password: String
)
