package com.example.domain.repository

import com.example.domain.common.Resource
import com.example.domain.model.ChordEntity
import kotlinx.coroutines.flow.Flow

interface ChordRepository {
    fun getChords(): Flow<Resource<List<ChordEntity>>>
    fun getChordById(id: Int): Flow<Resource<ChordEntity>>
}