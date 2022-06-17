package com.example.domain.repository

import com.example.domain.common.AuthResult
import com.example.domain.common.Resource
import com.example.domain.model.auth.TokenResponseEntity
import kotlinx.coroutines.flow.Flow

interface TokenAuthRepository {

    fun getTokensPair(
        email: String,
        password: String
    ): Flow<AuthResult<Unit>>

    fun refreshAccessToken(
        refreshToken: String
    ): String
}