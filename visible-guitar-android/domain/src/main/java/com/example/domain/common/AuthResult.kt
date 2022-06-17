package com.example.domain.common

sealed class AuthResult<T> {
    class Authorized<T>(val data: T? = null) : AuthResult<T>()
    class Unauthorized<T>(val message: String?) : AuthResult<T>()
    class Loading<T> : AuthResult<T>()
}
