package com.example.domain.mapper

interface Mapper<E, T> {
    fun from(type: E): T
}