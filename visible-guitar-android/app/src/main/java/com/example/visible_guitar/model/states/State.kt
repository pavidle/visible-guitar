package com.example.visible_guitar.model.states

sealed class State<T> {
     class Loading<T> : State<T>()
     class Error<T>(val error: String) : State<T>()
     class Success<T >(val data: List<T>) : State<T>()
}