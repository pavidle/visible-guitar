package com.example.data.model.auth

import com.google.gson.annotations.SerializedName

data class UserDTO(
    val username: String,
    val email: String,
    val password: String
)
