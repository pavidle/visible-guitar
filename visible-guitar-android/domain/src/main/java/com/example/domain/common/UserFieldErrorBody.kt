package com.example.domain.common

import com.google.gson.annotations.SerializedName

data class UserFieldErrorBody(
    @SerializedName("username")
    val usernameFieldErrors: List<String> = listOf(),

    @SerializedName("email")
    val emailFieldErrors: List<String> = listOf(),

    @SerializedName("password")
    val passwordFieldErrors: List<String> = listOf()
)