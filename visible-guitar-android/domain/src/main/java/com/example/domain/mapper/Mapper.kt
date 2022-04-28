package com.example.domain.mapper

interface Mapper<E, T> {
    fun convert(type: E): T
}