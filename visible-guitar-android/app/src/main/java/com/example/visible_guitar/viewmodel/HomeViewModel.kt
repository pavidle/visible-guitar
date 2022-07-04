package com.example.visible_guitar.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.interactor.GetChordsUseCase
import com.example.domain.interactor.GetMelodiesUseCase
import com.example.domain.mapper.Mapper
import com.example.domain.model.ChordEntity
import com.example.domain.model.MelodyEntity
import com.example.visible_guitar.common.extensions.launchCoroutine
import com.example.visible_guitar.model.Chord
import com.example.visible_guitar.model.Melody
import com.example.visible_guitar.model.states.StateOfList
import com.example.visible_guitar.viewmodel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getChordsUseCase: GetChordsUseCase,
    private val getMelodiesUseCase: GetMelodiesUseCase,
    private val chordMapper: Mapper<ChordEntity, Chord>,
    private val melodyMapper: Mapper<MelodyEntity, Melody>
) : BaseViewModel() {

    private val _chordsState = MutableLiveData<StateOfList<Chord>>()
    val chordsState: LiveData<StateOfList<Chord>> = _chordsState

    private val _melodiesState = MutableLiveData<StateOfList<Melody>>()
    val melodiesState: LiveData<StateOfList<Melody>> = _melodiesState

    init {
        reload()
    }

    private fun loadChords() =
        getChordsUseCase(Unit).subscribeOnCollect(chordMapper, _chordsState)

    private fun loadMelodies() =
        getMelodiesUseCase(Unit).subscribeOnCollect(melodyMapper, _melodiesState)

    fun reload() {
        loadChords()
        loadMelodies()
    }
}