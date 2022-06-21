package com.example.domain.di

import com.example.domain.common.AuthResult
import com.example.domain.common.Resource
import com.example.domain.common.SignUpResult
import com.example.domain.interactor.*
import com.example.domain.model.ChordEntity
import com.example.domain.model.auth.AuthRequestEntity
import com.example.domain.model.auth.SignUpRequestEntity
import com.example.domain.model.auth.UserEntity
import com.example.domain.repository.ChordRepository
import com.example.domain.repository.TokenAuthRepository
import com.example.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {


    @Provides
    @Singleton
    fun provideGetChordsUseCase(chordRepository: ChordRepository)
    : UseCase<Unit, Flow<Resource<List<ChordEntity>>>> =
        GetChordsUseCase(chordRepository)

    @Provides
    @Singleton
    fun provideGetChordByIdUseCase(chordRepository: ChordRepository)
    : UseCase<Int, Flow<Resource<ChordEntity>>> =
        GetChordByIdUseCase(chordRepository)

    @Provides
    @Singleton
    fun provideCreateANewUserUseCase(userRepository: UserRepository)
    : UseCase<SignUpRequestEntity, Flow<SignUpResult<Unit>>> =
        CreateANewUserUseCase(userRepository)

    @Provides
    @Singleton
    fun provideSignInUseCase(
        tokenAuthRepository: TokenAuthRepository
    ): UseCase<AuthRequestEntity, Flow<AuthResult<Unit>>> =
        SignInUseCase(tokenAuthRepository)

    @Provides
    @Singleton
    fun provideGetCurrentUserUseCase(
        userRepository: UserRepository
    ): UseCase<Unit, Flow<Resource<UserEntity>>> =
        GetCurrentUserUseCase(userRepository)

    @Provides
    @Singleton
    fun provideExitFromAccountUseCase(
        tokenAuthRepository: TokenAuthRepository
    ): UseCase<Unit, Unit> =
        ExitFromAccountUseCase(tokenAuthRepository)
}