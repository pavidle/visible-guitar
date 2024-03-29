package com.example.visible_guitar.di

import com.example.domain.mapper.Mapper
import com.example.domain.model.ChordEntity
import com.example.domain.model.MelodyEntity
import com.example.domain.model.auth.AuthRequestEntity
import com.example.domain.model.auth.SignUpRequestEntity
import com.example.domain.model.auth.UserEntity
import com.example.visible_guitar.mapper.*
import com.example.visible_guitar.model.Chord
import com.example.visible_guitar.model.Melody
import com.example.visible_guitar.model.User
import com.example.visible_guitar.model.auth.AuthRequest
import com.example.visible_guitar.model.auth.SignUpRequest
import dagger.Module
import javax.inject.Singleton
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideChordMapper(): Mapper<ChordEntity, Chord> =
        ChordMapper()

    @Provides
    @Singleton
    fun provideSignUpMapper(): Mapper<SignUpRequest, SignUpRequestEntity> =
        SignUpMapper()

    @Provides
    @Singleton
    fun provideAuthUserMapper(): Mapper<AuthRequest, AuthRequestEntity> =
        AuthRequestMapper()

    @Provides
    @Singleton
    fun provideUserMapper(): Mapper<UserEntity, User> =
        UserMapper()

    @Provides
    @Singleton
    fun provideMelodyMapper(): Mapper<MelodyEntity, Melody> =
        MelodyMapper()
}
