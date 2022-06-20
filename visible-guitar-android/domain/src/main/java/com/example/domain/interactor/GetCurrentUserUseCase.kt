package com.example.domain.interactor

import com.example.domain.common.Resource
import com.example.domain.model.auth.UserEntity
import com.example.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) : UseCase<Unit, Flow<Resource<UserEntity>>> {
    override fun invoke(parameter: Unit): Flow<Resource<UserEntity>> =
        userRepository.getCurrentUser()
}