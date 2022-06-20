package com.example.visible_guitar.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.interactor.GetChordByIdUseCase
import com.example.domain.mapper.Mapper
import com.example.domain.model.ChordEntity
import com.example.visible_guitar.common.extensions.launchCoroutine
import com.example.visible_guitar.model.Chord
import com.example.visible_guitar.model.states.State
import com.example.visible_guitar.viewmodel.base.BaseDetailViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class ChordDetailViewModel @Inject constructor(
    private val getChordByIdUseCase: GetChordByIdUseCase,
    private val chordMapper: Mapper<ChordEntity, Chord>
) : BaseDetailViewModel() {

    private val _chordState = MutableLiveData<State<Chord>>()
    val chordState: LiveData<State<Chord>> = _chordState

    fun loadChord(id: Int) =
        getChordByIdUseCase(id).subscribeOnCollect(chordMapper, _chordState)
}