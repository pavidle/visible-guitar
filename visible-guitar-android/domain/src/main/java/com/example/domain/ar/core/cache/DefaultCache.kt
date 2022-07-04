package com.example.domain.ar.core.cache

import java.security.KeyException

class DefaultCache<K: Any, T : Any> : Cache<K, T> {

    private val data = mutableMapOf<K, T>()

    override fun get(key: K): T? {
        return data[key]?.let {
            null
        }
    }

    override fun save(key: K, value: T) {
        data[key] = value
    }

    override fun getSize() = data.size

//    private data class CacheKey(
//        val data: Any
//    ) {
//        override fun equals(other: Any?): Boolean {
//            if (this === other) return true
//            if (javaClass != other?.javaClass) return false
//            other as CacheKey
//            if (data != other.data) return false
//            return true
//        }
//
//        override fun hashCode(): Int {
//            return data.hashCode()
//        }
//    }
}