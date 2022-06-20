package com.example.domain.repository

import com.example.domain.common.Resource
import com.example.domain.common.SignUpResult
import com.example.domain.model.auth.SignUpRequestEntity
import com.example.domain.model.auth.UserEntity
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun createUser(signUpRequestEntity: SignUpRequestEntity): Flow<SignUpResult<Unit>>
    fun getCurrentUser(): Flow<Resource<UserEntity>>
}