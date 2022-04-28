package com.example.domain.model

sealed interface ImageResponseEntity {
    val base64: String

    sealed interface Inactive : ImageResponseEntity {
        val message: String?
    }

    data class Success(
        override val base64: String
    ) : ImageResponseEntity

    data class Closed(
        override val base64: String,
        override val message: String?
    ) : Inactive

    data class Error(
        override val base64: String,
        override val message: String?
    ) : Inactive
}