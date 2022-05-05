package com.example.visible_guitar.viewmodel.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.common.Resource
import com.example.domain.mapper.Mapper
import com.example.domain.model.Entity
import com.example.visible_guitar.model.Model
import com.example.visible_guitar.model.states.State
import kotlinx.coroutines.flow.Flow

abstract class BaseViewModel : ViewModel() {

    protected open suspend fun <TEntity : Entity, TModel : Model>
            Flow<Resource<List<TEntity>>>.subscribeOnCollect(
        mapper: Mapper<TEntity, TModel>,
        state: MutableLiveData<State<TModel>>
    ) {
        this@subscribeOnCollect.collect { resource ->
            when (resource) {
                is Resource.Loading -> state.value = State.Loading()
                is Resource.Error -> resource.message?.let { message ->
                    state.value = State.Error(message)
                }
                is Resource.Success -> resource.data?.let { data ->
                    state.value = State.Success(
                        data.map { entity -> mapper.convert(entity) }
                    )
                }
            }
        }
    }
}