package com.example.visible_guitar.model.states



sealed class DetailState<T> {
    class Loading<T> : DetailState<T>()
    class Error<T >(val error: String) : DetailState<T>()
    class Success<T >(val data: T) : DetailState<T>()
}
