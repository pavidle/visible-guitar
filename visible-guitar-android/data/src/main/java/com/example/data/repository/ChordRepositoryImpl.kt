package com.example.data.repository

import com.example.data.model.ChordDTO
import com.example.data.remote.service.ChordApiService
import com.example.data.repository.base.BaseRepository
import com.example.domain.common.Resource
import com.example.domain.mapper.Mapper
import com.example.domain.model.ChordEntity
import com.example.domain.repository.ChordRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChordRepositoryImpl @Inject constructor(
    private val chordApiService: ChordApiService,
    private val chordMapper: Mapper<ChordDTO, ChordEntity>
): BaseRepository(), ChordRepository {
    override fun getChords(): Flow<Resource<List<ChordEntity>>> = makeRequest {
        chordApiService.getChords().map { chordMapper.convert(it) }
    }

    override fun getChordById(id: Int): Flow<Resource<ChordEntity>> = makeRequest {
        chordMapper.convert(chordApiService.getChordById(id))
    }
}
