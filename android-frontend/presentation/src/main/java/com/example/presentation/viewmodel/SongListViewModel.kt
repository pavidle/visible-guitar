package com.example.presentation.viewmodel

import com.example.domain.interactor.GetSongByNameUseCase
import javax.inject.Inject

class SongListViewModel @Inject constructor(
    private val getSongByNameUseCase: GetSongByNameUseCase
) {

}