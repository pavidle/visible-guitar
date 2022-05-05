package com.example.data.repository

import com.example.data.model.ChordDTO
import com.example.data.remote.ChordApi
import com.example.domain.common.Resource
import com.example.domain.mapper.Mapper
import com.example.domain.model.ChordEntity
import com.example.domain.repository.ChordRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import java.io.IOException

class ChordRepositoryImpl @Inject constructor(
    private val chordApi: ChordApi,
    private val chordMapper: Mapper<ChordDTO, ChordEntity>
): BaseRepository(), ChordRepository {
    override suspend fun getChords(): Flow<Resource<List<ChordEntity>>> = makeRequest {
        chordApi.getChords().map { chordMapper.convert(it) }
    }

    override suspend fun getChordById(id: Int): Flow<Resource<ChordEntity>> = makeRequest {
        chordMapper.convert(chordApi.getChordById(id))
    }
}
