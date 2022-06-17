package com.example.visible_guitar.model.states

sealed class StateOfList<T> {
     class Loading<T> : StateOfList<T>()
     class Error<T>(val error: String) : StateOfList<T>()
     class Success<T>(val data: List<T>) : StateOfList<T>()
}