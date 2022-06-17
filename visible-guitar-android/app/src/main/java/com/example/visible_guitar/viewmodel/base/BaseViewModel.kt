package com.example.visible_guitar.viewmodel.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.common.Resource
import com.example.domain.mapper.Mapper
import com.example.domain.model.Entity
import com.example.visible_guitar.common.extensions.launchCoroutine
import com.example.visible_guitar.model.Model
import com.example.visible_guitar.model.states.StateOfList
import kotlinx.coroutines.flow.Flow

abstract class BaseViewModel : ViewModel() {

    protected open fun <TEntity : Entity, TModel : Model>
            Flow<Resource<List<TEntity>>>.subscribeOnCollect(
        mapper: Mapper<TEntity, TModel>,
        state: MutableLiveData<StateOfList<TModel>>
    ) {
        launchCoroutine {
            this@subscribeOnCollect.collect { resource ->
                when (resource) {
                    is Resource.Loading -> state.value = StateOfList.Loading()
                    is Resource.Error -> resource.message?.let { message ->
                        state.value = StateOfList.Error(message)
                    }
                    is Resource.Success -> resource.data?.let { data ->
                        state.value = StateOfList.Success(
                            data.map { entity -> mapper.convert(entity) }
                        )
                    }
                }
            }
        }
    }
}