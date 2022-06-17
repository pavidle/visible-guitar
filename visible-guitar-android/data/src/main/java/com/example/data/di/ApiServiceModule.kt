package com.example.data.di

import android.content.SharedPreferences
import com.example.data.remote.authenticator.JsonWebTokenAuthenticator
import com.example.data.remote.authenticator.LocalTokenProvider
import com.example.data.remote.factory.HttpClientFactory
import com.example.data.remote.factory.RetrofitFactory
import com.example.data.remote.service.AuthApiService
import com.example.data.remote.service.ChordApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {

    @Provides
    @Singleton
    fun provideChordApiService(
        okHttpClient: OkHttpClient
    ): ChordApiService =
        RetrofitFactory(okHttpClient).create()

    @Provides
    @Singleton
    fun provideAuthApiService(
        okHttpClient: OkHttpClient
    ): AuthApiService =
        RetrofitFactory(okHttpClient).create()

    @Provides
    @Singleton
    fun provideLocalTokenProvider(
        encryptedSharedPreferences: SharedPreferences
    ): LocalTokenProvider =
        LocalTokenProvider(encryptedSharedPreferences)

    @Provides
    @Singleton
    fun provideJsonWebTokenAuthenticator(
        localTokenProvider: LocalTokenProvider
    ): JsonWebTokenAuthenticator =
        JsonWebTokenAuthenticator(localTokenProvider)

    @Provides
    @Singleton
    fun provideHttpClient(
        jsonWebTokenAuthenticator: JsonWebTokenAuthenticator
    ): OkHttpClient =
        HttpClientFactory(jsonWebTokenAuthenticator).create()
}