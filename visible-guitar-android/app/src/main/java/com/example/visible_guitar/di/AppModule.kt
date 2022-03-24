package com.example.visible_guitar.di

import com.example.data.common.Constants
import com.example.data.mapper.ChordDTOMapper
import com.example.data.model.ChordDTO
import com.example.data.remote.ChordApi
import com.example.data.repository.ChordRepositoryImpl
import com.example.domain.interactor.GetChordsUseCase
import com.example.domain.mapper.Mapper
import com.example.domain.model.ChordEntity
import com.example.domain.repository.ChordRepository
import com.example.visible_guitar.mapper.ChordMapper
import com.example.visible_guitar.model.Chord
import dagger.Module
import javax.inject.Singleton
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApi() : ChordApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ChordApi::class.java)
    }

    @Provides
    @Singleton
    fun provideChordDTOMapper() : Mapper<ChordDTO, ChordEntity> =
        ChordDTOMapper()

    @Provides
    @Singleton
    fun provideChordMapper() : Mapper<ChordEntity, Chord> =
        ChordMapper()

    @Provides
    @Singleton
    fun provideGetChordsUseCase(chordRepository: ChordRepository) : GetChordsUseCase =
        GetChordsUseCase(chordRepository)

    @Provides
    @Singleton
    fun provideChordRepository(chordApi: ChordApi, chordMapper: ChordDTOMapper) : ChordRepository =
        ChordRepositoryImpl(chordApi, chordMapper)

}
