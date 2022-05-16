package com.example.visible_guitar.di

import android.app.Application

import com.example.data.remote.RetrofitFactory
import com.example.data.remote.ScarletFactory
import com.example.data.mapper.ChordDTOMapper
import com.example.data.mapper.ImageResponseEntityMapper
import com.example.data.mapper.WebSocketEventMapper
import com.example.data.model.ChordDTO
import com.example.data.model.events.WebSocketEvent
import com.example.data.remote.ChordApi
import com.example.data.remote.ScarletWebSocketApi
import com.example.data.remote.WebSocketRemote
import com.example.data.remote.WebSocketRemoteImpl
import com.example.data.repository.ChordRepositoryImpl
import com.example.data.repository.WebSocketImageRepositoryImpl
import com.example.domain.interactor.GetChordByIdUseCase
import com.example.domain.interactor.GetChordsUseCase
import com.example.domain.interactor.ReceiveImageUseCase
import com.example.domain.interactor.SendImageUseCase
import com.example.domain.mapper.Mapper
import com.example.domain.model.ChordEntity
import com.example.domain.model.ImageResponseEntity
import com.example.domain.model.SubscribeDataEntity
import com.example.domain.repository.ChordRepository
import com.example.domain.repository.WebSocketImageRepository
import com.example.visible_guitar.common.Constants
import com.example.data.common.imagepreprocessor.Base64ImageAdapter
import com.example.data.common.imagepreprocessor.ImagePreprocessorService
import com.example.data.mapper.SubscribeDataDTOMapper
import com.example.data.model.SubscribeDataDTO
import com.example.visible_guitar.mapper.ChordMapper
import com.example.visible_guitar.mapper.ReceiveDataMapper
import com.example.visible_guitar.mapper.SubscribeDataMapper
import com.example.visible_guitar.model.Chord
import com.example.visible_guitar.model.ReceiveData
import com.example.visible_guitar.model.SubscribeData
import com.squareup.moshi.Moshi
import com.tinder.scarlet.WebSocket
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
    fun provideApi(): ChordApi =
        RetrofitFactory().create()

    @Provides
    @Singleton
    fun provideImagePreprocessorService(): ImagePreprocessorService =
        ImagePreprocessorService.Builder()
            .setCompressionRatio(Constants.IMAGE_COMPRESSION_RATIO)
            .setImageQuality(Constants.QUALITY)
            .setImageAdapterFactory(Base64ImageAdapter.Factory())
            .build()


    @Provides
    @Singleton
    fun provideChordDTOMapper(): Mapper<ChordDTO, ChordEntity> =
        ChordDTOMapper()

    @Provides
    @Singleton
    fun provideChordMapper(): Mapper<ChordEntity, Chord> =
        ChordMapper()

    @Provides
    @Singleton
    fun provideSubscribeDataMapper(
    ): Mapper<SubscribeData, SubscribeDataEntity> =
        SubscribeDataMapper()

    @Provides
    @Singleton
    fun provideSubscribeDataDTOMapper(
        imagePreprocessorService: ImagePreprocessorService
    ): Mapper<SubscribeDataEntity, SubscribeDataDTO> =
        SubscribeDataDTOMapper(imagePreprocessorService)

    @Provides
    @Singleton
    fun provideWebSocketEventMapper(): Mapper<WebSocket.Event, WebSocketEvent> =
        WebSocketEventMapper()

    @Provides
    @Singleton
    fun provideGetChordsUseCase(chordRepository: ChordRepository): GetChordsUseCase =
        GetChordsUseCase(chordRepository)

    @Provides
    @Singleton
    fun provideGetChordsByIdUseCase(chordRepository: ChordRepository): GetChordByIdUseCase =
        GetChordByIdUseCase(chordRepository)

    @Provides
    @Singleton
    fun provideChordRepository(chordApi: ChordApi, chordMapper: ChordDTOMapper): ChordRepository =
        ChordRepositoryImpl(chordApi, chordMapper)

    @Provides
    @Singleton
    fun provideWebSocketRemote(
        scarletWebSocketApi: ScarletWebSocketApi,
        webSocketEventMapper: WebSocketEventMapper
    ): WebSocketRemote =
        WebSocketRemoteImpl(scarletWebSocketApi, webSocketEventMapper)

    @Provides
    @Singleton
    fun provideImageResponseEntityMapper(): ImageResponseEntityMapper =
        ImageResponseEntityMapper()

    @Provides
    @Singleton
    fun provideReceiveDataMapper(
        imagePreprocessorService: ImagePreprocessorService
    ): Mapper<ImageResponseEntity, ReceiveData> =
        ReceiveDataMapper(imagePreprocessorService)

    @Provides
    @Singleton
    fun provideWebSocketImageRepository(
        webSocketRemote: WebSocketRemote,
        responseEntityMapper: ImageResponseEntityMapper,
        subscribeDataDTOMapper: Mapper<SubscribeDataEntity, SubscribeDataDTO>
    ): WebSocketImageRepository =
        WebSocketImageRepositoryImpl(
            webSocketRemote,
            responseEntityMapper,
            subscribeDataDTOMapper
        )

    @Provides
    @Singleton
    fun provideSendImageUseCase(webSocketImageRepository: WebSocketImageRepository): SendImageUseCase =
        SendImageUseCase(webSocketImageRepository)

    @Provides
    @Singleton
    fun provideReceiveImageUseCase(
        webSocketImageRepository: WebSocketImageRepository): ReceiveImageUseCase =
        ReceiveImageUseCase(webSocketImageRepository)

    @Provides
    @Singleton
    fun provideScarlet(
        application: Application,
        moshi: Moshi
    ): ScarletWebSocketApi =
        ScarletFactory(
            application = application,
            moshi = moshi)
            .create()


    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder().build()
}
