package com.example.domain.common

sealed class SignUpResult<T>(val data: T? = null) {
    class Registered<T>(data: T? = null) : SignUpResult<T>(data)
    class NetworkError<T>(val message: String?) : SignUpResult<T>()
    class ApiError<T>(val body: UserFieldErrorBody? = null, val code: Int? = null) : SignUpResult<T>()
    class Loading<T> : SignUpResult<T>()
}
