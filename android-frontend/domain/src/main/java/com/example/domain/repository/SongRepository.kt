package com.example.domain.repository

import com.example.domain.models.Song
import kotlinx.coroutines.flow.Flow

interface SongRepository {
    suspend fun getSongByName(name: String) : Flow<Song>
}