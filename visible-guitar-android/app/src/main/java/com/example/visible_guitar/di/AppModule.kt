package com.example.visible_guitar.di

import com.example.domain.mapper.Mapper
import com.example.domain.model.ChordEntity
import com.example.domain.model.auth.AuthRequestEntity
import com.example.domain.model.auth.UserEntity
import com.example.visible_guitar.mapper.*
import com.example.visible_guitar.model.Chord
import com.example.visible_guitar.model.auth.AuthRequest
import com.example.visible_guitar.model.auth.User
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
    fun provideUserMapper(): Mapper<User, UserEntity> =
        UserMapper()

    @Provides
    @Singleton
    fun provideAuthUserMapper(): Mapper<AuthRequest, AuthRequestEntity> =
        AuthRequestMapper()

}
