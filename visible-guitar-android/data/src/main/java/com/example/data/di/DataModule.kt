package com.example.data.di

import android.app.Application
import android.content.SharedPreferences
import com.example.data.common.EncryptedSharedPreferencesFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideEncryptedSharedPreferences(
        application: Application
    ): SharedPreferences =
        EncryptedSharedPreferencesFactory(application).create()
}