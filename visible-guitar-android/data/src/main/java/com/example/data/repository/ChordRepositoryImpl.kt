package com.example.data.repository

import com.example.data.model.ChordDTO
import com.example.data.remote.ChordApi
import com.example.domain.common.Resource
import com.example.domain.mapper.Mapper
import com.example.domain.model.ChordEntity
import com.example.domain.repository.ChordRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import retrofit2.HttpException
import java.io.IOException

class ChordRepositoryImpl @Inject constructor(
    private val chordApi: ChordApi,
    private val chordMapper: Mapper<ChordDTO, ChordEntity>
): ChordRepository {
    override suspend fun getChords(): Flow<Resource<List<ChordEntity>>> = flow {
        try {
            emit(Resource.Loading())
            val chords = chordApi.getChords().map { chordMapper.convert(it) }
            emit(Resource.Success(chords))
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