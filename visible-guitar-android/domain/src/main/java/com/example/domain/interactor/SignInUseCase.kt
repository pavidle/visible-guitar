package com.example.domain.interactor

import com.example.domain.common.AuthResult
import com.example.domain.common.Resource
import com.example.domain.model.auth.AuthRequestEntity
import com.example.domain.model.auth.TokenResponseEntity
import com.example.domain.repository.TokenAuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val tokenAuthRepository: TokenAuthRepository
) : UseCase<AuthRequestEntity, Flow<AuthResult<Unit>>> {
    override operator fun invoke(parameter: AuthRequestEntity): Flow<AuthResult<Unit>> =
        tokenAuthRepository.getTokensPair(parameter.email, parameter.password)
}
