package com.example.domain.interactor

import com.example.domain.models.Song
import com.example.domain.repository.SongRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSongByNameUseCase @Inject constructor(
    private val songRepository: SongRepository
) : BaseUseCase<String, Flow<Song>> {

    override suspend fun invoke(param: String): Flow<Song> = songRepository.getSongByName(param)
}