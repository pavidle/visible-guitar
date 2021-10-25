package com.example.domain.models

import kotlinx.coroutines.flow.Flow

data class Song(
    val name: String,
    val accords: Flow<List<Accord>>
)
