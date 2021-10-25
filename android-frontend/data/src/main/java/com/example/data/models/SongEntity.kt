package com.example.data.models

import com.example.domain.models.Accord
import kotlinx.coroutines.flow.Flow

data class SongEntity(
    val name: String,
    val accords: Flow<List<Accord>>
)
