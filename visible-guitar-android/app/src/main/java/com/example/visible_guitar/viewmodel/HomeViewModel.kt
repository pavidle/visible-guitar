package com.example.visible_guitar.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.interactor.GetChordsUseCase
import com.example.domain.mapper.Mapper
import com.example.domain.model.ChordEntity
import com.example.visible_guitar.common.extensions.launchCoroutine
import com.example.visible_guitar.model.Chord
import com.example.visible_guitar.model.states.StateOfList
import com.example.visible_guitar.viewmodel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getChordsUseCase: GetChordsUseCase,
    private val chordMapper: Mapper<ChordEntity, Chord>
) : BaseViewModel() {

    private val _chordsState = MutableLiveData<StateOfList<Chord>>()
    val chordsState: LiveData<StateOfList<Chord>> = _chordsState

    init {
        loadChords()
    }

    private fun loadChords() =
        getChordsUseCase(Unit).subscribeOnCollect(chordMapper, _chordsState)

    fun reload() {
        loadChords()
    }
}