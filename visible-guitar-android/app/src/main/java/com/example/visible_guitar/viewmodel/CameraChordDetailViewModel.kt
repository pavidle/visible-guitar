package com.example.visible_guitar.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.common.Resource
import com.example.domain.interactor.AddChordForCurrentUserUseCase
import com.example.domain.interactor.GetChordByIdUseCase
import com.example.domain.mapper.Mapper
import com.example.domain.model.ChordEntity
import com.example.visible_guitar.common.extensions.launchCoroutine
import com.example.visible_guitar.model.Chord
import com.example.visible_guitar.model.states.State
import com.example.visible_guitar.viewmodel.base.BaseDetailViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class CameraChordDetailViewModel @Inject constructor(
    private val addChordForCurrentUserUseCase: AddChordForCurrentUserUseCase,
    private val getChordByIdUseCase: GetChordByIdUseCase,
    private val chordMapper: Mapper<ChordEntity, Chord>
) : BaseDetailViewModel() {

    private val _chordState = MutableLiveData<State<Chord>>()
    val chordState: LiveData<State<Chord>> = _chordState

    fun loadChord(id: Int) =
        getChordByIdUseCase(id).subscribeOnCollect(chordMapper, _chordState)

    fun addLearnedChord(id: Int) =
        launchCoroutine {
            addChordForCurrentUserUseCase(id).collect {
                when(it) {
                    is Resource.Error -> Log.e("ERROR", "SOME ERROR")
                    is Resource.Loading -> Log.e("LOADING", "LOADING")
                    is Resource.Success -> State.Success(it.data)
                }
            }
        }
}