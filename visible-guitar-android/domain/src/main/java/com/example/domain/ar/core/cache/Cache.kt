package com.example.domain.ar.core.cache

interface Cache<K, T> {
    fun get(key: K): T?
    fun save(key: K, value: T)
    fun getSize(): Int
}