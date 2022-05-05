package com.example.domain.repository

import com.example.domain.common.Resource
import com.example.domain.model.ChordEntity
import kotlinx.coroutines.flow.Flow

interface ChordRepository {
    suspend fun getChords(): Flow<Resource<List<ChordEntity>>>
    suspend fun getChordById(id: Int): Flow<Resource<ChordEntity>>
}