package com.example.data.di

import com.example.data.mapper.ChordDTOMapper
import com.example.data.mapper.MelodyDTOMapper
import com.example.data.mapper.UserDTOMapper
import com.example.data.model.MelodyDTO
import com.example.data.model.auth.SignUpRequestDTO
import com.example.data.model.auth.UserDTO
import com.example.data.remote.authenticator.LocalTokenProvider
import com.example.data.remote.service.AuthApiService
import com.example.data.remote.service.ChordApiService
import com.example.data.remote.service.MelodyApiService
import com.example.data.repository.ChordRepositoryImpl
import com.example.data.repository.MelodyRepositoryImpl
import com.example.data.repository.TokenAuthRepositoryImpl
import com.example.data.repository.UserRepositoryImpl
import com.example.domain.mapper.Mapper
import com.example.domain.model.MelodyEntity
import com.example.domain.model.auth.SignUpRequestEntity
import com.example.domain.model.auth.UserEntity
import com.example.domain.repository.ChordRepository
import com.example.domain.repository.MelodyRepository
import com.example.domain.repository.TokenAuthRepository
import com.example.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {


    @Provides
    @Singleton
    fun provideChordRepository(chordApiService: ChordApiService, chordMapper: ChordDTOMapper): ChordRepository =
        ChordRepositoryImpl(chordApiService, chordMapper)

    @Provides
    @Singleton
    fun provideUserRepository(
        authApiService: AuthApiService,
        signUpRequestDTOMapper: Mapper<SignUpRequestEntity, SignUpRequestDTO>,
        userDTOMapper: Mapper<UserDTO, UserEntity>
    ): UserRepository =
        UserRepositoryImpl(authApiService, signUpRequestDTOMapper, userDTOMapper)

    @Provides
    @Singleton
    fun provideTokenAuthRepository(
        authApiService: AuthApiService,
        localTokenProvider: LocalTokenProvider
    ): TokenAuthRepository =
        TokenAuthRepositoryImpl(authApiService, localTokenProvider)

    @Provides
    @Singleton
    fun provideMelodyRepository(
        melodyApiService: MelodyApiService,
        melodyDTOMapper: Mapper<MelodyDTO, MelodyEntity>
    ): MelodyRepository =
        MelodyRepositoryImpl(melodyApiService, melodyDTOMapper)
}