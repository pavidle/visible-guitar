package com.example.domain.interactor

import com.example.domain.models.User
import com.example.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserByIdUseCase @Inject constructor(
    private val userRepository: UserRepository
    ) : BaseUseCase<Long, Flow<User>> {

    override suspend operator fun invoke(param: Long) : Flow<User> = userRepository.getUserById(param)
}
