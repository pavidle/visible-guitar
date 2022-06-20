package com.example.data.repository.base

import com.example.domain.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

abstract class BaseRepository {

    protected open fun <T: Any> makeRequest(request: suspend () -> T): Flow<Resource<T>> = flow {
//        try {
            emit(Resource.Loading())
            emit(Resource.Success(data = request()))
//        } catch (throwable: Throwable) {
//            when(throwable) {
//                is IOException -> emit(Resource.Error(
//                    message = "Отсутствует подключение к Интернету",
//                    data = null
//                ))
//                else -> emit(Resource.Error(
//                    message = "Произошла непредвиденная ошибка",
//                    data = null
//                ))
//            }
//        }
    }
}