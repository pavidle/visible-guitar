package com.example.visible_guitar.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.interactor.GetMelodyByIdUseCase
import com.example.domain.mapper.Mapper
import com.example.domain.model.MelodyEntity
import com.example.visible_guitar.mapper.MelodyMapper
import com.example.visible_guitar.model.Melody
import com.example.visible_guitar.model.states.State
import com.example.visible_guitar.viewmodel.base.BaseDetailViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MelodyDetailViewModel @Inject constructor(
    private val getMelodyByIdUseCase: GetMelodyByIdUseCase,
    private val melodyMapper: Mapper<MelodyEntity, Melody>
) : BaseDetailViewModel() {

    private val _melodyState = MutableLiveData<State<Melody>>()
    val melodyState: LiveData<State<Melody>> = _melodyState

    fun loadMelody(id: Int) =
        getMelodyByIdUseCase(id).subscribeOnCollect(melodyMapper, _melodyState)

}