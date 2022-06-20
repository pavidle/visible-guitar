package com.example.data.di

import com.example.data.mapper.*
import com.example.data.model.ChordDTO
import com.example.data.model.auth.TokenResponseDTO
import com.example.data.model.auth.SignUpRequestDTO
import com.example.data.model.auth.UserDTO
import com.example.domain.mapper.Mapper
import com.example.domain.model.ChordEntity
import com.example.domain.model.auth.TokenResponseEntity
import com.example.domain.model.auth.SignUpRequestEntity
import com.example.domain.model.auth.UserEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {

    @Provides
    @Singleton
    fun provideChordDTOMapper(): Mapper<ChordDTO, ChordEntity> =
        ChordDTOMapper()

    @Provides
    @Singleton
    fun provideRequestDTOMapper(): Mapper<SignUpRequestEntity, SignUpRequestDTO> =
        SignUpRequestDTOMapper()

    @Provides
    @Singleton
    fun provideUserDTOMapper(): Mapper<UserDTO, UserEntity> =
        UserDTOMapper()

    @Singleton
    @Provides
    fun provideTokenResponseDTOMapper(): Mapper<TokenResponseDTO, TokenResponseEntity> =
        TokenResponseDTOMapper()
}