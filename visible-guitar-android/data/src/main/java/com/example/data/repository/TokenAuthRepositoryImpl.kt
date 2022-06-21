package com.example.data.repository

import com.example.data.model.auth.AuthRequestDTO
import com.example.data.remote.authenticator.LocalTokenProvider
import com.example.data.remote.service.AuthApiService
import com.example.domain.common.AuthResult
import com.example.domain.repository.TokenAuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class TokenAuthRepositoryImpl @Inject constructor(
    private val authApiService: AuthApiService,
    private val localTokenProvider: LocalTokenProvider
) : TokenAuthRepository {

    override fun getTokensPair(
        email: String,
        password: String,
    ): Flow<AuthResult<Unit>> = flow {
        try {
            emit(AuthResult.Loading())
            val tokenResponse = authApiService.getTokensPair(AuthRequestDTO(email, password))
            localTokenProvider.setAccessToken(tokenResponse.accessToken)
            emit(AuthResult.Authorized())
        } catch (throwable: Throwable) {
            when(throwable) {
                is HttpException -> {
                    if (throwable.code() == 401) {
                        emit(AuthResult.Unauthorized(message = "Неверная почта или пароль"))
                    } else {
                        emit(AuthResult.Unauthorized(
                            message = "Произошла непредвиденная ошибка",
                            )
                        )
                    }
                }
                is IOException -> {
                    emit(AuthResult.Unauthorized(message = "Отсутствует подключение к Интернету"))
                }
            }
        }
    }

    override fun deleteAccessToken() =
        localTokenProvider.deleteAccessToken()


    override fun refreshAccessToken(refreshToken: String): String {
        TODO("Not yet implemented")
    }
}