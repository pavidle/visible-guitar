package com.example.visible_guitar.viewmodel.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.common.Resource
import com.example.domain.mapper.Mapper
import com.example.visible_guitar.model.states.DetailState
import kotlinx.coroutines.flow.Flow


abstract class BaseDetailViewModel : ViewModel() {

    protected open suspend fun <TEntity, TModel>
            Flow<Resource<TEntity>>.subscribeOnCollect(
        mapper: Mapper<TEntity, TModel>,
        state: MutableLiveData<DetailState<TModel>>
    ) {
        this@subscribeOnCollect.collect { resource ->
            when(resource) {
                is Resource.Loading -> state.value = DetailState.Loading()
                is Resource.Error -> resource.message?.let { message ->
                    state.value = DetailState.Error(message)
                }
                is Resource.Success -> resource.data?.let { data ->
                    state.value = DetailState.Success(mapper.convert(data))
                }
            }
        }
    }
}