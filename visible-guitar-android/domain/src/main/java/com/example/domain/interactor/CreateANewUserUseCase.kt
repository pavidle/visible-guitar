package com.example.domain.interactor

import com.example.domain.common.SignUpResult
import com.example.domain.model.auth.SignUpRequestEntity
import com.example.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateANewUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) : UseCase<SignUpRequestEntity, Flow<SignUpResult<Unit>>> {
    override operator fun invoke(parameter: SignUpRequestEntity): Flow<SignUpResult<Unit>> =
        userRepository.createUser(parameter)
}