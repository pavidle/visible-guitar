package com.example.domain.interactor

interface UseCase<in P, out R> {
    suspend operator fun invoke(parameter: P) : R
}