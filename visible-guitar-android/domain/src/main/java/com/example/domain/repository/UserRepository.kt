package com.example.domain.repository

import com.example.domain.common.SignUpResult
import com.example.domain.model.auth.UserEntity
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun createUser(userEntity: UserEntity): Flow<SignUpResult<Unit>>
}