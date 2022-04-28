package com.example.data.common

interface ApiFactory<T> {
    fun create() : T
}