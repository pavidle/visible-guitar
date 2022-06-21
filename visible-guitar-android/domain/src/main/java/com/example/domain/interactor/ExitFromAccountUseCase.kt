package com.example.domain.interactor

import com.example.domain.repository.TokenAuthRepository
import javax.inject.Inject

class ExitFromAccountUseCase @Inject constructor(
    private val tokenAuthRepository: TokenAuthRepository
) : UseCase<Unit, Unit> {
    override fun invoke(parameter: Unit) =
        tokenAuthRepository.deleteAccessToken()
}