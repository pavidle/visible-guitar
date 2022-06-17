package com.example.data.repository

import com.example.data.model.auth.UserDTO
import com.example.data.remote.service.AuthApiService
import com.example.domain.common.UserFieldErrorBody
import com.example.domain.common.SignUpResult
import com.example.domain.mapper.Mapper
import com.example.domain.model.auth.UserEntity
import com.example.domain.repository.UserRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val authApiService: AuthApiService,
    private val userDTOMapper: Mapper<UserEntity, UserDTO>
) : UserRepository {
    override fun createUser(userEntity: UserEntity): Flow<SignUpResult<Unit>> = flow {
        try {
            emit(SignUpResult.Loading())
            authApiService.createUser(userDTOMapper.convert(userEntity))
            emit(SignUpResult.Registered())
        } catch (throwable: Throwable) {
            when(throwable) {
                is HttpException -> {
                    val response = throwable.response()
                    val body = generateUserFieldErrorBody(response?.errorBody())
                    if (body != null) {
                        emit(SignUpResult.ApiError(body, response?.code() ?: 400))
                    }
                }
                is IOException -> {
                    emit(SignUpResult.NetworkError(message = "Отсутствует подключение к Интернету"))
                }
            }
        }
    }

    private fun generateUserFieldErrorBody(error: ResponseBody?): UserFieldErrorBody? {
        return when {
            error == null -> null
            error.contentLength() == 0L -> null
            else -> try {
                Gson().fromJson(error.charStream(), UserFieldErrorBody::class.java)
            } catch (ex: Exception) {
                null
            }
        }
    }
}