package com.example.data

import com.example.domain.models.Song
import com.example.domain.repository.SongRepository
import kotlinx.coroutines.flow.Flow

class SongRepositoryImpl : SongRepository {
    override suspend fun getSongByName(name: String): Flow<Song> {
        TODO("Not yet implemented")
    }
}