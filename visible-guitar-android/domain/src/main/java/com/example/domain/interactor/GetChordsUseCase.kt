package com.example.domain.interactor

import com.example.domain.common.Resource
import com.example.domain.model.ChordEntity
import com.example.domain.repository.ChordRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetChordsUseCase @Inject constructor(
    private val chordRepository: ChordRepository
) : UseCase<Unit, Flow<Resource<List<ChordEntity>>>> {
    override operator fun invoke(parameter: Unit) = chordRepository.getChords()
}