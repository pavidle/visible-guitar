package com.example.domain.interactor

import com.example.domain.common.Resource
import com.example.domain.model.ImageResponseEntity
import com.example.domain.repository.WebSocketImageRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class ReceiveImageUseCase(
    private val webSocketImageRepository: WebSocketImageRepository
) : UseCase<Unit, Flow<ImageResponseEntity>>  {
    override suspend fun invoke(parameter: Unit) =
        webSocketImageRepository.receive()
}