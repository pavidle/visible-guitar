package com.example.data

import com.example.domain.models.User
import com.example.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl : UserRepository {

    override suspend fun getUserById(userId: Long): Flow<User> {
        TODO("Not yet implemented")
    }

    override suspend fun saveUser(user: User): Flow<User> {
        TODO("Not yet implemented")
    }

}