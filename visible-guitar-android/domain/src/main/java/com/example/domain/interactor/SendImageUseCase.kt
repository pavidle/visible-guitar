package com.example.domain.interactor

import com.example.domain.model.SubscribeDataEntity
import com.example.domain.repository.WebSocketImageRepository

class SendImageUseCase(
    private val webSocketImageRepository: WebSocketImageRepository
) : UseCase<SubscribeDataEntity, Unit>  {
    override suspend operator fun invoke(parameter: SubscribeDataEntity) {
        webSocketImageRepository.subscribe(parameter)
    }

}