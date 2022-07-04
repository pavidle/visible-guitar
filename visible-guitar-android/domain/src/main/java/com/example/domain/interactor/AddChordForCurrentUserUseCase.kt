package com.example.domain.interactor

import com.example.domain.common.Resource
import com.example.domain.repository.ChordRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddChordForCurrentUserUseCase @Inject constructor(
    private val chordRepository: ChordRepository
) : UseCase<Int, Flow<Resource<Unit>>> {
    override fun invoke(parameter: Int): Flow<Resource<Unit>> {
        return chordRepository.addChordForCurrentUser(parameter)
    }
}