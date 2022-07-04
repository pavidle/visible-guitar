package com.example.data.repository

import com.example.data.mapper.MelodyDTOMapper
import com.example.data.model.MelodyDTO
import com.example.data.remote.service.MelodyApiService
import com.example.data.repository.base.BaseRepository
import com.example.domain.common.Resource
import com.example.domain.mapper.Mapper
import com.example.domain.model.MelodyEntity
import com.example.domain.repository.MelodyRepository
import kotlinx.coroutines.flow.Flow

class MelodyRepositoryImpl(
    private val melodyApiService: MelodyApiService,
    private val melodyDTOMapper: Mapper<MelodyDTO, MelodyEntity>
) : BaseRepository(), MelodyRepository {
    override fun getMelodies(): Flow<Resource<List<MelodyEntity>>> = makeRequest {
        melodyApiService.getMelodies().map { melodyDTOMapper.convert(it) }
    }

    override fun getMelodyById(id: Int): Flow<Resource<MelodyEntity>> = makeRequest {
        melodyDTOMapper.convert(melodyApiService.getMelodyById(id))
    }

    override fun addMelodyForCurrentUser(id: Int): Flow<Resource<Unit>> = makeRequest {
        melodyApiService.addMelodyForCurrentUser(id)
    }
}