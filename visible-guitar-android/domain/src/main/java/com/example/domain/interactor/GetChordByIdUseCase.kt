package com.example.domain.interactor

import com.example.domain.common.Resource
import com.example.domain.model.ChordEntity
import com.example.domain.repository.ChordRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetChordByIdUseCase @Inject constructor(
    private val chordRepository: ChordRepository
) : UseCase<Int, Flow<Resource<ChordEntity>>> {
    override operator fun invoke(parameter: Int): Flow<Resource<ChordEntity>> =
        chordRepository.getChordById(parameter)
}