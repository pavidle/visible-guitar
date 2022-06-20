package com.example.visible_guitar.viewmodel

import com.example.domain.interactor.GetChordByIdUseCase
import com.example.domain.mapper.Mapper
import com.example.domain.model.ChordEntity
import com.example.visible_guitar.model.Chord
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CameraChordDetailViewModel @Inject constructor(
    private val getChordByIdUseCase: GetChordByIdUseCase,
    private val chordMapper: Mapper<ChordEntity, Chord>
) : ChordDetailViewModel(getChordByIdUseCase, chordMapper)