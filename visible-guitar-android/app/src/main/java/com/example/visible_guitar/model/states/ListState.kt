package com.example.visible_guitar.model.states

import com.example.visible_guitar.model.Model

sealed class ListState<T : Model> {
     class Loading<T : Model> : ListState<T>()
     class Error<T : Model>(val error: String) : ListState<T>()
     class Success<T : Model>(val data: List<T>) : ListState<T>()
}