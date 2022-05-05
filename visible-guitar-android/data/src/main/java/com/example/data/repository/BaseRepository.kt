package com.example.data.repository

import com.example.domain.common.Resource
import com.example.domain.model.ImageResponseEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

abstract class BaseRepository {

    protected open fun <T> makeRequest(request: suspend () -> T): Flow<Resource<T>> = flow {
        try {
            emit(Resource.Loading())
            emit(Resource.Success(data = request()))
        } catch (e: HttpException) {
            emit(Resource.Error(
                message = "Произошла непредвиденная ошибка",
                data = null
            ))
        } catch (e: IOException) {
            emit(Resource.Error(
                message = "Отсутствует подключение к Интернету",
                data = null
            ))
        }
    }

}