package com.example.visible_guitar.di

import android.app.Application
import com.example.data.common.AsyncDispatcher
import com.example.data.common.DefaultAsyncDispatcher

import com.example.data.common.RetrofitFactory
import com.example.data.common.ScarletFactory
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
import com.example.domain.interactor.GetChordsUseCase
import com.example.domain.interactor.ReceiveImageUseCase
import com.example.domain.interactor.SendImageUseCase
import com.example.domain.mapper.Mapper
import com.example.domain.model.ChordEntity
import com.example.domain.model.ImageResponseEntity
import com.example.domain.model.SubscribeDataEntity
import com.example.domain.repository.ChordRepository
import com.example.domain.repository.WebSocketImageRepository
import com.example.visible_guitar.common.util.Base64Service
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
    fun provideApi() : ChordApi =
        RetrofitFactory().create()

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
    fun provideSubscribeDataMapper(
        base64Service: Base64Service
    ): Mapper<SubscribeData, SubscribeDataEntity> =
        SubscribeDataMapper(base64Service)

    @Provides
    @Singleton
    fun provideWebSocketEventMapper(): Mapper<WebSocket.Event, WebSocketEvent> =
        WebSocketEventMapper()

    @Provides
    @Singleton
    fun provideGetChordsUseCase(chordRepository: ChordRepository) : GetChordsUseCase =
        GetChordsUseCase(chordRepository)

    @Provides
    @Singleton
    fun provideChordRepository(chordApi: ChordApi, chordMapper: ChordDTOMapper) : ChordRepository =
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
    fun provideReceiveDataMapper(base64Service: Base64Service):
            Mapper<ImageResponseEntity, ReceiveData> =
        ReceiveDataMapper(base64Service)

    @Provides
    @Singleton
    fun provideWebSocketImageRepository(
        webSocketRemote: WebSocketRemote,
        responseEntityMapper: ImageResponseEntityMapper,
        asyncDispatcher: AsyncDispatcher
    ): WebSocketImageRepository =
        WebSocketImageRepositoryImpl(webSocketRemote, responseEntityMapper, asyncDispatcher)

    @Provides
    @Singleton
    fun provideSendImageUseCase(webSocketImageRepository: WebSocketImageRepository) : SendImageUseCase =
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

    @Provides
    @Singleton
    fun provideBase64Service(): Base64Service =
        Base64Service()

    @Provides
    @Singleton
    fun provideAsyncDispatcher(): AsyncDispatcher =
        DefaultAsyncDispatcher()

}