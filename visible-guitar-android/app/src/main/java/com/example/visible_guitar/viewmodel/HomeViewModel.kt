package com.example.visible_guitar.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.common.Resource
import com.example.domain.interactor.GetChordsUseCase
import com.example.domain.mapper.Mapper
import com.example.domain.model.ChordEntity
import com.example.visible_guitar.model.Chord
import com.example.visible_guitar.model.states.ChordState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getChordsUseCase: GetChordsUseCase,
    private val chordMapper: Mapper<ChordEntity, Chord>
) : BaseViewModel() {

    private val _state = MutableLiveData<ChordState>()
    val state: LiveData<ChordState> = _state

    init {
        _state.value = ChordState(items = emptyList(), isLoading = true)
        getChords()
    }

    private fun setStateForResource(resource: Resource<List<ChordEntity>>, isLoading: Boolean) {
        _state.value = state.value?.copy(
            items = resource.data?.map { chordMapper.from(it) },
            isLoading = isLoading
        )
    }

    private suspend fun loadChords() {
        getChordsUseCase(Unit).collect { resource ->
            when (resource) {
                is Resource.Success -> {
                    setStateForResource(resource, false)
                }
                is Resource.Error -> {
                    setStateForResource(resource, false)
                }
                is Resource.Loading -> {
                    setStateForResource(resource, true)
                }
            }
        }

    }

    fun getChords() {
        launchCoroutine {
            delay(500L)
            loadChords()
        }
    }

//    private val _text = MutableLiveData<String>().apply {
//        value = "This is home Fragment"
//    }
//    val text: LiveData<String> = _text
}