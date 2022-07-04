package com.example.domain.interactor

import com.example.domain.common.Resource
import com.example.domain.repository.MelodyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddMelodyForCurrentUserUseCase @Inject constructor(
    private val melodyRepository: MelodyRepository
) : UseCase<Int, Flow<Resource<Unit>>>{
    override fun invoke(parameter: Int): Flow<Resource<Unit>> {
        return melodyRepository.addMelodyForCurrentUser(parameter)
    }
}