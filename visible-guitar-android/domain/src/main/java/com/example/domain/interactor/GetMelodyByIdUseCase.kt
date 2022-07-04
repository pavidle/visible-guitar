package com.example.domain.interactor

import com.example.domain.common.Resource
import com.example.domain.model.MelodyEntity
import com.example.domain.repository.MelodyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMelodyByIdUseCase @Inject constructor(
    private val melodyRepository: MelodyRepository
) : UseCase<Int, Flow<Resource<MelodyEntity>>> {
    override fun invoke(parameter: Int): Flow<Resource<MelodyEntity>> =
        melodyRepository.getMelodyById(parameter)
}