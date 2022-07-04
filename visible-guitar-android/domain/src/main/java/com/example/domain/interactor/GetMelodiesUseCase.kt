package com.example.domain.interactor

import com.example.domain.common.Resource
import com.example.domain.model.MelodyEntity
import com.example.domain.repository.MelodyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMelodiesUseCase @Inject constructor(
    private val melodyRepository: MelodyRepository
) : UseCase<Unit, Flow<Resource<List<MelodyEntity>>>> {

    override operator fun invoke(parameter: Unit): Flow<Resource<List<MelodyEntity>>> {
        return melodyRepository.getMelodies()
    }
}