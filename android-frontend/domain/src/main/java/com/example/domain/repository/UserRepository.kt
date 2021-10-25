package com.example.domain.repository

import com.example.domain.models.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUserById(userId: Long): Flow<User>
    suspend fun saveUser(user: User) : Flow<User>
}
