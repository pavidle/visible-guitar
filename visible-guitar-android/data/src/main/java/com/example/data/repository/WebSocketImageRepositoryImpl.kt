package com.example.data.repository


import com.example.data.mapper.ImageResponseEntityMapper
import com.example.data.model.SubscribeDataDTO
import com.example.data.remote.WebSocketRemote
import com.example.domain.mapper.Mapper
import com.example.domain.model.ImageResponseEntity
import com.example.domain.model.SubscribeDataEntity
import com.example.domain.repository.WebSocketImageRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class WebSocketImageRepositoryImpl @Inject constructor(
    private val webSocketRemote: WebSocketRemote,
    private val imageResponseEntityMapper: ImageResponseEntityMapper,
    private val subscribeDataDTOMapper: Mapper<SubscribeDataEntity, SubscribeDataDTO>
) : WebSocketImageRepository {

    override suspend fun subscribe(data: SubscribeDataEntity) =
        webSocketRemote.subscribe(
            data = subscribeDataDTOMapper.convert(data)
        )


    override suspend fun receive(): Flow<ImageResponseEntity> =
        combine(
            webSocketRemote.observe().distinctUntilChanged(),
            webSocketRemote.observeEvent().distinctUntilChanged()
        ) { model, event ->
            return@combine imageResponseEntityMapper.convert(event, model)
    }
}
