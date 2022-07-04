package com.example.visible_guitar.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.common.Resource
import com.example.domain.interactor.GetChordByIdUseCase
import com.example.domain.interactor.GetChordsUseCase
import com.example.domain.mapper.Mapper
import com.example.domain.model.ChordEntity
import com.example.visible_guitar.common.extensions.launchCoroutine
import com.example.visible_guitar.model.Chord
import com.example.visible_guitar.model.states.State
import com.example.visible_guitar.model.states.StateOfList
import com.example.visible_guitar.viewmodel.base.BaseDetailViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
open class ChordDetailViewModel @Inject constructor(
    private val getChordByIdUseCase: GetChordByIdUseCase,
    private val getChordsUseCase: GetChordsUseCase,
    private val chordMapper: Mapper<ChordEntity, Chord>
) : BaseDetailViewModel() {

    private val _chordState = MutableLiveData<State<Chord>>()
    val chordState: LiveData<State<Chord>> = _chordState

    private val _chordsState = MutableLiveData<StateOfList<Chord>>()
    val chordsState: LiveData<StateOfList<Chord>> = _chordsState

    fun loadChord(id: Int) =
        getChordByIdUseCase(id).subscribeOnCollect(chordMapper, _chordState)

    fun getOtherChords(exceptId: Int) =
        launchCoroutine {
            getChordsUseCase(Unit).collect { resource ->
                when(resource) {
                    is Resource.Error -> Log.e("ERROR", "ERROR")
                    is Resource.Loading -> Log.e("LOADING", "LOADING")
                    is Resource.Success -> resource.data?.let { data ->
                        val filtered =  data.filter { item -> item.id != exceptId }
                        _chordsState.value = StateOfList.Success(
                            filtered.map { entity -> chordMapper.convert(entity) }
                        )
                    }
                }

            }
        }

}