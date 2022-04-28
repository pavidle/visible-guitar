package com.example.visible_guitar.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.common.Resource
import com.example.domain.mapper.Mapper
import com.example.domain.model.Entity
import com.example.visible_guitar.model.Model
import com.example.visible_guitar.model.states.ListState
import kotlinx.coroutines.flow.Flow

abstract class BaseViewModel<TModel : Model> : ViewModel() {

    protected suspend fun <TEntity : Entity> Flow<Resource<List<TEntity>>>.subscribeOnCollect(
        mapper: Mapper<TEntity, TModel>,
        state: MutableLiveData<ListState<TModel>>
    ) {
        this@subscribeOnCollect.collect { resource ->
            when (resource) {
                is Resource.Loading -> state.value = ListState.Loading()
                is Resource.Success -> resource.data?.let { data -> state.value = ListState.Success(
                    data.map { entity -> mapper.convert(entity) }
                )
            }
                is Resource.Error -> resource.message?.let {
                        message -> state.value = ListState.Error(message)
                }
            }
        }
    }
}