package com.example.visible_guitar.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.common.Resource
import com.example.domain.interactor.GetChordsUseCase
import com.example.domain.mapper.Mapper
import com.example.domain.model.ChordEntity
import com.example.visible_guitar.common.launchCoroutine
import com.example.visible_guitar.model.Chord
import com.example.visible_guitar.model.states.ChordState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getChordsUseCase: GetChordsUseCase,
    private val chordMapper: Mapper<ChordEntity, Chord>
) : ViewModel() {

    private val _state = MutableLiveData<ChordState>()
    val state: LiveData<ChordState> = _state

    private val _event = MutableSharedFlow<BarEvent>()
    val event = _event.asSharedFlow()

    init {
        _state.value = ChordState(
            items = emptyList(),
            isLoading = true
        )
        getChords()
    }

    private fun setParametersForState(
        resource: Resource<List<ChordEntity>>,
        isLoading: Boolean,
    ) {
        _state.value = _state.value?.copy(
            items = resource.data?.map { chordMapper.from(it) },
            isLoading = isLoading,
        )

    }

    private suspend fun loadChords() {
        getChordsUseCase(Unit).collect { resource ->
            when (resource) {
                is Resource.Success -> {
                    setParametersForState(resource, false)
                }
                is Resource.Error -> {
                    setParametersForState(resource, true)
                    _event.emit(
                        BarEvent.Show(
                            resource.message ?: "Непредвиденная ошибка"
                        )
                    )
                }
                is Resource.Loading -> {
                    setParametersForState(resource, true)
                }
            }
        }

    }

    private fun getChords() {
        launchCoroutine {
            delay(1000L)
            loadChords()
        }
    }

    sealed class BarEvent {
        data class Show(val message: String) : BarEvent()
    }
}