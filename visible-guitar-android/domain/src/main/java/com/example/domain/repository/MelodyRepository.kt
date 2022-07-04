package com.example.domain.repository

import com.example.domain.common.Resource
import com.example.domain.model.ChordEntity
import com.example.domain.model.MelodyEntity
import kotlinx.coroutines.flow.Flow

interface MelodyRepository {
    fun getMelodies(): Flow<Resource<List<MelodyEntity>>>
    fun getMelodyById(id: Int): Flow<Resource<MelodyEntity>>
    fun addMelodyForCurrentUser(id: Int): Flow<Resource<Unit>>
}