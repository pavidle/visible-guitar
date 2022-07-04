package com.example.visible_guitar.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.example.domain.ar.model.LabelNote
import com.example.domain.common.Resource
import com.example.domain.interactor.AddMelodyForCurrentUserUseCase
import com.example.domain.interactor.GetMelodyByIdUseCase
import com.example.domain.mapper.Mapper
import com.example.domain.model.MelodyEntity
import com.example.visible_guitar.common.extensions.launchCoroutine
import com.example.visible_guitar.common.extensions.toast
import com.example.visible_guitar.model.Chord
import com.example.visible_guitar.model.Melody
import com.example.visible_guitar.model.states.State
import com.example.visible_guitar.viewmodel.base.BaseDetailViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraMelodyDetailViewModel @Inject constructor(
    private val getMelodyByIdUseCase: GetMelodyByIdUseCase,
    private val addMelodyForCurrentUserUseCase: AddMelodyForCurrentUserUseCase,
    private val melodyMapper: Mapper<MelodyEntity, Melody>
) : BaseDetailViewModel() {

    private val _melodyState = MutableLiveData<State<Melody>>()
    val melodyState: LiveData<State<Melody>> = _melodyState
    private val _channel = Channel<Chord>()
    val channelFlow = _channel.receiveAsFlow()

    fun loadMelody(id: Int) =
        getMelodyByIdUseCase(id).subscribeOnCollect(melodyMapper, _melodyState)

    fun addLearnedMelody(id: Int) =
        launchCoroutine {
            addMelodyForCurrentUserUseCase(id).collect {
                when(it) {
                    is Resource.Error -> Log.e("ERROR", "SOME ERROR")
                    is Resource.Loading -> Log.e("LOADING", "LOADING")
                    is Resource.Success -> State.Success(it.data)
                }
            }
        }

}