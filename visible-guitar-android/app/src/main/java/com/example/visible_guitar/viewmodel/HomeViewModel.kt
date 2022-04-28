package com.example.visible_guitar.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.interactor.GetChordsUseCase
import com.example.domain.mapper.Mapper
import com.example.domain.model.ChordEntity
import com.example.visible_guitar.common.extensions.launchCoroutine
import com.example.visible_guitar.model.Chord
import com.example.visible_guitar.model.states.ListState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getChordsUseCase: GetChordsUseCase,
    private val chordMapper: Mapper<ChordEntity, Chord>
) : BaseViewModel<Chord>() {

    private val _state = MutableLiveData<ListState<Chord>>()
    val state: LiveData<ListState<Chord>> = _state

    init {
        getChords()
    }

    private suspend fun loadChords() =
        getChordsUseCase(Unit).subscribeOnCollect(chordMapper, _state)


    private fun getChords() =
        launchCoroutine {
//            delay(1000L)
            loadChords()
        }

    fun reload() {
        getChords()
    }
}