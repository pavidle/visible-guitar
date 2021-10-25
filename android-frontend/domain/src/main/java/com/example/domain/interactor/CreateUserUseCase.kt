package com.example.domain.interactor

import com.example.domain.models.User
import com.example.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) : BaseUseCase<User, Flow<User>> {

    override suspend operator fun invoke(param: User) : Flow<User> = userRepository.saveUser(param)
}